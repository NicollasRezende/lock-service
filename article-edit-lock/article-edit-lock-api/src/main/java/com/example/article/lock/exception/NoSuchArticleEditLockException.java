/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.example.article.lock.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Nicollas Rezende
 */
public class NoSuchArticleEditLockException extends NoSuchModelException {

	public NoSuchArticleEditLockException() {
	}

	public NoSuchArticleEditLockException(String msg) {
		super(msg);
	}

	public NoSuchArticleEditLockException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchArticleEditLockException(Throwable throwable) {
		super(throwable);
	}

}