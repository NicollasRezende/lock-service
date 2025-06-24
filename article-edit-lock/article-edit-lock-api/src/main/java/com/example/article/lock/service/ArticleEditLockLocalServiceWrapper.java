/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ArticleEditLockLocalService}.
 *
 * @author Nicollas Rezende
 * @see ArticleEditLockLocalService
 * @generated
 */
public class ArticleEditLockLocalServiceWrapper
	implements ArticleEditLockLocalService,
			   ServiceWrapper<ArticleEditLockLocalService> {

	public ArticleEditLockLocalServiceWrapper() {
		this(null);
	}

	public ArticleEditLockLocalServiceWrapper(
		ArticleEditLockLocalService articleEditLockLocalService) {

		_articleEditLockLocalService = articleEditLockLocalService;
	}

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
	@Override
	public com.example.article.lock.model.ArticleEditLock addArticleEditLock(
		com.example.article.lock.model.ArticleEditLock articleEditLock) {

		return _articleEditLockLocalService.addArticleEditLock(articleEditLock);
	}

	/**
	 * Limpa locks expirados (mais de 2 horas)
	 */
	@Override
	public void cleanExpiredLocks() {
		_articleEditLockLocalService.cleanExpiredLocks();
	}

	/**
	 * Creates a new article edit lock with the primary key. Does not add the article edit lock to the database.
	 *
	 * @param articleEditLockId the primary key for the new article edit lock
	 * @return the new article edit lock
	 */
	@Override
	public com.example.article.lock.model.ArticleEditLock createArticleEditLock(
		long articleEditLockId) {

		return _articleEditLockLocalService.createArticleEditLock(
			articleEditLockId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public com.example.article.lock.model.ArticleEditLock deleteArticleEditLock(
		com.example.article.lock.model.ArticleEditLock articleEditLock) {

		return _articleEditLockLocalService.deleteArticleEditLock(
			articleEditLock);
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
	@Override
	public com.example.article.lock.model.ArticleEditLock deleteArticleEditLock(
			long articleEditLockId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.deleteArticleEditLock(
			articleEditLockId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _articleEditLockLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _articleEditLockLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _articleEditLockLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _articleEditLockLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _articleEditLockLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _articleEditLockLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _articleEditLockLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _articleEditLockLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.example.article.lock.model.ArticleEditLock fetchArticleEditLock(
		long articleEditLockId) {

		return _articleEditLockLocalService.fetchArticleEditLock(
			articleEditLockId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _articleEditLockLocalService.getActionableDynamicQuery();
	}

	/**
	 * Obtém o lock ativo para um artigo
	 */
	@Override
	public com.example.article.lock.model.ArticleEditLock getActiveArticleLock(
		String articleId) {

		return _articleEditLockLocalService.getActiveArticleLock(articleId);
	}

	/**
	 * Returns the article edit lock with the primary key.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws PortalException if a article edit lock with the primary key could not be found
	 */
	@Override
	public com.example.article.lock.model.ArticleEditLock getArticleEditLock(
			long articleEditLockId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.getArticleEditLock(
			articleEditLockId);
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
	@Override
	public java.util.List<com.example.article.lock.model.ArticleEditLock>
		getArticleEditLocks(int start, int end) {

		return _articleEditLockLocalService.getArticleEditLocks(start, end);
	}

	/**
	 * Returns the number of article edit locks.
	 *
	 * @return the number of article edit locks
	 */
	@Override
	public int getArticleEditLocksCount() {
		return _articleEditLockLocalService.getArticleEditLocksCount();
	}

	/**
	 * Obtém informações do usuário que está editando
	 */
	@Override
	public com.liferay.portal.kernel.model.User getEditingUser(String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.getEditingUser(articleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _articleEditLockLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _articleEditLockLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Verifica se um artigo está bloqueado por outro usuário
	 */
	@Override
	public boolean isArticleLockedByOtherUser(String articleId, long userId) {
		return _articleEditLockLocalService.isArticleLockedByOtherUser(
			articleId, userId);
	}

	/**
	 * Toma o controle de um artigo bloqueado, transferindo o lock para um novo usuário
	 * Não requer verificação de permissões - qualquer usuário pode tomar controle
	 *
	 * @param articleId ID do artigo
	 * @param newUserId ID do novo usuário que assumirá o controle
	 * @param serviceContext contexto do serviço
	 * @return ArticleEditLock atualizado
	 * @throws PortalException se não houver lock ativo ou erro na transferência
	 */
	@Override
	public com.example.article.lock.model.ArticleEditLock takeControlOfArticle(
			String articleId, long newUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.takeControlOfArticle(
			articleId, newUserId, serviceContext);
	}

	/**
	 * Tenta criar um lock para edição do artigo
	 *
	 * @return true se conseguiu criar o lock, false se já existe um lock ativo
	 */
	@Override
	public boolean tryLockArticle(
			String articleId, long userId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _articleEditLockLocalService.tryLockArticle(
			articleId, userId, serviceContext);
	}

	/**
	 * Libera o lock do artigo
	 */
	@Override
	public void unlockArticle(String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_articleEditLockLocalService.unlockArticle(articleId);
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
	@Override
	public com.example.article.lock.model.ArticleEditLock updateArticleEditLock(
		com.example.article.lock.model.ArticleEditLock articleEditLock) {

		return _articleEditLockLocalService.updateArticleEditLock(
			articleEditLock);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _articleEditLockLocalService.getBasePersistence();
	}

	@Override
	public ArticleEditLockLocalService getWrappedService() {
		return _articleEditLockLocalService;
	}

	@Override
	public void setWrappedService(
		ArticleEditLockLocalService articleEditLockLocalService) {

		_articleEditLockLocalService = articleEditLockLocalService;
	}

	private ArticleEditLockLocalService _articleEditLockLocalService;

}