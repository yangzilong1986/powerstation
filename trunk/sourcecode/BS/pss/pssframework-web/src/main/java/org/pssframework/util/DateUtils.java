package org.pssframework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides helper methods for Date.
 * <p>
 * Create by Zhangyu.
 * </p>
 * 
 * @author
 * @version $1.0 $Date: 2010-08-28 23:25:00
 * @since 1.0
 */
public class DateUtils {
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
