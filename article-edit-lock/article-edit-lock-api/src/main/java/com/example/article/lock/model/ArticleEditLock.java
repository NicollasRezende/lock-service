/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ArticleEditLock service. Represents a row in the &quot;ArticleLock_ArticleEditLock&quot; database table, with each column mapped to a property of this class.
 *
 * @author Nicollas Rezende
 * @see ArticleEditLockModel
 * @generated
 */
@ImplementationClassName(
	"com.example.article.lock.model.impl.ArticleEditLockImpl"
)
@ProviderType
public interface ArticleEditLock extends ArticleEditLockModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.example.article.lock.model.impl.ArticleEditLockImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ArticleEditLock, Long>
		ARTICLE_EDIT_LOCK_ID_ACCESSOR = new Accessor<ArticleEditLock, Long>() {

			@Override
			public Long get(ArticleEditLock articleEditLock) {
				return articleEditLock.getArticleEditLockId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ArticleEditLock> getTypeClass() {
				return ArticleEditLock.class;
			}

		};

}