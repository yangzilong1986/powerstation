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
 * sql字符串安全处理接口,防止sql注入攻击
 * @author PSS
 */
public interface SafeSqlProcesser {

	public String process(String value);

}
