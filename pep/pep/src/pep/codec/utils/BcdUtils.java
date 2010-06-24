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

    public static String binArrayToString(byte bin[]) {
        StringBuilder sb = new StringBuilder();
        for (Byte b : bin) {
            int low = b & 0x0F;
            int high = (b >> 4) & 0x0F;
            sb.append(BcdUtils.byteToChar(high));
            sb.append(BcdUtils.byteToChar(low));
        }
        return sb.toString();
    }

    public static byte[] stringToByteArray(String str) {
        String standStr = standardizationHexString(str + " ");
        byte bin[] = new byte[standStr.length() / 2];

        for (int i = 0; i < bin.length; i++) {
            bin[i] = (byte) ((BcdUtils.charToByte(standStr.charAt(i * 2)) << 4) + BcdUtils.charToByte(standStr.charAt(i * 2 + 1)));
        }
        return bin;
    }

    public static String byteToString(byte b) {
        StringBuilder buff = new StringBuilder(2);
        buff.append(BcdUtils.byteToChar((b >> 4) & 0x0F)).append(BcdUtils.byteToChar(b & 0x0F));

        return buff.toString();
    }

    public static byte stringToByte(String str) {
        return (byte) (BcdUtils.charToByte(str.charAt(0)) * 0x10 + BcdUtils.charToByte(str.charAt(1)));
    }

    public static int bcdToInt(int bcd) {
        return ((bcd & 0xf0) >> 4) * 10 + (bcd & 0x0f);
    }

    public static long bcdToInt(byte[] bcds, int beginIndex, int length) {
        int result = 0;
        int base = 1;
        int b;
        for (int i = 0; i < length; i++) {
            b = BcdUtils.byteToUnsigned(bcds[beginIndex + i]);
            result += (BcdUtils.bcdToInt(b)) * base;
            base *= 100;
        }
        return result;
    }

    public static byte[] intTobcd(long value, int length) {
        byte result[] = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) BcdUtils.intToBcd((int) (value % 100));
            value = value / 100;
        }
        return result;
    }

    public static byte intToBcd(int decimal) {
        return (byte) ((decimal / 10) * 0x10 + (decimal % 10));
    }

    public static Date bcdToDate(byte[] bcdArray, String format, int beginPosition) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MINUTE, 0);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);

        String uformat = format.toUpperCase();
        int yyyyidx = uformat.indexOf("YYYY");
        if (yyyyidx != -1) {
            calendar.set(GregorianCalendar.YEAR, (int)BcdUtils.bcdToInt(bcdArray, beginPosition + yyyyidx / 2, 2));
        } else {
            int yyindex = uformat.indexOf("YY");
            if (yyindex != -1) {
                calendar.set(GregorianCalendar.YEAR,
                        (calendar.get(GregorianCalendar.YEAR) / 100) * 100
                        + BcdUtils.bcdToInt(bcdArray[beginPosition + yyindex / 2]));
            }
        }

        int mmindex = uformat.indexOf("MM");
        if (mmindex != -1) {
            calendar.set(GregorianCalendar.MONTH, BcdUtils.bcdToInt(bcdArray[beginPosition + mmindex / 2]) - 1);
        }

        int ddindex = uformat.indexOf("DD");
        if (ddindex != -1) {
            calendar.set(GregorianCalendar.DAY_OF_MONTH, BcdUtils.bcdToInt(bcdArray[beginPosition + ddindex / 2]));
        }

        int hhindex = uformat.indexOf("HH");
        if (hhindex != -1) {
            calendar.set(GregorianCalendar.HOUR_OF_DAY, BcdUtils.bcdToInt(bcdArray[beginPosition + hhindex / 2]));
        }

        int miindex = uformat.indexOf("MI");
        if (miindex != -1) {
            calendar.set(GregorianCalendar.MINUTE, BcdUtils.bcdToInt(bcdArray[beginPosition + miindex / 2]));
        }

        int ssindex = uformat.indexOf("SS");
        if (ssindex != -1) {
            calendar.set(GregorianCalendar.SECOND, BcdUtils.bcdToInt(bcdArray[beginPosition + ssindex / 2]));
        }

        return calendar.getTime();
    }

    public static byte[] dateToBcd(Date date, String format) {
        byte[] result = new byte[format.length() / 2];
        String uformat = format.toUpperCase();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int yyyyidx = uformat.indexOf("YYYY");
        if (yyyyidx != -1) {
            byte[] years = BcdUtils.intTobcd(calendar.get(GregorianCalendar.YEAR), 2);
            result[yyyyidx / 2] = years[0];
            result[yyyyidx / 2 + 1] = years[1];
        } else {
            int yyindex = uformat.indexOf("YY");
            if (yyindex != -1) {
                result[yyindex / 2] = BcdUtils.intToBcd((byte) (calendar.get(GregorianCalendar.YEAR) % 100));
            }
        }

        int mmindex = uformat.indexOf("MM");
        if (mmindex != -1) {
            result[mmindex / 2] = BcdUtils.intToBcd((byte) (calendar.get(GregorianCalendar.MONTH) + 1));
        }

        int ddindex = uformat.indexOf("DD");
        if (ddindex != -1) {
            result[ddindex / 2] = BcdUtils.intToBcd((byte) (calendar.get(GregorianCalendar.DAY_OF_MONTH)));
        }

        int hhindex = uformat.indexOf("HH");
        if (hhindex != -1) {
            result[hhindex / 2] = BcdUtils.intToBcd((byte) (calendar.get(GregorianCalendar.HOUR_OF_DAY)));
        }

        int miindex = uformat.indexOf("MI");
        if (miindex != -1) {
            result[miindex / 2] = BcdUtils.intToBcd((byte) (calendar.get(GregorianCalendar.MINUTE)));
        }

        int ssindex = uformat.indexOf("SS");
        if (ssindex != -1) {
            result[ssindex / 2] = BcdUtils.intToBcd((byte) (calendar.get(GregorianCalendar.SECOND)));
        }

        return result;
    }

    public static int bitSetOfByte(byte b) {
        if (b == 1) {
            return 1;
        } else if (b == 2) {
            return 2;
        } else if (b == 4) {
            return 3;
        } else if (b == 8) {
            return 4;
        } else if (b == 0x10) {
            return 5;
        } else if (b == 0x20) {
            return 6;
        } else if (b == 0x40) {
            return 7;
        } else if (b == 0x80) {
            return 8;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static byte[] bitSetStringToBytes(String bitString) {
        int len = bitString.length() / 8;
        byte[] bitArray = new byte[len];

        for (int i = 0; i < len; i++) {
            int ch = 0;
            for (int j = 0; j < 8; j++) {
                if (bitString.charAt(i * 8 + j) == '1') {
                    ch |= 1 << j;
                }
            }
            bitArray[i] = (byte) ch;
        }

        return bitArray;
    }

    public static byte[] IPStringToBytes(String ipString){
        String[] strs = ipString.split("[.]");
        byte[] b = new byte[strs.length];
        int index = 0;

        for (String str : strs) {
            b[index++] = (byte) (Integer.parseInt(str));
        }
        return b;
    }

    public static byte[] IpPortStringToBytes(String ipportString) {
        String[] strs = ipportString.substring(0, ipportString.indexOf(":")).split("[.]");
        byte[] b = new byte[strs.length + 2];
        int index = 0;

        for (String str : strs) {
            b[index++] = (byte) (Integer.parseInt(str));
        }
        String[] port = ipportString.split("[:]");
        b[index++] = (byte) ((Integer.parseInt(port[1]) & 0x00FF));
        b[index++] = (byte) ((Integer.parseInt(port[1]) & 0xFF00) >> 8);
        return b;
    }

    /**
     * 
     * @param telString
     * @return
     */
    public static byte[] TelStringToBytes(String telString) {
        String StrValue = telString.replace(",", "A");
        StrValue = StrValue.replace("#", "B");
        if (StrValue.length() > 16) {
            StrValue = StrValue.substring(0, 15);
        } else if (StrValue.length() < 16) {
            StrValue += dupeString("F", 16 - StrValue.length());
        }
        return stringToByteArray(StrValue);
    }

    public static String bytesToTelString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int valueHigh,valueLower = 0;
        for (int i = 0; i <= bytes.length - 1; i++) {
            valueHigh = (bytes[i] & 0xF0) >> 4;
            valueLower = (bytes[i] & 0x0F);
            if (valueHigh < 0xA) {
                sb.append(valueHigh);
            } else if (valueHigh == 0xA) {
                sb.append(",");
            } else if (valueHigh == 0xB) {
                sb.append("#");
            }
            if (valueLower < 0xA) {
                sb.append(valueLower);
            } else if (valueLower == 0xA) {
                sb.append(",");
            } else if (valueLower == 0xB) {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    public static String bytesToIpPortString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int Ip = 0;
            if (bytes[i] < 0) {
                Ip = bytes[i] + 256;
            } else {
                Ip = bytes[i];
            }
            sb.append(String.valueOf(Ip));
            if (i < 3) {
                sb.append(".");
            } else {
                sb.append(":");
            }
        }
        int portHigh = (bytes[5] >= 0 ? bytes[5] : bytes[5] + 256);
        int portLow = (bytes[4] >= 0 ? bytes[4] : bytes[4] + 256);
        int port = portHigh * 256 + portLow;
        sb.append(String.valueOf(port));
        return sb.toString();
    }

    public static String bytesToIpString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int Ip = 0;
            if (bytes[i] < 0) {
                Ip = bytes[i] + 256;
            } else {
                Ip = bytes[i];
            }
            sb.append(String.valueOf(Ip));
            if (i < 3) {
                sb.append(".");
            } else {
                sb.append("");
            }
        }
        return sb.toString();
    }

    public static String bytesToBitSetString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            for (int j = 0; j < 8; j++) {
                if ((b & (1 << j)) == (1 << j)) {
                    sb.append('1');
                } else {
                    sb.append('0');
                }
            }
        }
        return sb.toString();
    }

    private static char byteToChar(int b) {
        char ch = (b < 0xA) ? (char) ('0' + b) : (char) ('A' + b - 10);
        return ch;
    }

    private static byte charToByte(char ch) {
        return ((ch >= '0') && (ch <= '9')) ? (byte) (ch - '0') : (byte) (Character.toUpperCase(ch) - 'A' + 10);
    }

    private static String standardizationHexString(String str) {
        StringBuffer buff = new StringBuffer();

        int firstTokenPosition = findFirstTokenPosition(str, 0);
        while (firstTokenPosition != -1) {
            int endTokenPosition = findEndTokenPosition(str, firstTokenPosition);
            if (endTokenPosition == firstTokenPosition) {
                buff.append('0');
                buff.append(Character.toUpperCase(str.charAt(endTokenPosition)));
            } else {
                buff.append(str.charAt(firstTokenPosition));
                buff.append(str.charAt(endTokenPosition));
            }
            firstTokenPosition = findFirstTokenPosition(str, endTokenPosition + 1);
        }

        return buff.toString();
    }

    private static int findFirstTokenPosition(String str, int initPos) {
        for (int i = initPos; i < str.length(); i++) {
            if (BcdUtils.isHexChar(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    //返回Token的结束字符位置，initPos是有效的第一个字符位置
    private static int findEndTokenPosition(String str, int initPos) {
        for (int i = initPos; (i < initPos + 2) && (i < str.length()); i++) {
            if (!BcdUtils.isHexChar(str.charAt(i))) {
                return i - 1;
            } else if (i == initPos + 1) {
                return i;
            }
        }
        return initPos;
    }

    private static boolean isHexChar(char ch) {
        return ((ch >= '0') && (ch <= '9')) || ((Character.toUpperCase(ch) >= 'A') && (Character.toUpperCase(ch) <= 'F'));
    }

    public static byte[] reverseBytes(byte[] bytes) {
        int len = bytes.length;
        byte[] reverseBytes = new byte[len];

        for (int i = 0; i < len; i++) {
            reverseBytes[i] = bytes[len - i - 1];
        }
        return reverseBytes;
    }

    public static String dupeString(String str, int repeats) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeats; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static int byteToUnsigned(byte b){
        return (0x100+b)%0x100;
    }

}
