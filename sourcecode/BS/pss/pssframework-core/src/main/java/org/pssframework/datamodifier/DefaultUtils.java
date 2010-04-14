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
