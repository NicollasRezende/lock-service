/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service.persistence.impl;

import com.example.article.lock.exception.NoSuchArticleEditLockException;
import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.model.ArticleEditLockTable;
import com.example.article.lock.model.impl.ArticleEditLockImpl;
import com.example.article.lock.model.impl.ArticleEditLockModelImpl;
import com.example.article.lock.service.persistence.ArticleEditLockPersistence;
import com.example.article.lock.service.persistence.ArticleEditLockUtil;
import com.example.article.lock.service.persistence.impl.constants.ArticleLockPersistenceConstants;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the article edit lock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nicollas Rezende
 * @generated
 */
@Component(service = ArticleEditLockPersistence.class)
public class ArticleEditLockPersistenceImpl
	extends BasePersistenceImpl<ArticleEditLock>
	implements ArticleEditLockPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ArticleEditLockUtil</code> to access the article edit lock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ArticleEditLockImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByArticleId;

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or throws a <code>NoSuchArticleEditLockException</code> if it could not be found.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock findByArticleId(String articleId, boolean locked)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByArticleId(articleId, locked);

		if (articleEditLock == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("articleId=");
			sb.append(articleId);

			sb.append(", locked=");
			sb.append(locked);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchArticleEditLockException(sb.toString());
		}

		return articleEditLock;
	}

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByArticleId(String articleId, boolean locked) {
		return fetchByArticleId(articleId, locked, true);
	}

	/**
	 * Returns the article edit lock where articleId = &#63; and locked = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByArticleId(
		String articleId, boolean locked, boolean useFinderCache) {

		articleId = Objects.toString(articleId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {articleId, locked};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByArticleId, finderArgs, this);
		}

		if (result instanceof ArticleEditLock) {
			ArticleEditLock articleEditLock = (ArticleEditLock)result;

			if (!Objects.equals(articleId, articleEditLock.getArticleId()) ||
				(locked != articleEditLock.isLocked())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
			}

			sb.append(_FINDER_COLUMN_ARTICLEID_LOCKED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindArticleId) {
					queryPos.add(articleId);
				}

				queryPos.add(locked);

				List<ArticleEditLock> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByArticleId, finderArgs, list);
					}
				}
				else {
					ArticleEditLock articleEditLock = list.get(0);

					result = articleEditLock;

					cacheResult(articleEditLock);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ArticleEditLock)result;
		}
	}

	/**
	 * Removes the article edit lock where articleId = &#63; and locked = &#63; from the database.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the article edit lock that was removed
	 */
	@Override
	public ArticleEditLock removeByArticleId(String articleId, boolean locked)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = findByArticleId(articleId, locked);

		return remove(articleEditLock);
	}

	/**
	 * Returns the number of article edit locks where articleId = &#63; and locked = &#63;.
	 *
	 * @param articleId the article ID
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	@Override
	public int countByArticleId(String articleId, boolean locked) {
		ArticleEditLock articleEditLock = fetchByArticleId(articleId, locked);

		if (articleEditLock == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_2 =
		"articleEditLock.articleId = ? AND ";

	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_3 =
		"(articleEditLock.articleId IS NULL OR articleEditLock.articleId = '') AND ";

	private static final String _FINDER_COLUMN_ARTICLEID_LOCKED_2 =
		"articleEditLock.locked = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @return the matching article edit locks
	 */
	@Override
	public List<ArticleEditLock> findByUserId(long userId, boolean locked) {
		return findByUserId(
			userId, locked, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end) {

		return findByUserId(userId, locked, start, end, null);
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
	@Override
	public List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return findByUserId(
			userId, locked, start, end, orderByComparator, true);
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
	@Override
	public List<ArticleEditLock> findByUserId(
		long userId, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId, locked};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {
				userId, locked, start, end, orderByComparator
			};
		}

		List<ArticleEditLock> list = null;

		if (useFinderCache) {
			list = (List<ArticleEditLock>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ArticleEditLock articleEditLock : list) {
					if ((userId != articleEditLock.getUserId()) ||
						(locked != articleEditLock.isLocked())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			sb.append(_FINDER_COLUMN_USERID_LOCKED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ArticleEditLockModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(locked);

				list = (List<ArticleEditLock>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public ArticleEditLock findByUserId_First(
			long userId, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByUserId_First(
			userId, locked, orderByComparator);

		if (articleEditLock != null) {
			return articleEditLock;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", locked=");
		sb.append(locked);

		sb.append("}");

		throw new NoSuchArticleEditLockException(sb.toString());
	}

	/**
	 * Returns the first article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByUserId_First(
		long userId, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		List<ArticleEditLock> list = findByUserId(
			userId, locked, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ArticleEditLock findByUserId_Last(
			long userId, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByUserId_Last(
			userId, locked, orderByComparator);

		if (articleEditLock != null) {
			return articleEditLock;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", locked=");
		sb.append(locked);

		sb.append("}");

		throw new NoSuchArticleEditLockException(sb.toString());
	}

	/**
	 * Returns the last article edit lock in the ordered set where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByUserId_Last(
		long userId, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		int count = countByUserId(userId, locked);

		if (count == 0) {
			return null;
		}

		List<ArticleEditLock> list = findByUserId(
			userId, locked, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ArticleEditLock[] findByUserId_PrevAndNext(
			long articleEditLockId, long userId, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = findByPrimaryKey(articleEditLockId);

		Session session = null;

		try {
			session = openSession();

			ArticleEditLock[] array = new ArticleEditLockImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, articleEditLock, userId, locked, orderByComparator,
				true);

			array[1] = articleEditLock;

			array[2] = getByUserId_PrevAndNext(
				session, articleEditLock, userId, locked, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ArticleEditLock getByUserId_PrevAndNext(
		Session session, ArticleEditLock articleEditLock, long userId,
		boolean locked, OrderByComparator<ArticleEditLock> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		sb.append(_FINDER_COLUMN_USERID_LOCKED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ArticleEditLockModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(locked);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						articleEditLock)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ArticleEditLock> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the article edit locks where userId = &#63; and locked = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 */
	@Override
	public void removeByUserId(long userId, boolean locked) {
		for (ArticleEditLock articleEditLock :
				findByUserId(
					userId, locked, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(articleEditLock);
		}
	}

	/**
	 * Returns the number of article edit locks where userId = &#63; and locked = &#63;.
	 *
	 * @param userId the user ID
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	@Override
	public int countByUserId(long userId, boolean locked) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId, locked};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ARTICLEEDITLOCK_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			sb.append(_FINDER_COLUMN_USERID_LOCKED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(locked);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"articleEditLock.userId = ? AND ";

	private static final String _FINDER_COLUMN_USERID_LOCKED_2 =
		"articleEditLock.locked = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the article edit locks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching article edit locks
	 */
	@Override
	public List<ArticleEditLock> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
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
	@Override
	public List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
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
	@Override
	public List<ArticleEditLock> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<ArticleEditLock> list = null;

		if (useFinderCache) {
			list = (List<ArticleEditLock>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ArticleEditLock articleEditLock : list) {
					if (groupId != articleEditLock.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ArticleEditLockModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<ArticleEditLock>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock findByGroupId_First(
			long groupId, OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByGroupId_First(
			groupId, orderByComparator);

		if (articleEditLock != null) {
			return articleEditLock;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchArticleEditLockException(sb.toString());
	}

	/**
	 * Returns the first article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByGroupId_First(
		long groupId, OrderByComparator<ArticleEditLock> orderByComparator) {

		List<ArticleEditLock> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock
	 * @throws NoSuchArticleEditLockException if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock findByGroupId_Last(
			long groupId, OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (articleEditLock != null) {
			return articleEditLock;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchArticleEditLockException(sb.toString());
	}

	/**
	 * Returns the last article edit lock in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByGroupId_Last(
		long groupId, OrderByComparator<ArticleEditLock> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ArticleEditLock> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ArticleEditLock[] findByGroupId_PrevAndNext(
			long articleEditLockId, long groupId,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = findByPrimaryKey(articleEditLockId);

		Session session = null;

		try {
			session = openSession();

			ArticleEditLock[] array = new ArticleEditLockImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, articleEditLock, groupId, orderByComparator, true);

			array[1] = articleEditLock;

			array[2] = getByGroupId_PrevAndNext(
				session, articleEditLock, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ArticleEditLock getByGroupId_PrevAndNext(
		Session session, ArticleEditLock articleEditLock, long groupId,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ArticleEditLockModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						articleEditLock)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ArticleEditLock> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the article edit locks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ArticleEditLock articleEditLock :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(articleEditLock);
		}
	}

	/**
	 * Returns the number of article edit locks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching article edit locks
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ARTICLEEDITLOCK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"articleEditLock.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByLockTime;
	private FinderPath _finderPathWithPaginationCountByLockTime;

	/**
	 * Returns all the article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @return the matching article edit locks
	 */
	@Override
	public List<ArticleEditLock> findByLockTime(Date lockTime, boolean locked) {
		return findByLockTime(
			lockTime, locked, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end) {

		return findByLockTime(lockTime, locked, start, end, null);
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
	@Override
	public List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return findByLockTime(
			lockTime, locked, start, end, orderByComparator, true);
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
	@Override
	public List<ArticleEditLock> findByLockTime(
		Date lockTime, boolean locked, int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLockTime;
		finderArgs = new Object[] {
			_getTime(lockTime), locked, start, end, orderByComparator
		};

		List<ArticleEditLock> list = null;

		if (useFinderCache) {
			list = (List<ArticleEditLock>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ArticleEditLock articleEditLock : list) {
					if ((lockTime.getTime() <= articleEditLock.getLockTime(
						).getTime()) ||
						(locked != articleEditLock.isLocked())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

			boolean bindLockTime = false;

			if (lockTime == null) {
				sb.append(_FINDER_COLUMN_LOCKTIME_LOCKTIME_1);
			}
			else {
				bindLockTime = true;

				sb.append(_FINDER_COLUMN_LOCKTIME_LOCKTIME_2);
			}

			sb.append(_FINDER_COLUMN_LOCKTIME_LOCKED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ArticleEditLockModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLockTime) {
					queryPos.add(new Timestamp(lockTime.getTime()));
				}

				queryPos.add(locked);

				list = (List<ArticleEditLock>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public ArticleEditLock findByLockTime_First(
			Date lockTime, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByLockTime_First(
			lockTime, locked, orderByComparator);

		if (articleEditLock != null) {
			return articleEditLock;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lockTime<");
		sb.append(lockTime);

		sb.append(", locked=");
		sb.append(locked);

		sb.append("}");

		throw new NoSuchArticleEditLockException(sb.toString());
	}

	/**
	 * Returns the first article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByLockTime_First(
		Date lockTime, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		List<ArticleEditLock> list = findByLockTime(
			lockTime, locked, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ArticleEditLock findByLockTime_Last(
			Date lockTime, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByLockTime_Last(
			lockTime, locked, orderByComparator);

		if (articleEditLock != null) {
			return articleEditLock;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lockTime<");
		sb.append(lockTime);

		sb.append(", locked=");
		sb.append(locked);

		sb.append("}");

		throw new NoSuchArticleEditLockException(sb.toString());
	}

	/**
	 * Returns the last article edit lock in the ordered set where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching article edit lock, or <code>null</code> if a matching article edit lock could not be found
	 */
	@Override
	public ArticleEditLock fetchByLockTime_Last(
		Date lockTime, boolean locked,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		int count = countByLockTime(lockTime, locked);

		if (count == 0) {
			return null;
		}

		List<ArticleEditLock> list = findByLockTime(
			lockTime, locked, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ArticleEditLock[] findByLockTime_PrevAndNext(
			long articleEditLockId, Date lockTime, boolean locked,
			OrderByComparator<ArticleEditLock> orderByComparator)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = findByPrimaryKey(articleEditLockId);

		Session session = null;

		try {
			session = openSession();

			ArticleEditLock[] array = new ArticleEditLockImpl[3];

			array[0] = getByLockTime_PrevAndNext(
				session, articleEditLock, lockTime, locked, orderByComparator,
				true);

			array[1] = articleEditLock;

			array[2] = getByLockTime_PrevAndNext(
				session, articleEditLock, lockTime, locked, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ArticleEditLock getByLockTime_PrevAndNext(
		Session session, ArticleEditLock articleEditLock, Date lockTime,
		boolean locked, OrderByComparator<ArticleEditLock> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ARTICLEEDITLOCK_WHERE);

		boolean bindLockTime = false;

		if (lockTime == null) {
			sb.append(_FINDER_COLUMN_LOCKTIME_LOCKTIME_1);
		}
		else {
			bindLockTime = true;

			sb.append(_FINDER_COLUMN_LOCKTIME_LOCKTIME_2);
		}

		sb.append(_FINDER_COLUMN_LOCKTIME_LOCKED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ArticleEditLockModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindLockTime) {
			queryPos.add(new Timestamp(lockTime.getTime()));
		}

		queryPos.add(locked);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						articleEditLock)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ArticleEditLock> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the article edit locks where lockTime &lt; &#63; and locked = &#63; from the database.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 */
	@Override
	public void removeByLockTime(Date lockTime, boolean locked) {
		for (ArticleEditLock articleEditLock :
				findByLockTime(
					lockTime, locked, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(articleEditLock);
		}
	}

	/**
	 * Returns the number of article edit locks where lockTime &lt; &#63; and locked = &#63;.
	 *
	 * @param lockTime the lock time
	 * @param locked the locked
	 * @return the number of matching article edit locks
	 */
	@Override
	public int countByLockTime(Date lockTime, boolean locked) {
		FinderPath finderPath = _finderPathWithPaginationCountByLockTime;

		Object[] finderArgs = new Object[] {_getTime(lockTime), locked};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ARTICLEEDITLOCK_WHERE);

			boolean bindLockTime = false;

			if (lockTime == null) {
				sb.append(_FINDER_COLUMN_LOCKTIME_LOCKTIME_1);
			}
			else {
				bindLockTime = true;

				sb.append(_FINDER_COLUMN_LOCKTIME_LOCKTIME_2);
			}

			sb.append(_FINDER_COLUMN_LOCKTIME_LOCKED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLockTime) {
					queryPos.add(new Timestamp(lockTime.getTime()));
				}

				queryPos.add(locked);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_LOCKTIME_LOCKTIME_1 =
		"articleEditLock.lockTime IS NULL AND ";

	private static final String _FINDER_COLUMN_LOCKTIME_LOCKTIME_2 =
		"articleEditLock.lockTime < ? AND ";

	private static final String _FINDER_COLUMN_LOCKTIME_LOCKED_2 =
		"articleEditLock.locked = ?";

	public ArticleEditLockPersistenceImpl() {
		setModelClass(ArticleEditLock.class);

		setModelImplClass(ArticleEditLockImpl.class);
		setModelPKClass(long.class);

		setTable(ArticleEditLockTable.INSTANCE);
	}

	/**
	 * Caches the article edit lock in the entity cache if it is enabled.
	 *
	 * @param articleEditLock the article edit lock
	 */
	@Override
	public void cacheResult(ArticleEditLock articleEditLock) {
		entityCache.putResult(
			ArticleEditLockImpl.class, articleEditLock.getPrimaryKey(),
			articleEditLock);

		finderCache.putResult(
			_finderPathFetchByArticleId,
			new Object[] {
				articleEditLock.getArticleId(), articleEditLock.isLocked()
			},
			articleEditLock);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the article edit locks in the entity cache if it is enabled.
	 *
	 * @param articleEditLocks the article edit locks
	 */
	@Override
	public void cacheResult(List<ArticleEditLock> articleEditLocks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (articleEditLocks.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ArticleEditLock articleEditLock : articleEditLocks) {
			if (entityCache.getResult(
					ArticleEditLockImpl.class,
					articleEditLock.getPrimaryKey()) == null) {

				cacheResult(articleEditLock);
			}
		}
	}

	/**
	 * Clears the cache for all article edit locks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ArticleEditLockImpl.class);

		finderCache.clearCache(ArticleEditLockImpl.class);
	}

	/**
	 * Clears the cache for the article edit lock.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ArticleEditLock articleEditLock) {
		entityCache.removeResult(ArticleEditLockImpl.class, articleEditLock);
	}

	@Override
	public void clearCache(List<ArticleEditLock> articleEditLocks) {
		for (ArticleEditLock articleEditLock : articleEditLocks) {
			entityCache.removeResult(
				ArticleEditLockImpl.class, articleEditLock);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ArticleEditLockImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ArticleEditLockImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ArticleEditLockModelImpl articleEditLockModelImpl) {

		Object[] args = new Object[] {
			articleEditLockModelImpl.getArticleId(),
			articleEditLockModelImpl.isLocked()
		};

		finderCache.putResult(
			_finderPathFetchByArticleId, args, articleEditLockModelImpl);
	}

	/**
	 * Creates a new article edit lock with the primary key. Does not add the article edit lock to the database.
	 *
	 * @param articleEditLockId the primary key for the new article edit lock
	 * @return the new article edit lock
	 */
	@Override
	public ArticleEditLock create(long articleEditLockId) {
		ArticleEditLock articleEditLock = new ArticleEditLockImpl();

		articleEditLock.setNew(true);
		articleEditLock.setPrimaryKey(articleEditLockId);

		articleEditLock.setCompanyId(CompanyThreadLocal.getCompanyId());

		return articleEditLock;
	}

	/**
	 * Removes the article edit lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock that was removed
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	@Override
	public ArticleEditLock remove(long articleEditLockId)
		throws NoSuchArticleEditLockException {

		return remove((Serializable)articleEditLockId);
	}

	/**
	 * Removes the article edit lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the article edit lock
	 * @return the article edit lock that was removed
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	@Override
	public ArticleEditLock remove(Serializable primaryKey)
		throws NoSuchArticleEditLockException {

		Session session = null;

		try {
			session = openSession();

			ArticleEditLock articleEditLock = (ArticleEditLock)session.get(
				ArticleEditLockImpl.class, primaryKey);

			if (articleEditLock == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchArticleEditLockException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(articleEditLock);
		}
		catch (NoSuchArticleEditLockException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ArticleEditLock removeImpl(ArticleEditLock articleEditLock) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(articleEditLock)) {
				articleEditLock = (ArticleEditLock)session.get(
					ArticleEditLockImpl.class,
					articleEditLock.getPrimaryKeyObj());
			}

			if (articleEditLock != null) {
				session.delete(articleEditLock);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (articleEditLock != null) {
			clearCache(articleEditLock);
		}

		return articleEditLock;
	}

	@Override
	public ArticleEditLock updateImpl(ArticleEditLock articleEditLock) {
		boolean isNew = articleEditLock.isNew();

		if (!(articleEditLock instanceof ArticleEditLockModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(articleEditLock.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					articleEditLock);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in articleEditLock proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ArticleEditLock implementation " +
					articleEditLock.getClass());
		}

		ArticleEditLockModelImpl articleEditLockModelImpl =
			(ArticleEditLockModelImpl)articleEditLock;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (articleEditLock.getCreateDate() == null)) {
			if (serviceContext == null) {
				articleEditLock.setCreateDate(date);
			}
			else {
				articleEditLock.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!articleEditLockModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				articleEditLock.setModifiedDate(date);
			}
			else {
				articleEditLock.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(articleEditLock);
			}
			else {
				articleEditLock = (ArticleEditLock)session.merge(
					articleEditLock);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ArticleEditLockImpl.class, articleEditLockModelImpl, false, true);

		cacheUniqueFindersCache(articleEditLockModelImpl);

		if (isNew) {
			articleEditLock.setNew(false);
		}

		articleEditLock.resetOriginalValues();

		return articleEditLock;
	}

	/**
	 * Returns the article edit lock with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	@Override
	public ArticleEditLock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchArticleEditLockException {

		ArticleEditLock articleEditLock = fetchByPrimaryKey(primaryKey);

		if (articleEditLock == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchArticleEditLockException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return articleEditLock;
	}

	/**
	 * Returns the article edit lock with the primary key or throws a <code>NoSuchArticleEditLockException</code> if it could not be found.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock
	 * @throws NoSuchArticleEditLockException if a article edit lock with the primary key could not be found
	 */
	@Override
	public ArticleEditLock findByPrimaryKey(long articleEditLockId)
		throws NoSuchArticleEditLockException {

		return findByPrimaryKey((Serializable)articleEditLockId);
	}

	/**
	 * Returns the article edit lock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleEditLockId the primary key of the article edit lock
	 * @return the article edit lock, or <code>null</code> if a article edit lock with the primary key could not be found
	 */
	@Override
	public ArticleEditLock fetchByPrimaryKey(long articleEditLockId) {
		return fetchByPrimaryKey((Serializable)articleEditLockId);
	}

	/**
	 * Returns all the article edit locks.
	 *
	 * @return the article edit locks
	 */
	@Override
	public List<ArticleEditLock> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ArticleEditLock> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<ArticleEditLock> findAll(
		int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<ArticleEditLock> findAll(
		int start, int end,
		OrderByComparator<ArticleEditLock> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ArticleEditLock> list = null;

		if (useFinderCache) {
			list = (List<ArticleEditLock>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ARTICLEEDITLOCK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ARTICLEEDITLOCK;

				sql = sql.concat(ArticleEditLockModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ArticleEditLock>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the article edit locks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ArticleEditLock articleEditLock : findAll()) {
			remove(articleEditLock);
		}
	}

	/**
	 * Returns the number of article edit locks.
	 *
	 * @return the number of article edit locks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ARTICLEEDITLOCK);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "articleEditLockId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ARTICLEEDITLOCK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ArticleEditLockModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the article edit lock persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByArticleId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByArticleId",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"articleId", "locked"}, true);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "locked"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"userId", "locked"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"userId", "locked"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByLockTime = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLockTime",
			new String[] {
				Date.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"lockTime", "locked"}, true);

		_finderPathWithPaginationCountByLockTime = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLockTime",
			new String[] {Date.class.getName(), Boolean.class.getName()},
			new String[] {"lockTime", "locked"}, false);

		ArticleEditLockUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ArticleEditLockUtil.setPersistence(null);

		entityCache.removeCache(ArticleEditLockImpl.class.getName());
	}

	@Override
	@Reference(
		target = ArticleLockPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ArticleLockPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ArticleLockPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_ARTICLEEDITLOCK =
		"SELECT articleEditLock FROM ArticleEditLock articleEditLock";

	private static final String _SQL_SELECT_ARTICLEEDITLOCK_WHERE =
		"SELECT articleEditLock FROM ArticleEditLock articleEditLock WHERE ";

	private static final String _SQL_COUNT_ARTICLEEDITLOCK =
		"SELECT COUNT(articleEditLock) FROM ArticleEditLock articleEditLock";

	private static final String _SQL_COUNT_ARTICLEEDITLOCK_WHERE =
		"SELECT COUNT(articleEditLock) FROM ArticleEditLock articleEditLock WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "articleEditLock.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ArticleEditLock exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ArticleEditLock exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ArticleEditLockPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}