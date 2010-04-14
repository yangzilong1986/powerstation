package org.pssframework.base;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * @author PSS
 */
public class BaseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7758275328091215015L;

	/**
	 * yyyy-MM-dd
	 */
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * HH:mm:ss
	 */
	protected static final String TIME_FORMAT = "HH:mm:ss";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy-MM-dd HH:mm:ss.S
	 */
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String date2String(java.util.Date date, String dateFormat) {
		return DateConvertUtils.format(date, dateFormat);
	}

	/**
	 * 
	 * @param <T>
	 * @param dateString
	 * @param dateFormat
	 * @param targetResultType
	 * @return
	 */
	public static <T extends java.util.Date> T string2Date(String dateString, String dateFormat,
			Class<T> targetResultType) {
		return DateConvertUtils.parse(dateString, dateFormat, targetResultType);
	}
}
