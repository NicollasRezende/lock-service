/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.model.impl;

import com.example.article.lock.model.ArticleEditLock;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ArticleEditLock in entity cache.
 *
 * @author Nicollas Rezende
 * @generated
 */
public class ArticleEditLockCacheModel
	implements CacheModel<ArticleEditLock>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ArticleEditLockCacheModel)) {
			return false;
		}

		ArticleEditLockCacheModel articleEditLockCacheModel =
			(ArticleEditLockCacheModel)object;

		if (articleEditLockId == articleEditLockCacheModel.articleEditLockId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, articleEditLockId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{articleEditLockId=");
		sb.append(articleEditLockId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", userFullName=");
		sb.append(userFullName);
		sb.append(", locked=");
		sb.append(locked);
		sb.append(", lockTime=");
		sb.append(lockTime);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ArticleEditLock toEntityModel() {
		ArticleEditLockImpl articleEditLockImpl = new ArticleEditLockImpl();

		articleEditLockImpl.setArticleEditLockId(articleEditLockId);
		articleEditLockImpl.setCompanyId(companyId);
		articleEditLockImpl.setGroupId(groupId);

		if (createDate == Long.MIN_VALUE) {
			articleEditLockImpl.setCreateDate(null);
		}
		else {
			articleEditLockImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			articleEditLockImpl.setModifiedDate(null);
		}
		else {
			articleEditLockImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (articleId == null) {
			articleEditLockImpl.setArticleId("");
		}
		else {
			articleEditLockImpl.setArticleId(articleId);
		}

		articleEditLockImpl.setUserId(userId);

		if (userName == null) {
			articleEditLockImpl.setUserName("");
		}
		else {
			articleEditLockImpl.setUserName(userName);
		}

		if (userFullName == null) {
			articleEditLockImpl.setUserFullName("");
		}
		else {
			articleEditLockImpl.setUserFullName(userFullName);
		}

		articleEditLockImpl.setLocked(locked);

		if (lockTime == Long.MIN_VALUE) {
			articleEditLockImpl.setLockTime(null);
		}
		else {
			articleEditLockImpl.setLockTime(new Date(lockTime));
		}

		articleEditLockImpl.resetOriginalValues();

		return articleEditLockImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		articleEditLockId = objectInput.readLong();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		articleId = objectInput.readUTF();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		userFullName = objectInput.readUTF();

		locked = objectInput.readBoolean();
		lockTime = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(articleEditLockId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(groupId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (articleId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(articleId);
		}

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		if (userFullName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userFullName);
		}

		objectOutput.writeBoolean(locked);
		objectOutput.writeLong(lockTime);
	}

	public long articleEditLockId;
	public long companyId;
	public long groupId;
	public long createDate;
	public long modifiedDate;
	public String articleId;
	public long userId;
	public String userName;
	public String userFullName;
	public boolean locked;
	public long lockTime;

}