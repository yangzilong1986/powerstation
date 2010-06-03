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
package org.pssframework.xsqlbuilder;

/**
 * 支持Ibatis类似的语法
 * <pre>
 * 		String sql = "select * from user where 1=1"
 *			+"/~ and username = #username#~/"
 *			+"/~ and pwd = '$password$'~/";
 * </pre>
 * @author PSS
 *
 */
public class IbatisStyleXsqlBuilder extends XsqlBuilder {

	public IbatisStyleXsqlBuilder() {
		setAsIbatisStyle();
	}

	public IbatisStyleXsqlBuilder(boolean isRemoveEmptyStrings, SafeSqlProcesser safeSqlProcesser) {
		super(isRemoveEmptyStrings, safeSqlProcesser);
		setAsIbatisStyle();
	}

	public IbatisStyleXsqlBuilder(boolean isRemoveEmptyStrings) {
		super(isRemoveEmptyStrings);
		setAsIbatisStyle();
	}

	public IbatisStyleXsqlBuilder(SafeSqlProcesser safeSqlProcesser) {
		super(safeSqlProcesser);
		setAsIbatisStyle();
	}

	private void setAsIbatisStyle() {
		markKeyEndChar = "#";
		markKeyStartChar = "#";

		replaceKeyEndChar = "$";
		replaceKeyStartChar = "$";
	}

}
