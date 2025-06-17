package com.example.article.lock.service.impl;

import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.service.base.ArticleEditLockLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nicollas Rezende
 */
@Component(
		property = "model.class.name=com.example.article.lock.model.ArticleEditLock",
		service = AopService.class
)
public class ArticleEditLockLocalServiceImpl
		extends ArticleEditLockLocalServiceBaseImpl {

	private static final Log _log = LogFactoryUtil.getLog(ArticleEditLockLocalServiceImpl.class);

	/**
	 * Tenta criar um lock para edição do artigo
	 * @return true se conseguiu criar o lock, false se já existe um lock ativo
	 */
	public boolean tryLockArticle(
			String articleId, long userId, ServiceContext serviceContext)
			throws PortalException {

		_log.info("Trying to lock article: " + articleId + " for user: " + userId);

		// Verifica se já existe um lock ativo para este artigo
		ArticleEditLock existingLock = getActiveArticleLock(articleId);

		if (existingLock != null) {
			_log.info("Found existing lock for article: " + articleId +
					", locked by user: " + existingLock.getUserId() +
					", lock time: " + existingLock.getLockTime());

			// Verifica se o lock expirou (mais de 5 minutos para teste, mude para 2 horas em produção)
			long lockAge = System.currentTimeMillis() - existingLock.getLockTime().getTime();
			long fiveMinutesInMillis = 5 * 60 * 1000; // 5 minutos para teste
			// long twoHoursInMillis = 2 * 60 * 60 * 1000; // 2 horas para produção

			if (lockAge > fiveMinutesInMillis) {
				// Lock expirou, reutiliza o registro existente
				_log.info("Lock expired, reusing existing record...");

				User user = userLocalService.getUser(userId);

				existingLock.setUserId(userId);
				existingLock.setUserName(user.getScreenName());
				existingLock.setUserFullName(user.getFullName());
				existingLock.setLocked(true);
				existingLock.setLockTime(new Date());
				existingLock.setModifiedDate(new Date());

				updateArticleEditLock(existingLock);
				_log.info("Lock updated successfully for article: " + articleId);
				return true;

			} else if (existingLock.getUserId() != userId) {
				// Lock ativo de outro usuário
				_log.info("Article is locked by another user");
				return false;
			} else {
				// Mesmo usuário, atualiza o timestamp
				_log.info("Same user, updating lock timestamp");
				existingLock.setLockTime(new Date());
				existingLock.setModifiedDate(new Date());
				updateArticleEditLock(existingLock);
				return true;
			}
		}

		// Verifica se existe um registro inativo (locked=false) para reutilizar
		ArticleEditLock inactiveLock = getInactiveArticleLock(articleId);

		if (inactiveLock != null) {
			_log.info("Found inactive lock record, reusing it...");

			User user = userLocalService.getUser(userId);

			inactiveLock.setUserId(userId);
			inactiveLock.setUserName(user.getScreenName());
			inactiveLock.setUserFullName(user.getFullName());
			inactiveLock.setLocked(true);
			inactiveLock.setLockTime(new Date());
			inactiveLock.setModifiedDate(new Date());

			updateArticleEditLock(inactiveLock);
			_log.info("Inactive lock reactivated successfully for article: " + articleId);
			return true;
		}

		// Cria novo lock apenas se não existe nenhum registro
		try {
			_log.info("Creating new lock record for article: " + articleId);

			long articleEditLockId = counterLocalService.increment();
			ArticleEditLock articleEditLock = createArticleEditLock(articleEditLockId);

			User user = userLocalService.getUser(userId);

			articleEditLock.setCompanyId(serviceContext.getCompanyId());
			articleEditLock.setGroupId(serviceContext.getScopeGroupId());
			articleEditLock.setCreateDate(new Date());
			articleEditLock.setModifiedDate(new Date());
			articleEditLock.setArticleId(articleId);
			articleEditLock.setUserId(userId);
			articleEditLock.setUserName(user.getScreenName());
			articleEditLock.setUserFullName(user.getFullName());
			articleEditLock.setLocked(true);
			articleEditLock.setLockTime(new Date());

			addArticleEditLock(articleEditLock);
			_log.info("New lock created successfully for article: " + articleId);

			return true;
		} catch (Exception e) {
			_log.error("Error creating lock for article: " + articleId, e);
			// Se falhar por constraint violation, significa que outro processo criou o lock
			// Retorna false indicando que não conseguiu criar o lock
			return false;
		}
	}

	/**
	 * Libera o lock do artigo
	 */
	public void unlockArticle(String articleId) throws PortalException {
		_log.info("Unlocking article: " + articleId);

		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery = dynamicQuery();

		dynamicQuery.add(
				com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.eq("articleId", articleId)
		);

		List<ArticleEditLock> locks = dynamicQuery(dynamicQuery);

		for (ArticleEditLock lock : locks) {
			deleteArticleEditLock(lock);
			_log.info("Lock record DELETED for article: " + articleId);
		}
	}

	/**
	 * Obtém o lock ativo para um artigo
	 */
	public ArticleEditLock getActiveArticleLock(String articleId) {
		try {
			return articleEditLockPersistence.findByArticleId(
					articleId, true);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Obtém um lock inativo para um artigo
	 */
	private ArticleEditLock getInactiveArticleLock(String articleId) {
		try {
			return articleEditLockPersistence.findByArticleId(
					articleId, false);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Verifica se um artigo está bloqueado por outro usuário
	 */
	public boolean isArticleLockedByOtherUser(String articleId, long userId) {
		ArticleEditLock lock = getActiveArticleLock(articleId);

		if (lock == null) {
			return false;
		}

		// Verifica timeout de 5 minutos para teste
		long lockAge = System.currentTimeMillis() - lock.getLockTime().getTime();
		long fiveMinutesInMillis = 5 * 60 * 1000; // 5 minutos para teste
		// long twoHoursInMillis = 2 * 60 * 60 * 1000; // 2 horas para produção

		if (lockAge > fiveMinutesInMillis) {
			return false;
		}

		return lock.getUserId() != userId;
	}

	/**
	 * Limpa locks expirados (mais de 2 horas)
	 */
	public void cleanExpiredLocks() {
		try {
			_log.info("Starting expired locks cleanup...");

			// Busca todos os locks ativos
			// Como não temos um finder específico para todos os locks ativos,
			// vamos buscar todos e filtrar
			List<ArticleEditLock> allLocks =
					articleEditLockPersistence.findAll();

			Date fiveMinutesAgo = new Date(System.currentTimeMillis() - (5 * 60 * 1000)); // 5 minutos para teste
			// Date twoHoursAgo = new Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000)); // 2 horas para produção

			_log.info("Looking for locks older than: " + fiveMinutesAgo);
			_log.info("Total locks found: " + allLocks.size());

			int expiredCount = 0;
			for (ArticleEditLock lock : allLocks) {
				// Verifica se está locked e se expirou
				if (lock.isLocked() && lock.getLockTime().before(fiveMinutesAgo)) {
					_log.info("Found expired lock: articleId=" + lock.getArticleId() +
							", userId=" + lock.getUserId() +
							", lockTime=" + lock.getLockTime());

					lock.setLocked(false);
					lock.setModifiedDate(new Date());
					updateArticleEditLock(lock);
					expiredCount++;
				}
			}

			_log.info("Expired locks cleanup completed. Cleaned " + expiredCount + " locks.");
		} catch (Exception e) {
			// Log the error
			_log.error("Error during expired locks cleanup", e);
			e.printStackTrace();
		}
	}

	/**
	 * Obtém informações do usuário que está editando
	 */
	public User getEditingUser(String articleId) throws PortalException {
		ArticleEditLock lock = getActiveArticleLock(articleId);

		if (lock != null) {
			return userLocalService.getUser(lock.getUserId());
		}

		return null;
	}

	// UserLocalService já é herdado da classe base
	// Não precisa declarar @Reference aqui
}