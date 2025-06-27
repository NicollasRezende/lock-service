package com.example.article.lock.service.impl;

import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.service.base.ArticleEditLockLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nicollas Rezende
 */
@Component(property = "model.class.name=com.example.article.lock.model.ArticleEditLock", service = AopService.class)
public class ArticleEditLockLocalServiceImpl
		extends ArticleEditLockLocalServiceBaseImpl {

	private static final Log _log = LogFactoryUtil.getLog(ArticleEditLockLocalServiceImpl.class);

	/**
	 * Tenta criar um lock para edição do artigo
	 * 
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

			// Verifica se o lock expirou (30 minutos para teste, 2 horas para produção)
			long lockAge = System.currentTimeMillis() - existingLock.getLockTime().getTime();
			long thirtyMinutesInMillis = 30 * 60 * 1000; // 30 minutos para teste
			// long twoHoursInMillis = 2 * 60 * 60 * 1000; // 2 horas para produção

			if (lockAge > thirtyMinutesInMillis) {
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
				com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.eq("articleId", articleId));

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
		_log.info("🔍 SERVICE: isArticleLockedByOtherUser(articleId=" + articleId + ", userId=" + userId + ")");

		ArticleEditLock lock = getActiveArticleLock(articleId);

		if (lock == null) {
			_log.info("🔓 SERVICE: No active lock found → returning false");
			return false;
		}

		_log.info("🔒 SERVICE: Active lock found - lockUserId=" + lock.getUserId() +
				", lockTime=" + lock.getLockTime());

		// Verifica timeout de 30 minutos para teste
		long lockAge = System.currentTimeMillis() - lock.getLockTime().getTime();
		long thirtyMinutesInMillis = 30 * 60 * 1000; // 30 minutos para teste
		// long twoHoursInMillis = 2 * 60 * 60 * 1000; // 2 horas para produção

		_log.info("⏱️ SERVICE: Lock age=" + lockAge + "ms (" + (lockAge / 1000) + " seconds)");

		if (lockAge > thirtyMinutesInMillis) {
			_log.info("⌛ SERVICE: Lock expired → returning false");
			return false;
		}

		boolean isOtherUser = lock.getUserId() != userId;
		_log.info("👤 SERVICE: Is other user? " + isOtherUser + " (lock=" + lock.getUserId() + " vs current=" + userId
				+ ")");

		return isOtherUser;
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
			List<ArticleEditLock> allLocks = articleEditLockPersistence.findAll();

			Date thirtyMinutesAgo = new Date(System.currentTimeMillis() - (30 * 60 * 1000)); // 30 minutos para teste
			// Date twoHoursAgo = new Date(System.currentTimeMillis() - (2 * 60 * 60 *
			// 1000)); // 2 horas para produção

			_log.info("Looking for locks older than: " + thirtyMinutesAgo);
			_log.info("Total locks found: " + allLocks.size());

			int expiredCount = 0;
			for (ArticleEditLock lock : allLocks) {
				// Verifica se está locked e se expirou
				if (lock.isLocked() && lock.getLockTime().before(thirtyMinutesAgo)) {
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

	/**
	 * Toma o controle de um artigo bloqueado, transferindo o lock para um novo
	 * usuário
	 * Não requer verificação de permissões - qualquer usuário pode tomar controle
	 *
	 * @param articleId      ID do artigo
	 * @param newUserId      ID do novo usuário que assumirá o controle
	 * @param serviceContext contexto do serviço
	 * @return ArticleEditLock atualizado
	 * @throws PortalException se não houver lock ativo ou erro na transferência
	 */
	@Override
	public ArticleEditLock takeControlOfArticle(
			String articleId, long newUserId, ServiceContext serviceContext)
			throws PortalException {

		_log.info("takeControlOfArticle - Article: " + articleId +
				", New User: " + newUserId);

		// 1. Buscar lock ativo
		ArticleEditLock currentLock = getActiveArticleLock(articleId);

		if (currentLock == null) {
			throw new PortalException(
					"No active lock found for article: " + articleId);
		}

		// 2. Guardar informações do usuário anterior
		long previousUserId = currentLock.getUserId();
		String previousUserName = currentLock.getUserName();

		// 3. Atualizar lock com novo usuário
		User newUser = userLocalService.getUser(newUserId);
		currentLock.setUserId(newUserId);
		currentLock.setUserName(newUser.getScreenName());
		currentLock.setUserFullName(newUser.getFullName());
		currentLock.setLockTime(new Date());
		currentLock.setModifiedDate(new Date());

		// 4. Persistir mudanças
		ArticleEditLock updatedLock = updateArticleEditLock(currentLock);

		// 5. Enviar notificação (será implementado na Parte 4)
		try {
			sendTakeControlNotification(
					previousUserId, previousUserName, newUserId,
					newUser.getFullName(), articleId, serviceContext);
		} catch (Exception e) {
			_log.error("Error sending notification", e);
			// Não falhar a operação por erro na notificação
		}

		_log.info("Control taken successfully: Article " + articleId +
				" transferred from user " + previousUserId +
				" to user " + newUserId);

		return updatedLock;
	}

	/**
	 * Método auxiliar para notificação (implementação básica por enquanto)
	 */
	private void sendTakeControlNotification(
			long previousUserId, String previousUserName,
			long newUserId, String newUserName,
			String articleId, ServiceContext serviceContext) {

		try {
			// Buscar informações do artigo
			String articleTitle = articleId;
			try {
				com.liferay.journal.model.JournalArticle article = journalArticleLocalService.getArticle(
						serviceContext.getScopeGroupId(), articleId);
				articleTitle = article.getTitle(serviceContext.getLocale());
			} catch (Exception e) {
				_log.debug("Could not get article title", e);
			}

			// Criar payload da notificação
			JSONObject payload = JSONFactoryUtil.createJSONObject();
			payload.put("notificationType", "article-control-taken");
			payload.put("articleId", articleId);
			payload.put("articleTitle", articleTitle);
			payload.put("previousUserId", previousUserId);
			payload.put("previousUserName", previousUserName);
			payload.put("newUserId", newUserId);
			payload.put("newUserName", newUserName);
			payload.put("timestamp", System.currentTimeMillis());

			// Criar notificação
			com.liferay.portal.kernel.model.UserNotificationEvent notification = com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil
					.createUserNotificationEvent(
							counterLocalService.increment());

			notification.setCompanyId(serviceContext.getCompanyId());
			notification.setUserId(previousUserId);
			notification.setType(com.liferay.journal.constants.JournalPortletKeys.JOURNAL);
			notification.setTimestamp(System.currentTimeMillis());
			notification.setDeliveryType(
					com.liferay.portal.kernel.model.UserNotificationDeliveryConstants.TYPE_WEBSITE);
			notification.setDelivered(false);
			notification.setArchived(false);
			notification.setPayload(payload.toString());

			// Salvar notificação
			com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
					notification);

			_log.info("Notification sent to user " + previousUserId +
					" about control transfer");

		} catch (Exception e) {
			_log.error("Error sending take control notification", e);
			// Não propagar erro - notificação é secundária
		}
	}

	// UserLocalService já é herdado da classe base
	// Não precisa declarar @Reference aqui
}