/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ArticleLock_ArticleEditLock&quot; database table.
 *
 * @author Nicollas Rezende
 * @see ArticleEditLock
 * @generated
 */
public class ArticleEditLockTable extends BaseTable<ArticleEditLockTable> {

	public static final ArticleEditLockTable INSTANCE =
		new ArticleEditLockTable();

	public final Column<ArticleEditLockTable, Long> articleEditLockId =
		createColumn(
			"articleEditLockId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ArticleEditLockTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, String> articleId = createColumn(
		"articleId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, String> userFullName =
		createColumn(
			"userFullName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, Boolean> locked = createColumn(
		"locked", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ArticleEditLockTable, Date> lockTime = createColumn(
		"lockTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private ArticleEditLockTable() {
		super("ArticleLock_ArticleEditLock", ArticleEditLockTable::new);
	}

}