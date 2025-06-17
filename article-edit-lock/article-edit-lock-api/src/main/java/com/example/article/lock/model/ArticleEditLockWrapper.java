/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ArticleEditLock}.
 * </p>
 *
 * @author Nicollas Rezende
 * @see ArticleEditLock
 * @generated
 */
public class ArticleEditLockWrapper
	extends BaseModelWrapper<ArticleEditLock>
	implements ArticleEditLock, ModelWrapper<ArticleEditLock> {

	public ArticleEditLockWrapper(ArticleEditLock articleEditLock) {
		super(articleEditLock);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("articleEditLockId", getArticleEditLockId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("articleId", getArticleId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("userFullName", getUserFullName());
		attributes.put("locked", isLocked());
		attributes.put("lockTime", getLockTime());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long articleEditLockId = (Long)attributes.get("articleEditLockId");

		if (articleEditLockId != null) {
			setArticleEditLockId(articleEditLockId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String articleId = (String)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		String userFullName = (String)attributes.get("userFullName");

		if (userFullName != null) {
			setUserFullName(userFullName);
		}

		Boolean locked = (Boolean)attributes.get("locked");

		if (locked != null) {
			setLocked(locked);
		}

		Date lockTime = (Date)attributes.get("lockTime");

		if (lockTime != null) {
			setLockTime(lockTime);
		}
	}

	@Override
	public ArticleEditLock cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the article edit lock ID of this article edit lock.
	 *
	 * @return the article edit lock ID of this article edit lock
	 */
	@Override
	public long getArticleEditLockId() {
		return model.getArticleEditLockId();
	}

	/**
	 * Returns the article ID of this article edit lock.
	 *
	 * @return the article ID of this article edit lock
	 */
	@Override
	public String getArticleId() {
		return model.getArticleId();
	}

	/**
	 * Returns the company ID of this article edit lock.
	 *
	 * @return the company ID of this article edit lock
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this article edit lock.
	 *
	 * @return the create date of this article edit lock
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this article edit lock.
	 *
	 * @return the group ID of this article edit lock
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the locked of this article edit lock.
	 *
	 * @return the locked of this article edit lock
	 */
	@Override
	public boolean getLocked() {
		return model.getLocked();
	}

	/**
	 * Returns the lock time of this article edit lock.
	 *
	 * @return the lock time of this article edit lock
	 */
	@Override
	public Date getLockTime() {
		return model.getLockTime();
	}

	/**
	 * Returns the modified date of this article edit lock.
	 *
	 * @return the modified date of this article edit lock
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this article edit lock.
	 *
	 * @return the primary key of this article edit lock
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user full name of this article edit lock.
	 *
	 * @return the user full name of this article edit lock
	 */
	@Override
	public String getUserFullName() {
		return model.getUserFullName();
	}

	/**
	 * Returns the user ID of this article edit lock.
	 *
	 * @return the user ID of this article edit lock
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this article edit lock.
	 *
	 * @return the user name of this article edit lock
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this article edit lock.
	 *
	 * @return the user uuid of this article edit lock
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this article edit lock is locked.
	 *
	 * @return <code>true</code> if this article edit lock is locked; <code>false</code> otherwise
	 */
	@Override
	public boolean isLocked() {
		return model.isLocked();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the article edit lock ID of this article edit lock.
	 *
	 * @param articleEditLockId the article edit lock ID of this article edit lock
	 */
	@Override
	public void setArticleEditLockId(long articleEditLockId) {
		model.setArticleEditLockId(articleEditLockId);
	}

	/**
	 * Sets the article ID of this article edit lock.
	 *
	 * @param articleId the article ID of this article edit lock
	 */
	@Override
	public void setArticleId(String articleId) {
		model.setArticleId(articleId);
	}

	/**
	 * Sets the company ID of this article edit lock.
	 *
	 * @param companyId the company ID of this article edit lock
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this article edit lock.
	 *
	 * @param createDate the create date of this article edit lock
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this article edit lock.
	 *
	 * @param groupId the group ID of this article edit lock
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets whether this article edit lock is locked.
	 *
	 * @param locked the locked of this article edit lock
	 */
	@Override
	public void setLocked(boolean locked) {
		model.setLocked(locked);
	}

	/**
	 * Sets the lock time of this article edit lock.
	 *
	 * @param lockTime the lock time of this article edit lock
	 */
	@Override
	public void setLockTime(Date lockTime) {
		model.setLockTime(lockTime);
	}

	/**
	 * Sets the modified date of this article edit lock.
	 *
	 * @param modifiedDate the modified date of this article edit lock
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this article edit lock.
	 *
	 * @param primaryKey the primary key of this article edit lock
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user full name of this article edit lock.
	 *
	 * @param userFullName the user full name of this article edit lock
	 */
	@Override
	public void setUserFullName(String userFullName) {
		model.setUserFullName(userFullName);
	}

	/**
	 * Sets the user ID of this article edit lock.
	 *
	 * @param userId the user ID of this article edit lock
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this article edit lock.
	 *
	 * @param userName the user name of this article edit lock
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this article edit lock.
	 *
	 * @param userUuid the user uuid of this article edit lock
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected ArticleEditLockWrapper wrap(ArticleEditLock articleEditLock) {
		return new ArticleEditLockWrapper(articleEditLock);
	}

}