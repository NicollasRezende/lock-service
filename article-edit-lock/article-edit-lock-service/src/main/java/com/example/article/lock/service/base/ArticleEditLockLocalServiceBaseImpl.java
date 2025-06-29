/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service.base;

import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.service.ArticleEditLockLocalService;
import com.example.article.lock.service.persistence.ArticleEditLockPersistence;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.sql.Connection;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the article edit lock local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.example.article.lock.service.impl.ArticleEditLockLocalServiceImpl}.
 * </p>
 *
 * @author Nicollas Rezende
 * @see com.example.article.lock.service.impl.ArticleEditLockLocalServiceImpl
 * @generated
 */
public abstract class ArticleEditLockLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, ArticleEditLockLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>ArticleEditLockLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.example.article.lock.service.ArticleEditLockLocalServiceUtil</code>.
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
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ArticleEditLock addArticleEditLock(ArticleEditLock articleEditLock) {
		articleEditLock.setNew(true);

		return articleEditLockPersistence.update(articleEditLock);
	}

	/**
	 * Creates a new article edit lock with the primary key. Does not add the article edit lock to the database.
	 *
	 * @param articleEditLockId the primary key for the new article edit lock
	 * @return the new article edit lock
	 */
	@Override
	@Transactional(enabled = false)
	public ArticleEditLock createArticleEditLock(long articleEditLockId) {
		return articleEditLockPersistence.create(articleEditLockId);
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
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ArticleEditLock deleteArticleEditLock(long articleEditLockId)
		throws PortalException {

		return articleEditLockPersistence.remove(articleEditLockId);
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
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ArticleEditLock deleteArticleEditLock(
		ArticleEditLock articleEditLock) {

		return articleEditLockPersistence.remove(articleEditLock);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return articleEditLockPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			ArticleEditLock.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return articleEditLockPersistence.findWithDynamicQuery(dynamicQuery);
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
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return articleEditLockPersistence.findWithDynamicQuery(
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
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return articleEditLockPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return articleEditLockPersistence.countWithDynamicQuery(dynamicQuery);
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
		DynamicQuery dynamicQuery, Projection projection) {

		return articleEditLockPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public ArticleEditLock fetchArticleEditLock(long articleEditLockId) {
		return articleEditLockPersistence.fetchByPrimaryKey(articleEditLockId);
	}

	/**
	 * Returns the article edit lock with the primary key.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws PortalException if a article edit lock with the primary key could not be found
	 */
	@Override
	public ArticleEditLock getArticleEditLock(long articleEditLockId)
		throws PortalException {

		return articleEditLockPersistence.findByPrimaryKey(articleEditLockId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(articleEditLockLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(ArticleEditLock.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("articleEditLockId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			articleEditLockLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(ArticleEditLock.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"articleEditLockId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(articleEditLockLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(ArticleEditLock.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("articleEditLockId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return articleEditLockPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Implement ArticleEditLockLocalServiceImpl#deleteArticleEditLock(ArticleEditLock) to avoid orphaned data");
		}

		return articleEditLockLocalService.deleteArticleEditLock(
			(ArticleEditLock)persistedModel);
	}

	@Override
	public BasePersistence<ArticleEditLock> getBasePersistence() {
		return articleEditLockPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return articleEditLockPersistence.findByPrimaryKey(primaryKeyObj);
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
	public List<ArticleEditLock> getArticleEditLocks(int start, int end) {
		return articleEditLockPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of article edit locks.
	 *
	 * @return the number of article edit locks
	 */
	@Override
	public int getArticleEditLocksCount() {
		return articleEditLockPersistence.countAll();
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
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ArticleEditLock updateArticleEditLock(
		ArticleEditLock articleEditLock) {

		return articleEditLockPersistence.update(articleEditLock);
	}

	@Deactivate
	protected void deactivate() {
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			ArticleEditLockLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		articleEditLockLocalService = (ArticleEditLockLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return ArticleEditLockLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return ArticleEditLock.class;
	}

	protected String getModelClassName() {
		return ArticleEditLock.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		DataSource dataSource = articleEditLockPersistence.getDataSource();

		DB db = DBManagerUtil.getDB();

		Connection currentConnection = CurrentConnectionUtil.getConnection(
			dataSource);

		try {
			if (currentConnection != null) {
				db.runSQL(currentConnection, new String[] {sql});

				return;
			}

			try (Connection connection = dataSource.getConnection()) {
				db.runSQL(connection, new String[] {sql});
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected ArticleEditLockLocalService articleEditLockLocalService;

	@Reference
	protected ArticleEditLockPersistence articleEditLockPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.journal.service.JournalArticleLocalService
		journalArticleLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		ArticleEditLockLocalServiceBaseImpl.class);

}