/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.model.impl;

import com.example.article.lock.model.ArticleEditLock;
import com.example.article.lock.service.ArticleEditLockLocalServiceUtil;

/**
 * The extended model base implementation for the ArticleEditLock service. Represents a row in the &quot;ArticleLock_ArticleEditLock&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ArticleEditLockImpl}.
 * </p>
 *
 * @author Nicollas Rezende
 * @see ArticleEditLockImpl
 * @see ArticleEditLock
 * @generated
 */
public abstract class ArticleEditLockBaseImpl
	extends ArticleEditLockModelImpl implements ArticleEditLock {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a article edit lock model instance should use the <code>ArticleEditLock</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			ArticleEditLockLocalServiceUtil.addArticleEditLock(this);
		}
		else {
			ArticleEditLockLocalServiceUtil.updateArticleEditLock(this);
		}
	}

}