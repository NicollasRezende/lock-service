/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service.persistence;

import com.example.article.lock.exception.NoSuchArticleEditLockException;
import com.example.article.lock.model.ArticleEditLock;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the article edit lock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nicollas Rezende
 * @see ArticleEditLockUtil
 * @generated
 */
@ProviderType
public interface ArticleEditLockPersistence
	extends BasePersistence<ArticleEditLock> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ArticleEditLockUtil} to access the article edit lock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or throws a <code>NoSuchArticleEditLockException</code> if it could not be found.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByArticleId(String articleId, boolean locked)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByArticleId(String articleId, boolean locked);

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByArticleId(
		String articleId, boolean locked, boolean useFinderCache);

	/**
	 * Removes the article edit lock where articleId = &#63; and locked = &#63; from the database.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the article edit lock that was removed
	 */
	public ArticleEditLock removeByArticleId(String articleId, boolean locked)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the number of article edit locks where articleId = &#63; and locked = &#63;.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	public int countByArticleId(String articleId, boolean locked);

	/**
	 * Returns all the article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @return the matching article edit locks
	 */
	public java.util.List<ArticleEditLock> findByUserId(
		long userId, boolean locked);

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
	public java.util.List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end);

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
	public java.util.List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

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
	public java.util.List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByUserId_First(
			long userId, boolean locked,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the first article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByUserId_First(
		long userId, boolean locked,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

	/**
	 * Returns the last article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByUserId_Last(
			long userId, boolean locked,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the last article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByUserId_Last(
		long userId, boolean locked,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

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
	public ArticleEditLock[] findByUserId_PrevAndNext(
			long articleEditLockId, long userId, boolean locked,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Removes all the article edit locks where userId = &#63; and locked = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 */
	public void removeByUserId(long userId, boolean locked);

	/**
	 * Returns the number of article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	public int countByUserId(long userId, boolean locked);

	/**
	 * Returns all the article edit locks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching article edit locks
	 */
	public java.util.List<ArticleEditLock> findByGroupId(long groupId);

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
	public java.util.List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

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
	public java.util.List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the first article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

	/**
	 * Returns the last article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the last article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

	/**
	 * Returns the article edit locks before and after the current article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param articleEditLockId the primary key of the current article edit lock
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public ArticleEditLock[] findByGroupId_PrevAndNext(
			long articleEditLockId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Removes all the article edit locks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of article edit locks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching article edit locks
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @return the matching article edit locks
	 */
	public java.util.List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked);

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
	public java.util.List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end);

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
	public java.util.List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

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
	public java.util.List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByLockTime_First(
			Date lockTime, boolean locked,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the first article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByLockTime_First(
		Date lockTime, boolean locked,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

	/**
	 * Returns the last article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	public ArticleEditLock findByLockTime_Last(
			Date lockTime, boolean locked,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the last article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	public ArticleEditLock fetchByLockTime_Last(
		Date lockTime, boolean locked,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

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
	public ArticleEditLock[] findByLockTime_PrevAndNext(
			long articleEditLockId, Date lockTime, boolean locked,
			com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
				orderByComparator)
		throws NoSuchArticleEditLockException;

	/**
	 * Removes all the article edit locks where lockTime &lt; &#63; and locked = &#63; from the database.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 */
	public void removeByLockTime(Date lockTime, boolean locked);

	/**
	 * Returns the number of article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	public int countByLockTime(Date lockTime, boolean locked);

	/**
	 * Caches the article edit lock in the entity cache if it is enabled.
	 *
	 * @param articleEditLock the article edit lock
	 */
	public void cacheResult(ArticleEditLock articleEditLock);

	/**
	 * Caches the article edit locks in the entity cache if it is enabled.
	 *
	 * @param articleEditLocks the article edit locks
	 */
	public void cacheResult(java.util.List<ArticleEditLock> articleEditLocks);

	/**
	 * Creates a new article edit lock with the primary key. Does not add the article edit lock to the database.
	 *
	 * @param articleEditLockId the primary key for the new article edit lock
	 * @return the new article edit lock
	 */
	public ArticleEditLock create(long articleEditLockId);

	/**
	 * Removes the article edit lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock that was removed
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public ArticleEditLock remove(long articleEditLockId)
		throws NoSuchArticleEditLockException;

	public ArticleEditLock updateImpl(ArticleEditLock articleEditLock);

	/**
	 * Returns the article edit lock with the primary key or throws a <code>NoSuchArticleEditLockException</code> if it could not be found.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	public ArticleEditLock findByPrimaryKey(long articleEditLockId)
		throws NoSuchArticleEditLockException;

	/**
	 * Returns the article edit lock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock, or <code>null</code> if a article edit lock with the primary key could not be found
	 */
	public ArticleEditLock fetchByPrimaryKey(long articleEditLockId);

	/**
	 * Returns all the article edit locks.
	 *
	 * @return the article edit locks
	 */
	public java.util.List<ArticleEditLock> findAll();

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
	public java.util.List<ArticleEditLock> findAll(int start, int end);

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
	public java.util.List<ArticleEditLock> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator);

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
	public java.util.List<ArticleEditLock> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ArticleEditLock>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the article edit locks from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of article edit locks.
	 *
	 * @return the number of article edit locks
	 */
	public int countAll();

}