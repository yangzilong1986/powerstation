/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import pep.codec.utils.BcdUtils;

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

    @SuppressWarnings("static-access")
    public static String getThisDay(){
        Calendar   cal   =   Calendar.getInstance();
        int Day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return lPad(String.valueOf(Day),"0",2);
    }

    @SuppressWarnings("static-access")
    public static String getThisHour(){
        Calendar   cal   =   Calendar.getInstance();
        int Hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return lPad(String.valueOf(Hour),"0",2);
    }

     @SuppressWarnings("static-access")
    public static String getYeasterday() {
        return getYeasterday_YYMMDD() + " 00:00:00";
    }

    @SuppressWarnings("static-access")
    public static String getYeasterday_YYMMDD() {
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        return new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
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
        byte hh = (byte) CD.get(Calendar.HOUR_OF_DAY);
        byte WW = (byte) CD.get(Calendar.DAY_OF_WEEK);
        byte DD = (byte) CD.get(Calendar.DAY_OF_MONTH);
        byte MM = (byte) (CD.get(Calendar.MONTH)+1);//月份从0开始
        byte YY = (byte) (CD.get(Calendar.YEAR) % 2000);

        result[0] = BcdUtils.intToBcd(ss);
        result[1] = BcdUtils.intToBcd(mm);
        result[2] = BcdUtils.intToBcd(hh);
        result[3] = BcdUtils.intToBcd(WW);
        result[4] = BcdUtils.intToBcd(DD);
        result[5] = BcdUtils.intToBcd(MM);
        result[6] = BcdUtils.intToBcd(YY);

        return result;
    }

    //ssmmhhWWDDMMYY
    @SuppressWarnings("static-access")
    public static String DateArray2String(byte[] date) {
        String result = "";
        if (date.length >= 7) {
            int second = BcdUtils.bcdToInt(date[0]);
            int minute = BcdUtils.bcdToInt(date[1]);
            int hourOfDay = BcdUtils.bcdToInt(date[2]);
            int day = BcdUtils.bcdToInt(date[4]);
            int month = BcdUtils.bcdToInt(date[5])-1;
            int year = BcdUtils.bcdToInt(date[6])  + 2000;

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

    public static String Reverse(String source){
        StringBuffer sb  = new StringBuffer(source);
        sb.reverse();
        return  sb.toString();
    }
}
