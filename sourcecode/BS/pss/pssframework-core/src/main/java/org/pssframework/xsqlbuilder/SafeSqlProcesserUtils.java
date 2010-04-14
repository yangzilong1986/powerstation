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

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SafeSqlProcesserUtils {
	
	public void processAll(Properties map,SafeSqlProcesser processer) {
		for(Iterator it = map.keySet().iterator();it.hasNext();) {
			String key = (String)it.next();
			String value = map.getProperty(key);
			map.put(key,processer.process(value));
		}
	}
	
}
