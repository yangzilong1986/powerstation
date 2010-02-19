package com.hzjbbis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat shortTimeFormat = new SimpleDateFormat("HH:mm");

    public static Calendar getBeginOfToday() {
        Calendar date = Calendar.getInstance();
        clearTimePart(date);
        return date;
    }

    public static Calendar getEndOfToday() {
        Calendar date = Calendar.getInstance();
        setLastTimeOfDay(date);
        return date;
    }

    public static Calendar clearTimePart(Calendar date) {
        date.set(11, 0);
        date.set(12, 0);
        date.set(13, 0);
        date.set(14, 0);

        return date;
    }

    public static Calendar setLastTimeOfDay(Calendar date) {
        date.set(9, 1);
        date.set(11, 23);
        date.set(12, 59);
        date.set(13, 59);
        date.set(14, 999);
        return date;
    }

    public static Calendar getFirstDayOfYear(int year) {
        Calendar date = Calendar.getInstance();
        date.set(1, year);
        date.set(2, 0);
        date.set(5, 1);
        clearTimePart(date);

        return date;
    }

    public static Calendar getLastDayOfYear(int year) {
        Calendar date = Calendar.getInstance();
        date.set(1, year);
        date.set(2, 11);
        date.set(5, 31);
        date.set(10, 11);
        setLastTimeOfDay(date);

        return date;
    }

    public static Calendar parse(String val) {
        if (val == null) {
            return null;
        }
        try {
            Date date = null;
            String s = val.trim();
            int indexOfDateDelim = s.indexOf("-");
            int indexOfTimeDelim = s.indexOf(":");
            int indexOfTimeDelim2 = s.indexOf(":", indexOfTimeDelim + 1);
            if ((indexOfDateDelim < 0) && (indexOfTimeDelim > 0)) {
                if (indexOfTimeDelim2 > 0) {
                    date = timeFormat.parse(s);
                } else {
                    date = shortTimeFormat.parse(s);
                }
            } else if ((indexOfDateDelim > 0) && (indexOfTimeDelim < 0)) {
                date = dateFormat.parse(s);
            } else if (indexOfTimeDelim2 > 0) {
                date = dateTimeFormat.parse(s);
            } else {
                date = shortDateTimeFormat.parse(s);
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException ex) {
            throw new IllegalArgumentException(val + " is invalid date format");
        }
    }

    public static long getTimeMillis(Calendar time) {
        if (time == null) {
            return 0L;
        }

        return (time.get(11) * 3600000L + time.get(12) * 60000L + time.get(13) * 1000L + time.get(14));
    }

    public static int compareTime(Calendar time1, Calendar time2) {
        long ms1 = getTimeMillis(time1);
        long ms2 = getTimeMillis(time2);
        if (ms1 == ms2) {
            return 0;
        }
        if (ms1 > ms2) {
            return 1;
        }

        return -1;
    }

    public static Calendar parse(String val, Calendar defaultValue) {
        try {
            return parse(val);
        } catch (Exception ex) {
        }
        return defaultValue;
    }
}