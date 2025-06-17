/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service.persistence;

import com.example.article.lock.model.ArticleEditLock;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the article edit lock service. This utility wraps <code>com.example.article.lock.service.persistence.impl.ArticleEditLockPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nicollas Rezende
 * @see ArticleEditLockPersistence
 * @generated
 */
public class ArticleEditLockUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ArticleEditLock articleEditLock) {
		getPersistence().clearCache(articleEditLock);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, ArticleEditLock> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ArticleEditLock> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ArticleEditLock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ArticleEditLock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ArticleEditLock update(ArticleEditLock articleEditLock) {
		return getPersistence().update(articleEditLock);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ArticleEditLock update(
		ArticleEditLock articleEditLock, ServiceContext serviceContext) {

		return getPersistence().update(articleEditLock, serviceContext);
	}

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or throws a <code>NoSuchArticleEditLockException</code> if it could not be found.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByArticleId(
			String articleId, boolean locked)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByArticleId(articleId, locked);
	}

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByArticleId(
		String articleId, boolean locked) {

		return getPersistence().fetchByArticleId(articleId, locked);
	}

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByArticleId(
		String articleId, boolean locked, boolean useFinderCache) {

		return getPersistence().fetchByArticleId(
			articleId, locked, useFinderCache);
	}

