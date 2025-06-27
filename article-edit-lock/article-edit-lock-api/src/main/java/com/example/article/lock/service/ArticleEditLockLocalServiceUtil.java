/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service;

import com.example.article.lock.model.ArticleEditLock;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ArticleEditLock. This utility wraps
 * <code>com.example.article.lock.service.impl.ArticleEditLockLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Nicollas Rezende
 * @see ArticleEditLockLocalService
 * @generated
 */
public class ArticleEditLockLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.example.article.lock.service.impl.ArticleEditLockLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the article edit lock to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArticleEditLockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param articleEditLock the article edit lock
	 * @return the article edit lock that was added
	 */
	public static ArticleEditLock addArticleEditLock(
		ArticleEditLock articleEditLock) {

		return getService().addArticleEditLock(articleEditLock);
	}

	/**
	 * Limpa locks expirados (mais de 2 horas)
	 */
	public static void cleanExpiredLocks() {
		getService().cleanExpiredLocks();
	}

	/**
	 * Creates a new article edit lock with the primary key. Does not add the article edit lock to the database.
	 *
	 * @param articleEditLockId the primary key for the new article edit lock
	 * @return the new article edit lock
	 */
	public static ArticleEditLock createArticleEditLock(
		long articleEditLockId) {

		return getService().createArticleEditLock(articleEditLockId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the article edit lock from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArticleEditLockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param articleEditLock the article edit lock
	 * @return the article edit lock that was removed
	 */
	public static ArticleEditLock deleteArticleEditLock(
		ArticleEditLock articleEditLock) {

		return getService().deleteArticleEditLock(articleEditLock);
	}

	/**
	 * Deletes the article edit lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArticleEditLockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock that was removed
	 * @throws PortalException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock deleteArticleEditLock(long articleEditLockId)
		throws PortalException {

		return getService().deleteArticleEditLock(articleEditLockId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.article.lock.model.impl.ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.article.lock.model.impl.ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static ArticleEditLock fetchArticleEditLock(long articleEditLockId) {
		return getService().fetchArticleEditLock(articleEditLockId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Obtém o lock ativo para um artigo
	 */
	public static ArticleEditLock getActiveArticleLock(String articleId) {
		return getService().getActiveArticleLock(articleId);
	}

	/**
	 * Returns the article edit lock with the primary key.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws PortalException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock getArticleEditLock(long articleEditLockId)
		throws PortalException {

		return getService().getArticleEditLock(articleEditLockId);
	}

	/**
	 * Returns a range of all the article edit locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.example.article.lock.model.impl.ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @return the range of article edit locks
	 */
	public static List<ArticleEditLock> getArticleEditLocks(
		int start, int end) {

		return getService().getArticleEditLocks(start, end);
	}

	/**
	 * Returns the number of article edit locks.
	 *
	 * @return the number of article edit locks
	 */
	public static int getArticleEditLocksCount() {
		return getService().getArticleEditLocksCount();
	}

	/**
	 * Obtém informações do usuário que está editando
	 */
	public static com.liferay.portal.kernel.model.User getEditingUser(
			String articleId)
		throws PortalException {

		return getService().getEditingUser(articleId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Verifica se um artigo está bloqueado por outro usuário
	 */
	public static boolean isArticleLockedByOtherUser(
		String articleId, long userId) {

		return getService().isArticleLockedByOtherUser(articleId, userId);
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
	public static ArticleEditLock takeControlOfArticle(
			String articleId, long newUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().takeControlOfArticle(
			articleId, newUserId, serviceContext);
	}

	/**
	 * Tenta criar um lock para edição do artigo
	 *
	 * @return true se conseguiu criar o lock, false se já existe um lock ativo
	 */
	public static boolean tryLockArticle(
			String articleId, long userId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().tryLockArticle(articleId, userId, serviceContext);
	}

	/**
	 * Libera o lock do artigo
	 */
	public static void unlockArticle(String articleId) throws PortalException {
		getService().unlockArticle(articleId);
	}

	/**
	 * Updates the article edit lock in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArticleEditLockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param articleEditLock the article edit lock
	 * @return the article edit lock that was updated
	 */
	public static ArticleEditLock updateArticleEditLock(
		ArticleEditLock articleEditLock) {

		return getService().updateArticleEditLock(articleEditLock);
	}

	public static ArticleEditLockLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<ArticleEditLockLocalService>
		_serviceSnapshot = new Snapshot<>(
			ArticleEditLockLocalServiceUtil.class,
			ArticleEditLockLocalService.class);

}