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

package org.pssframework.datamodifier.modifier;

import java.text.DateFormat;
import java.util.Date;

import org.pssframework.datamodifier.DataModifier;
import org.pssframework.datamodifier.DefaultUtils;


public class DateDataModifier implements DataModifier{
	
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private String dateFormat = DEFAULT_DATE_FORMAT;
	
	public DateDataModifier(){}
	
	public DateDataModifier(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public Object modify(Object value, String modifierArgument)throws Exception {
		if(value == null) return null;
		if(value instanceof Date) return value;
		DateFormat df = DefaultUtils.defaultDateFormat(modifierArgument,dateFormat);
		return df.parse(value.toString());
	}
}
