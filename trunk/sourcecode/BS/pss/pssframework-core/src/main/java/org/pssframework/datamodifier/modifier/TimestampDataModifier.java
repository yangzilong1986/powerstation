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

import java.sql.Timestamp;
import java.text.DateFormat;

import org.pssframework.datamodifier.DataModifier;
import org.pssframework.datamodifier.DefaultUtils;

public class TimestampDataModifier implements DataModifier {
	private static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	private String dateFormat = DEFAULT_TIMESTAMP_FORMAT;

	public TimestampDataModifier() {
	}

	public TimestampDataModifier(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Object modify(Object value, String modifierArgument) throws Exception {
		if (value == null)
			return null;
		if (value instanceof Timestamp)
			return value;
		DateFormat df = DefaultUtils.defaultDateFormat(modifierArgument, dateFormat);
		return new Timestamp(df.parse(value.toString()).getTime());
	}

}
