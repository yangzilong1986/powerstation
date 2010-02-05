package com.hisun.util;

import com.hisun.exception.HiException;
import org.apache.commons.lang.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class HiDateUtils {
    public static boolean chkDateFormat(String date) {
        try {
            if ((null == date) || ("".equals(date)) || (!(date.matches("[0-9]{8}")))) {
                return false;
            }
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6)) - 1;
            int day = Integer.parseInt(date.substring(6));
            Calendar calendar = GregorianCalendar.getInstance();

            calendar.setLenient(false);
            calendar.set(1, year);
            calendar.set(2, month);
            calendar.set(5, day);

            calendar.get(1);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static String getMonthBegin(String strdate) throws HiException {
        java.util.Date date = parseDate(strdate);

        return formatDateByFormat(date, "yyyy-MM") + "-01";
    }

    public static String getMonthEnd(String strdate) throws HiException {
        java.util.Date date = parseDate(getMonthBegin(strdate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, 1);
        calendar.add(6, -1);
        return formatDate(calendar.getTime());
    }

    public static String formatDate(java.util.Date date) throws HiException {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    public static String formatDateByFormat(java.util.Date date, String format) throws HiException {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                throw new HiException(ex);
            }
        }
        return result;
    }

    public static java.util.Date parseDate(String dateStr, String format) throws HiException {
        java.util.Date date;
        try {
            date = DateUtils.parseDate(dateStr, new String[]{format});
        } catch (ParseException e) {
            throw new HiException(e);
        }
        return date;
    }

    public static java.util.Date parseDate(String dateStr) throws HiException {
        return parseDate(dateStr, "yyyyMMdd");
    }

    public static java.util.Date parseDate(java.sql.Date date) {
        return date;
    }

    public static java.sql.Date parseSqlDate(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }
        return null;
    }

    public static java.sql.Date parseSqlDate(String dateStr, String format) throws HiException {
        java.util.Date date = parseDate(dateStr, format);
        return parseSqlDate(date);
    }

    public static java.sql.Date parseSqlDate(String dateStr) throws HiException {
        return parseSqlDate(dateStr, "yyyy/MM/dd");
    }

    public static Timestamp parseTimestamp(String dateStr, String format) throws HiException {
        java.util.Date date = parseDate(dateStr, format);
        if (date != null) {
            long t = date.getTime();
            return new Timestamp(t);
        }
        return null;
    }

    public static Timestamp parseTimestamp(String dateStr) throws HiException {
        return parseTimestamp(dateStr, "yyyy/MM/dd HH:mm:ss");
    }

    public static String format(java.util.Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String format(java.util.Date date) {
        return format(date, "yyyy/MM/dd");
    }

    public static int getYear(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(1);
    }

    public static int getMonth(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (c.get(2) + 1);
    }

    public static int getDay(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(5);
    }

    public static int getHour(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(11);
    }

    public static int getMinute(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(12);
    }

    public static int getSecond(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(13);
    }

    public static long getMillis(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static String getDate(java.util.Date date) {
        return format(date, "yyyy/MM/dd");
    }

    public static String getTime(java.util.Date date) {
        return format(date, "HH:mm:ss");
    }

    public static String getDateTime(java.util.Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    public static java.util.Date addDate(java.util.Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + day * 24L * 3600L * 1000L);
        return c.getTime();
    }

    public static int diffDate(java.util.Date date, java.util.Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / 86400000L);
    }

    public static int diffDate(String dateStr1, String dateStr2) throws HiException {
        java.util.Date date1 = parseDate(dateStr1);
        java.util.Date date2 = parseDate(dateStr2);
        return (int) ((getMillis(date1) - getMillis(date2)) / 86400000L);
    }

    public static boolean isSameWeekDates(java.util.Date date1, java.util.Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(1) - cal2.get(1);
        if (0 == subYear) {
            if (cal1.get(3) != cal2.get(3)) break label114;
            return true;
        }
        if ((1 == subYear) && (11 == cal2.get(2))) {
            if (cal1.get(3) != cal2.get(3)) break label114;
            return true;
        }

        label114:
        return ((-1 != subYear) || (11 != cal1.get(2)) || (cal1.get(3) != cal2.get(3)));
    }

    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(3));
        if (week.length() == 1) week = "0" + week;
        String year = Integer.toString(c.get(1));
        return year + week;
    }

    public static String getMonday(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(7, 2);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static String getFriday(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(7, 6);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }
}