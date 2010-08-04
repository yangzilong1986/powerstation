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
    public static String getNow(){
        SimpleDateFormat sDateFormat   =   new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;

    }

    public static String Date2String(Date date){
        SimpleDateFormat sDateFormat   =   new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = sDateFormat.format(date);
        return dateStr;

    }

    public static byte[] String2DateArray(String dateStr,String format) throws ParseException{
        byte [] result = new byte [7];
        SimpleDateFormat sDateFormat   =   new SimpleDateFormat(format);
        Date date = sDateFormat.parse(dateStr);
        Calendar CD = Calendar.getInstance();
        CD.setTime(date);
        byte ss = (byte)CD.get(Calendar.SECOND);
        byte mm = (byte)CD.get(Calendar.MINUTE);
        byte hh = (byte)CD.get(Calendar.HOUR);
        byte WW = (byte)CD.get(Calendar.DAY_OF_WEEK);
        byte DD = (byte)CD.get(Calendar.DAY_OF_MONTH);
        byte MM = (byte)CD.get(Calendar.MONTH);
        byte YY = (byte)(CD.get(Calendar.YEAR) % 2000);

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
    public static String DateArray2String(byte[] date){
        String result = "";
        if(date.length >= 7)
        {
            int second = date[0];
            int minute = date[1];
            int hourOfDay = date[2];
            int day = date[4];
            int month = date[5];
            int year =date[6]+2000;

            Calendar CD = Calendar.getInstance();
            CD.set(year, month, day, hourOfDay, minute, second);
            result = Date2String(CD.getTime());
        }
        return result;
    }
}