	/**
	 * Removes the article edit lock where articleId = &#63; and locked = &#63; from the database.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the article edit lock that was removed
	 */
	public static ArticleEditLock removeByArticleId(
			String articleId, boolean locked)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().removeByArticleId(articleId, locked);
	}

	/**
	 * Returns the number of article edit locks where articleId = &#63; and locked = &#63;.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	public static int countByArticleId(String articleId, boolean locked) {
		return getPersistence().countByArticleId(articleId, locked);
	}

	/**
	 * Returns all the article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @return the matching article edit locks
	 */
	public static List<ArticleEditLock> findByUserId(
		long userId, boolean locked) {

		return getPersistence().findByUserId(userId, locked);
	}

	/**
	 * Returns a range of all the article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @return the range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end) {

		return getPersistence().findByUserId(userId, locked, start, end);
	}

	/**
	 * Returns an ordered range of all the article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().findByUserId(
			userId, locked, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, locked, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByUserId_First(
			long userId, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByUserId_First(
			userId, locked, orderByComparator);
	}

	/**
	 * Returns the first article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByUserId_First(
		long userId, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().fetchByUserId_First(
			userId, locked, orderByComparator);
	}

	/**
	 * Returns the last article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByUserId_Last(
			long userId, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByUserId_Last(
			userId, locked, orderByComparator);
	}

	/**
	 * Returns the last article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByUserId_Last(
		long userId, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().fetchByUserId_Last(
			userId, locked, orderByComparator);
	}

	/**
	 * Returns the article edit locks before and after the current article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param articleEditLockId the primary key of the current article edit lock
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock[] findByUserId_PrevAndNext(
			long articleEditLockId, long userId, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByUserId_PrevAndNext(
			articleEditLockId, userId, locked, orderByComparator);
	}

	/**
	 * Removes all the article edit locks where userId = &#63; and locked = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 */
	public static void removeByUserId(long userId, boolean locked) {
		getPersistence().removeByUserId(userId, locked);
	}

	/**
	 * Returns the number of article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	public static int countByUserId(long userId, boolean locked) {
		return getPersistence().countByUserId(userId, locked);
	}

	/**
	 * Returns all the article edit locks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching article edit locks
	 */
	public static List<ArticleEditLock> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the article edit locks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @return the range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the article edit locks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the article edit locks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByGroupId_First(
			long groupId, OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByGroupId_First(
		long groupId, OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByGroupId_Last(
			long groupId, OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByGroupId_Last(
		long groupId, OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the article edit locks before and after the current article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param articleEditLockId the primary key of the current article edit lock
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock[] findByGroupId_PrevAndNext(
			long articleEditLockId, long groupId,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByGroupId_PrevAndNext(
			articleEditLockId, groupId, orderByComparator);
	}

	/**
	 * Removes all the article edit locks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of article edit locks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching article edit locks
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @return the matching article edit locks
	 */
	public static List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked) {

		return getPersistence().findByLockTime(lockTime, locked);
	}

	/**
	 * Returns a range of all the article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @return the range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end) {

		return getPersistence().findByLockTime(lockTime, locked, start, end);
	}

	/**
	 * Returns an ordered range of all the article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().findByLockTime(
			lockTime, locked, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching article edit locks
	 */
	public static List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLockTime(
			lockTime, locked, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByLockTime_First(
			Date lockTime, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByLockTime_First(
			lockTime, locked, orderByComparator);
	}

	/**
	 * Returns the first article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByLockTime_First(
		Date lockTime, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().fetchByLockTime_First(
			lockTime, locked, orderByComparator);
	}

	/**
	 * Returns the last article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public static ArticleEditLock findByLockTime_Last(
			Date lockTime, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByLockTime_Last(
			lockTime, locked, orderByComparator);
	}

	/**
	 * Returns the last article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public static ArticleEditLock fetchByLockTime_Last(
		Date lockTime, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().fetchByLockTime_Last(
			lockTime, locked, orderByComparator);
	}

	/**
	 * Returns the article edit locks before and after the current article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param articleEditLockId the primary key of the current article edit lock
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock[] findByLockTime_PrevAndNext(
			long articleEditLockId, Date lockTime, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByLockTime_PrevAndNext(
			articleEditLockId, lockTime, locked, orderByComparator);
	}

	/**
	 * Removes all the article edit locks where lockTime &lt; &#63; and locked = &#63; from the database.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 */
	public static void removeByLockTime(Date lockTime, boolean locked) {
		getPersistence().removeByLockTime(lockTime, locked);
	}

	/**
	 * Returns the number of article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	public static int countByLockTime(Date lockTime, boolean locked) {
		return getPersistence().countByLockTime(lockTime, locked);
	}

	/**
	 * Caches the article edit lock in the entity cache if it is enabled.
	 *
	 * @param articleEditLock the article edit lock
	 */
	public static void cacheResult(ArticleEditLock articleEditLock) {
		getPersistence().cacheResult(articleEditLock);
	}

	/**
	 * Caches the article edit locks in the entity cache if it is enabled.
	 *
	 * @param articleEditLocks the article edit locks
	 */
	public static void cacheResult(List<ArticleEditLock> articleEditLocks) {
		getPersistence().cacheResult(articleEditLocks);
	}

	/**
	 * Creates a new article edit lock with the primary key. Does not add the article edit lock to the database.
	 *
	 * @param articleEditLockId the primary key for the new article edit lock
	 * @return the new article edit lock
	 */
	public static ArticleEditLock create(long articleEditLockId) {
		return getPersistence().create(articleEditLockId);
	}

	/**
	 * Removes the article edit lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock that was removed
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock remove(long articleEditLockId)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().remove(articleEditLockId);
	}

	public static ArticleEditLock updateImpl(ArticleEditLock articleEditLock) {
		return getPersistence().updateImpl(articleEditLock);
	}

	/**
	 * Returns the article edit lock with the primary key or throws a <code>NoSuchArticleEditLockException</code> if it could not be found.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock findByPrimaryKey(long articleEditLockId)
		throws com.example.article.lock.exception.
			NoSuchArticleEditLockException {

		return getPersistence().findByPrimaryKey(articleEditLockId);
	}

	/**
	 * Returns the article edit lock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock, or <code>null</code> if a article edit lock with the primary key could not be found
	 */
	public static ArticleEditLock fetchByPrimaryKey(long articleEditLockId) {
		return getPersistence().fetchByPrimaryKey(articleEditLockId);
	}

	/**
	 * Returns all the article edit locks.
	 *
	 * @return the article edit locks
	 */
	public static List<ArticleEditLock> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the article edit locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @return the range of article edit locks
	 */
	public static List<ArticleEditLock> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the article edit locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of article edit locks
	 */
	public static List<ArticleEditLock> findAll(
		int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the article edit locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ArticleEditLockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of article edit locks
	 * @param end the upper bound of the range of article edit locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of article edit locks
	 */
	public static List<ArticleEditLock> findAll(
		int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the article edit locks from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of article edit locks.
	 *
	 * @return the number of article edit locks
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ArticleEditLockPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ArticleEditLockPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ArticleEditLockPersistence _persistence;

}