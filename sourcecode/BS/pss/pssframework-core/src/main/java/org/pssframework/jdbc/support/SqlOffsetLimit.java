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

public class SqlOffsetLimit {
	public String sql;
	public int offset;
	public int limit;

	public SqlOffsetLimit(String sql, int offset, int limit) {
		this.sql = sql;
		this.offset = offset;
		this.limit = limit;
	}
}