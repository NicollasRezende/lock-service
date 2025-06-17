/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.article.lock.service.persistence.impl;

import com.example.article.lock.model.ArticleEditLockTable;
import com.example.article.lock.model.impl.ArticleEditLockImpl;
import com.example.article.lock.model.impl.ArticleEditLockModelImpl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from ArticleEditLock.
 *
 * @author Nicollas Rezende
 * @generated
 */
@Component(
	property = {
		"class.name=com.example.article.lock.model.impl.ArticleEditLockImpl",
		"table.name=ArticleLock_ArticleEditLock"
	},
	service = ArgumentsResolver.class
)
public class ArticleEditLockModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		ArticleEditLockModelImpl articleEditLockModelImpl =
			(ArticleEditLockModelImpl)baseModel;

		long columnBitmask = articleEditLockModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(articleEditLockModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					articleEditLockModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(articleEditLockModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return ArticleEditLockImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return ArticleEditLockTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		ArticleEditLockModelImpl articleEditLockModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = articleEditLockModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = articleEditLockModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}