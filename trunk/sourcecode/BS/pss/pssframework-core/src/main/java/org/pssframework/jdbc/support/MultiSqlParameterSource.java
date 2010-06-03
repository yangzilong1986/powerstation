/*******************************************************************************
 * Copyright (c) 2010 PSS Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PSS Corporation - initial API and implementation
 *******************************************************************************/
package org.pssframework.jdbc.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class MultiSqlParameterSource implements SqlParameterSource {
	List<SqlParameterSource> paramSources = new ArrayList(2);

	public int getSqlType(String paramName) {
		for (SqlParameterSource sps : paramSources) {
			int value = sps.getSqlType(paramName);
			if (value != TYPE_UNKNOWN)
				return value;
		}
		return 0;
	}

	public String getTypeName(String paramName) {
		for (SqlParameterSource sps : paramSources) {
			String value = sps.getTypeName(paramName);
			if (value != null)
				return value;
		}
		return null;
	}

	public Object getValue(String paramName) throws IllegalArgumentException {
		for (SqlParameterSource sps : paramSources) {
			Object value = sps.getValue(paramName);
			if (value != null)
				return value;
		}
		return null;
	}

	public boolean hasValue(String paramName) {
		for (SqlParameterSource sps : paramSources) {
			if (sps.hasValue(paramName))
				return true;
		}
		return false;
	}

}
