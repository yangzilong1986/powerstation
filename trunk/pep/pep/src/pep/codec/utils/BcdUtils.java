/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.utils;

/**
 *
 * @author luxiaochung
 */

import java.util.GregorianCalendar;
import java.util.Date;

public class BcdUtils {

    public static String binArrayToString(byte bin[]){
        StringBuffer sb = new StringBuffer();
        for (Byte b : bin) {
          int low = b & 0x0F;
          int high = (b >> 4) & 0x0F;
          sb.append(BcdUtils.byteToChar(high));
          sb.append(BcdUtils.byteToChar(low));
        }
        return sb.toString();
    }

    public static byte[] stringToByteArray(String str){
        String standStr = standardizationHexString(str+" ");
        byte bin[] = new byte[standStr.length()/2];

        for (int i=0; i<bin.length; i++){
            bin[i] = (byte) ((BcdUtils.charToByte(standStr.charAt(i*2))<<4)+BcdUtils.charToByte(standStr.charAt(i*2+1)));
        }
        return bin;
    }

    public static String byteToString(byte b){
        StringBuffer buff = new StringBuffer(2);
        buff.append(BcdUtils.byteToChar((b >> 4) & 0x0F)).append(BcdUtils.byteToChar(b & 0x0F));

        return buff.toString();
    }

    public static byte stringToByte(String str){
        return (byte) (BcdUtils.charToByte(str.charAt(0))*0x10+BcdUtils.charToByte(str.charAt(1)));
    }

    public static byte bcdToInt(byte bcd){
        return (byte)((bcd>>4)*10+(bcd &0x0f));
    }

    public static int bcdToInt(byte[] bcds, int beginIndex, int length){
        int result = 0;
        int base = 1;
        for (int i=0; i<length;i++){
            result += ((bcds[beginIndex+i]>>4)*10+(bcds[beginIndex+i] &0x0f))*base;
            base *= 100;
        }
        return result;
    }

    public static byte[] intTobcd(int value, int length){
        byte result[] = new byte[length];
        for (int i=0; i<length;i++){
           result[i] = BcdUtils.intToBcd((byte) (value % 100));
           value = value / 100;
        }
        return result;
    }

    public static byte intToBcd(byte decimal){
        return (byte)((decimal / 10)*0x10+(decimal % 10));
    }

    public static Date bcdToDate(byte[] bcdArray, String format){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MINUTE, 0);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);

        String uformat = format.toUpperCase();
        int yyyyidx = uformat.indexOf("YYYY");
        if (yyyyidx!=-1) calendar.set(GregorianCalendar.YEAR, BcdUtils.bcdToInt(bcdArray, yyyyidx/2, 2));
        else {
            int yyindex = uformat.indexOf("YY");
            if (yyindex!=-1) calendar.set(GregorianCalendar.YEAR, 
                    (calendar.get(GregorianCalendar.YEAR)/100)*100+
                    BcdUtils.bcdToInt(bcdArray[yyindex/2]));
        }
        
        int mmindex = uformat.indexOf("MM");
        if (mmindex!=-1) calendar.set(GregorianCalendar.MONTH, BcdUtils.bcdToInt(bcdArray[mmindex/2])-1);

        int ddindex = uformat.indexOf("DD");
        if (ddindex!=-1) calendar.set(GregorianCalendar.DAY_OF_MONTH, BcdUtils.bcdToInt(bcdArray[ddindex/2]));
        
        int hhindex = uformat.indexOf("HH");
        if (hhindex!=-1) calendar.set(GregorianCalendar.HOUR_OF_DAY, BcdUtils.bcdToInt(bcdArray[hhindex/2]));
        
        int miindex = uformat.indexOf("MI");
        if (miindex!=-1) calendar.set(GregorianCalendar.MINUTE, BcdUtils.bcdToInt(bcdArray[miindex/2]));
        
        int ssindex = uformat.indexOf("SS");
        if (ssindex!=-1) calendar.set(GregorianCalendar.SECOND, BcdUtils.bcdToInt(bcdArray[ssindex/2]));
        
        return calendar.getTime();
    }

    public static byte[] dateToBcd(Date date, String format){
        byte[] result = new byte[format.length()/2];
        String uformat = format.toUpperCase();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        
        int yyyyidx = uformat.indexOf("YYYY");
        if (yyyyidx!=-1){
            byte[] years = BcdUtils.intTobcd(calendar.get(GregorianCalendar.YEAR),2);
            result[yyyyidx/2] = years[0];
            result[yyyyidx/2+1] = years[1];
        }
        else {
            int yyindex = uformat.indexOf("YY");
            if (yyindex!=-1) 
                result[yyindex/2] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.YEAR)%100));
        }
        
        int mmindex = uformat.indexOf("MM");
        if (mmindex!=-1) result[mmindex/2] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.MONTH)+1));

        int ddindex = uformat.indexOf("DD");
        if (ddindex!=-1) result[ddindex/2] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.DAY_OF_MONTH)));
        
        int hhindex = uformat.indexOf("HH");
        if (hhindex!=-1) result[hhindex/2] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.HOUR_OF_DAY)));
        
        int miindex = uformat.indexOf("MI");
        if (miindex!=-1) result[miindex/2] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.MINUTE)));
        
        int ssindex = uformat.indexOf("SS");
        if (ssindex!=-1) result[ssindex/2] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.SECOND)));

        return result;
    }
    private static char byteToChar(int b){
        char ch = (b<0xA)? (char) ('0' + b) : (char) ('A'+b-10);
        return ch;
    }

    private static byte charToByte(char ch){
        return ((ch>='0') && (ch<='9'))? (byte)(ch-'0') : (byte)(Character.toUpperCase(ch)-'A'+10);
    }

    private static String standardizationHexString(String str){
        StringBuffer buff = new StringBuffer();

        int firstTokenPosition = findFirstTokenPosition(str,0);
        while (firstTokenPosition!=-1){
          int endTokenPosition = findEndTokenPosition(str,firstTokenPosition);
        if (endTokenPosition==firstTokenPosition){
              buff.append('0');
              buff.append(Character.toUpperCase(str.charAt(endTokenPosition)));
          }
          else {
              buff.append(str.charAt(firstTokenPosition));
              buff.append(str.charAt(endTokenPosition));
          }
          firstTokenPosition = findFirstTokenPosition(str,endTokenPosition+1);
        }

        return buff.toString();
    }

    private static int findFirstTokenPosition(String str, int initPos){
        for (int i=initPos; i<str.length(); i++){
            if (BcdUtils.isHexChar(str.charAt(i))) return i;
        }
        return -1;
    }

    //返回Token的结束字符位置，initPos是有效的第一个字符位置
    private static int findEndTokenPosition(String str, int initPos){
        for (int i=initPos; (i<initPos+2)&&(i<str.length()); i++){
           if (!BcdUtils.isHexChar(str.charAt(i))) return i-1;
           else if (i==initPos+1) return i;
        }
        return initPos;
    }

    private static boolean isHexChar(char ch){
        return ((ch>='0') && (ch<='9')) || ((Character.toUpperCase(ch)>='A') && (Character.toUpperCase(ch)<='F'));
    }
}
