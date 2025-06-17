/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service.persistence.test;

import com.example.article.lock.exception.NoSuchArticleEditLockException;
import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.service.ArticleEditLockLocalServiceUtil;
import com.example.article.lock.service.persistence.ArticleEditLockPersistence;
import com.example.article.lock.service.persistence.ArticleEditLockUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ArticleEditLockPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.example.article.lock.service"));

	@Before
	public void setUp() {
		_persistence = ArticleEditLockUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ArticleEditLock> iterator = _articleEditLocks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ArticleEditLock articleEditLock = _persistence.create(pk);

		Assert.assertNotNull(articleEditLock);

		Assert.assertEquals(articleEditLock.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		_persistence.remove(newArticleEditLock);

		ArticleEditLock existingArticleEditLock =
			_persistence.fetchByPrimaryKey(newArticleEditLock.getPrimaryKey());

		Assert.assertNull(existingArticleEditLock);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addArticleEditLock();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ArticleEditLock newArticleEditLock = _persistence.create(pk);

		newArticleEditLock.setCompanyId(RandomTestUtil.nextLong());

		newArticleEditLock.setGroupId(RandomTestUtil.nextLong());

		newArticleEditLock.setCreateDate(RandomTestUtil.nextDate());

		newArticleEditLock.setModifiedDate(RandomTestUtil.nextDate());

		newArticleEditLock.setArticleId(RandomTestUtil.randomString());

		newArticleEditLock.setUserId(RandomTestUtil.nextLong());

		newArticleEditLock.setUserName(RandomTestUtil.randomString());

		newArticleEditLock.setUserFullName(RandomTestUtil.randomString());

		newArticleEditLock.setLocked(RandomTestUtil.randomBoolean());

		newArticleEditLock.setLockTime(RandomTestUtil.nextDate());

		_articleEditLocks.add(_persistence.update(newArticleEditLock));

		ArticleEditLock existingArticleEditLock = _persistence.findByPrimaryKey(
			newArticleEditLock.getPrimaryKey());

		Assert.assertEquals(
			existingArticleEditLock.getArticleEditLockId(),
			newArticleEditLock.getArticleEditLockId());
		Assert.assertEquals(
			existingArticleEditLock.getCompanyId(),
			newArticleEditLock.getCompanyId());
		Assert.assertEquals(
			existingArticleEditLock.getGroupId(),
			newArticleEditLock.getGroupId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingArticleEditLock.getCreateDate()),
			Time.getShortTimestamp(newArticleEditLock.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingArticleEditLock.getModifiedDate()),
			Time.getShortTimestamp(newArticleEditLock.getModifiedDate()));
		Assert.assertEquals(
			existingArticleEditLock.getArticleId(),
			newArticleEditLock.getArticleId());
		Assert.assertEquals(
			existingArticleEditLock.getUserId(),
			newArticleEditLock.getUserId());
		Assert.assertEquals(
			existingArticleEditLock.getUserName(),
			newArticleEditLock.getUserName());
		Assert.assertEquals(
			existingArticleEditLock.getUserFullName(),
			newArticleEditLock.getUserFullName());
		Assert.assertEquals(
			existingArticleEditLock.isLocked(), newArticleEditLock.isLocked());
		Assert.assertEquals(
			Time.getShortTimestamp(existingArticleEditLock.getLockTime()),
			Time.getShortTimestamp(newArticleEditLock.getLockTime()));
	}

	@Test
	public void testCountByArticleId() throws Exception {
		_persistence.countByArticleId("", RandomTestUtil.randomBoolean());

		_persistence.countByArticleId("null", RandomTestUtil.randomBoolean());

		_persistence.countByArticleId(
			(String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByUserId(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByLockTime() throws Exception {
		_persistence.countByLockTime(
			RandomTestUtil.nextDate(), RandomTestUtil.randomBoolean());

		_persistence.countByLockTime(
			RandomTestUtil.nextDate(), RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		ArticleEditLock existingArticleEditLock = _persistence.findByPrimaryKey(
			newArticleEditLock.getPrimaryKey());

		Assert.assertEquals(existingArticleEditLock, newArticleEditLock);
	}

	@Test(expected = NoSuchArticleEditLockException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ArticleEditLock> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ArticleLock_ArticleEditLock", "articleEditLockId", true,
			"companyId", true, "groupId", true, "createDate", true,
			"modifiedDate", true, "articleId", true, "userId", true, "userName",
			true, "userFullName", true, "locked", true, "lockTime", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		ArticleEditLock existingArticleEditLock =
			_persistence.fetchByPrimaryKey(newArticleEditLock.getPrimaryKey());

		Assert.assertEquals(existingArticleEditLock, newArticleEditLock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ArticleEditLock missingArticleEditLock = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingArticleEditLock);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ArticleEditLock newArticleEditLock1 = addArticleEditLock();
		ArticleEditLock newArticleEditLock2 = addArticleEditLock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newArticleEditLock1.getPrimaryKey());
		primaryKeys.add(newArticleEditLock2.getPrimaryKey());

		Map<Serializable, ArticleEditLock> articleEditLocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, articleEditLocks.size());
		Assert.assertEquals(
			newArticleEditLock1,
			articleEditLocks.get(newArticleEditLock1.getPrimaryKey()));
		Assert.assertEquals(
			newArticleEditLock2,
			articleEditLocks.get(newArticleEditLock2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ArticleEditLock> articleEditLocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(articleEditLocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ArticleEditLock newArticleEditLock = addArticleEditLock();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newArticleEditLock.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ArticleEditLock> articleEditLocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, articleEditLocks.size());
		Assert.assertEquals(
			newArticleEditLock,
			articleEditLocks.get(newArticleEditLock.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ArticleEditLock> articleEditLocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(articleEditLocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newArticleEditLock.getPrimaryKey());

		Map<Serializable, ArticleEditLock> articleEditLocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, articleEditLocks.size());
		Assert.assertEquals(
			newArticleEditLock,
			articleEditLocks.get(newArticleEditLock.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ArticleEditLockLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<ArticleEditLock>() {

				@Override
				public void performAction(ArticleEditLock articleEditLock) {
					Assert.assertNotNull(articleEditLock);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ArticleEditLock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"articleEditLockId",
				newArticleEditLock.getArticleEditLockId()));

		List<ArticleEditLock> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ArticleEditLock existingArticleEditLock = result.get(0);

		Assert.assertEquals(existingArticleEditLock, newArticleEditLock);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ArticleEditLock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"articleEditLockId", RandomTestUtil.nextLong()));

		List<ArticleEditLock> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ArticleEditLock.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("articleEditLockId"));

		Object newArticleEditLockId = newArticleEditLock.getArticleEditLockId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"articleEditLockId", new Object[] {newArticleEditLockId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingArticleEditLockId = result.get(0);

		Assert.assertEquals(existingArticleEditLockId, newArticleEditLockId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ArticleEditLock.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("articleEditLockId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"articleEditLockId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ArticleEditLock newArticleEditLock = addArticleEditLock();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newArticleEditLock.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		ArticleEditLock newArticleEditLock = addArticleEditLock();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ArticleEditLock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"articleEditLockId",
				newArticleEditLock.getArticleEditLockId()));

		List<ArticleEditLock> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(ArticleEditLock articleEditLock) {
		Assert.assertEquals(
			articleEditLock.getArticleId(),
			ReflectionTestUtil.invoke(
				articleEditLock, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "articleId"));
		Assert.assertEquals(
			Boolean.valueOf(articleEditLock.getLocked()),
			ReflectionTestUtil.<Boolean>invoke(
				articleEditLock, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "locked"));
	}

	protected ArticleEditLock addArticleEditLock() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ArticleEditLock articleEditLock = _persistence.create(pk);

		articleEditLock.setCompanyId(RandomTestUtil.nextLong());

		articleEditLock.setGroupId(RandomTestUtil.nextLong());

		articleEditLock.setCreateDate(RandomTestUtil.nextDate());

		articleEditLock.setModifiedDate(RandomTestUtil.nextDate());

		articleEditLock.setArticleId(RandomTestUtil.randomString());

		articleEditLock.setUserId(RandomTestUtil.nextLong());

		articleEditLock.setUserName(RandomTestUtil.randomString());

		articleEditLock.setUserFullName(RandomTestUtil.randomString());

		articleEditLock.setLocked(RandomTestUtil.randomBoolean());

		articleEditLock.setLockTime(RandomTestUtil.nextDate());

		_articleEditLocks.add(_persistence.update(articleEditLock));

		return articleEditLock;
	}

	private List<ArticleEditLock> _articleEditLocks =
		new ArrayList<ArticleEditLock>();
	private ArticleEditLockPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}