package org.pssframework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     *
     * @return String
     */
    public static String getYestDate() {
        Calendar calYesterday = Calendar.getInstance(); //昨日
        calYesterday.add(Calendar.DAY_OF_MONTH, -1);
        String sYestYear = "" + calYesterday.get(Calendar.YEAR);
        String sYestMonth = "" + (calYesterday.get(Calendar.MONTH) + 1);
        String sYestDay = "" + calYesterday.get(Calendar.DAY_OF_MONTH);
        String sYestDate = sYestYear + "-" + sYestMonth + "-" + sYestDay; //昨日日期
        return sYestDate;
    }
}
