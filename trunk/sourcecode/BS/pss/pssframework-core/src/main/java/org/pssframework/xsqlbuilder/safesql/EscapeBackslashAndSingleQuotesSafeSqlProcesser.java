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
package org.pssframework.xsqlbuilder.safesql;

import org.pssframework.xsqlbuilder.SafeSqlProcesser;

/**
 * 过滤(单引号,反斜杠)为(两个单引号,两个反斜杠)的SafeSqlFilter<p>
 * 适用数据库(Mysql,PostgreSql)
 * @author PSS
 *
 */
public class EscapeBackslashAndSingleQuotesSafeSqlProcesser implements SafeSqlProcesser {

	public String process(String value) {
		if (value == null)
			return null;
		return value.replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\");
	}

}
