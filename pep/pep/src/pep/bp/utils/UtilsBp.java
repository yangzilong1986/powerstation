/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Thinkpad
 */
public class UtilsBp {

    public static String getNow() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;

    }

    public static String Date2String(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(date);
        return dateStr;

    }

    public static byte[] String2DateArray(String dateStr, String format) throws ParseException {
        byte[] result = new byte[7];
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        Date date = sDateFormat.parse(dateStr);
        Calendar CD = Calendar.getInstance();
        CD.setTime(date);
        byte ss = (byte) CD.get(Calendar.SECOND);
        byte mm = (byte) CD.get(Calendar.MINUTE);
        byte hh = (byte) CD.get(Calendar.HOUR);
        byte WW = (byte) CD.get(Calendar.DAY_OF_WEEK);
        byte DD = (byte) CD.get(Calendar.DAY_OF_MONTH);
        byte MM = (byte) CD.get(Calendar.MONTH);
        byte YY = (byte) (CD.get(Calendar.YEAR) % 2000);

        result[0] = ss;
        result[1] = mm;
        result[2] = hh;
        result[3] = WW;
        result[4] = DD;
        result[5] = MM;
        result[6] = YY;

        return result;
    }

    //ssmmhhWWDDMMYY
    @SuppressWarnings("static-access")
    public static String DateArray2String(byte[] date) {
        String result = "";
        if (date.length >= 7) {
            int second = date[0];
            int minute = date[1];
            int hourOfDay = date[2];
            int day = date[4];
            int month = date[5];
            int year = date[6] + 2000;

            Calendar CD = Calendar.getInstance();
            CD.set(year, month, day, hourOfDay, minute, second);
            result = Date2String(CD.getTime());
        }
        return result;
    }

    /**
     * 去除左边多余的空格。
     *
     * @param value 待去左边空格的字符串
     * @return 去掉左边空格后的字符串
     */
    public static String trimLeft(String value) {
        String result = value;
        if (result == null) {
            return result;
        }
        char ch[] = result.toCharArray();
        int index = -1;
        for (int i = 0; i < ch.length; i++) {
            if (Character.isWhitespace(ch[i])) {
                index = i;
            } else {
                break;
            }
        }
        if (index != -1) {
            result = result.substring(index + 1);
        }
        return result;
    }

    /**
     * 去除右边多余的空格。
     *
     * @param value 待去右边空格的字符串
     * @return 去掉右边空格后的字符串
     */
    public static String trimRight(String value) {
        String result = value;
        if (result == null) {
            return result;
        }
        char ch[] = result.toCharArray();
        int endIndex = -1;
        for (int i = ch.length - 1; i > -1; i--) {
            if (Character.isWhitespace(ch[i])) {
                endIndex = i;
            } else {
                break;
            }
        }
        if (endIndex != -1) {
            result = result.substring(0, endIndex);
        }
        return result;
    }

    /**
     * 使用给定的字串替换源字符串中指定的字串。
     *
     * @param mainString 源字符串
     * @param oldString 被替换的字串
     * @param newString 替换字串
     * @return 替换后的字符串
     */
    public final static String replace(String mainString, String oldString, String newString) {
        if (mainString == null) {
            return null;
        }
        int i = mainString.lastIndexOf(oldString);
        if (i < 0) {
            return mainString;
        }
        StringBuffer mainSb = new StringBuffer(mainString);
        while (i >= 0) {
            mainSb.replace(i, i + oldString.length(), newString);
            i = mainString.lastIndexOf(oldString, i - 1);
        }
        return mainSb.toString();
    }

    //指定的字符串累加
    public static String strAdd(String chr, int len) {
        if (len > 0) {
            StringBuffer ret = new StringBuffer(len);
            for (int i = 0; i < len; i++) {
                ret.append(chr);
            }
            return (ret.toString());
        } else {
            return "";
        }
    }

    //给字符串补足到指定的长度，从左边补足chr指定的字符
    public static String lPad(String source, String chr, int len) {
        int lenleft = len - source.length();
        if (lenleft < 0) {
            lenleft = 0;
        }
        return (strAdd(chr, lenleft) + source);
    }

    //给字符串补足到指定的长度，从右边补足chr指定的字符
    public static String rPad(String source, String chr, int len) {
        int lenleft = len - source.length();
        if (lenleft < 0) {
            lenleft = 0;
        }
        return (source + strAdd(chr, lenleft));
    }
}
