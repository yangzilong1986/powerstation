/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.utils;

/**
 *
 * @author luxiaochung
 */
public class BcdUtils {

    public static String binArrayToString(byte bin[]){
        StringBuffer sb = new StringBuffer();
        for (Byte b : bin) {
          int low = b & 0x0F;
          int high = (b >> 4) & 0x0F;
          sb.append(byteToChar(high));
          sb.append(byteToChar(low));
        }
        return sb.toString();
    }

    public static byte[] stringToByteArray(String str){
        String standStr = standardizationHexString(str+" ");
        byte bin[] = new byte[standStr.length()/2];

        for (int i=0; i<bin.length; i++){
            bin[i] = (byte) ((charToByte(standStr.charAt(i*2))<<4)+charToByte(standStr.charAt(i*2+1)));
        }
        return bin;
    }

    public static String byteToString(byte b){
        StringBuffer buff = new StringBuffer(2);
        buff.append(byteToChar((b >> 4) & 0x0F)).append(byteToChar(b & 0x0F));

        return buff.toString();
    }

    public static byte stringToByte(String str){
        return (byte) (charToByte(str.charAt(0))*0x10+charToByte(str.charAt(1)));
    }

    public static byte bcdToInt(byte bcd){
        return (byte)((bcd>>4)*10+(bcd &0x0f));
    }

    public static byte intToBcd(byte decimal){
        return (byte)((decimal / 10)*0x10+(decimal % 10));
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
            if (isHexChar(str.charAt(i))) return i;
        }
        return -1;
    }

    //返回Token的结束字符位置，initPos是有效的第一个字符位置
    private static int findEndTokenPosition(String str, int initPos){
        for (int i=initPos; (i<initPos+2)&&(i<str.length()); i++){
           if (!isHexChar(str.charAt(i))) return i-1;
           else if (i==initPos+1) return i;
        }
        return initPos;
    }

    private static boolean isHexChar(char ch){
        return ((ch>='0') && (ch<='9')) || ((Character.toUpperCase(ch)>='A') && (Character.toUpperCase(ch)<='F'));
    }
}
