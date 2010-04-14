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

import java.util.Map;

import org.pssframework.xsqlbuilder.MapAndObject.FastPropertyUtils;


class ObjectUtils {
	
	public static Object getProperty(Object obj,String key) {
		if(obj == null) return null;
		if(obj instanceof Map) {
			Map map = (Map)obj;
			return map.get(key);
		}else {
			return FastPropertyUtils.getBeanPropertyValue(obj, key);
		}
	}
	
}
