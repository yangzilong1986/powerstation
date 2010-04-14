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
package org.pssframework.datamodifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DefaultUtils {
	public static DateFormat defaultDateFormat(String dateFormat,String defaultDateFormat) {
		DateFormat df = new SimpleDateFormat(defaultString(dateFormat, defaultDateFormat));
		return df;
	}

	public static String defaultString(String value, String defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		return value;
	}
}
