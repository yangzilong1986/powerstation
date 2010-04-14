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
 * 直接返回,不做任何处理
 * @author PSS
 *
 */
public class DirectReturnSafeSqlProcesser implements SafeSqlProcesser {

	public static SafeSqlProcesser INSTANCE = new DirectReturnSafeSqlProcesser();

	public String process(String value) {
		return value;
	}

}
