package com.hisun.util;


import com.hisun.exception.HiException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;


public class HiConvHelper {
    static final char[] ascii = "0123456789ABCDEF".toCharArray();


    public static int binary2Int(String bin) throws HiException {

        try {

            return Integer.valueOf(bin, 2).intValue();

        } catch (NumberFormatException e) {

            throw new HiException("", "错误提示信息: 待转换的bin-[" + bin + "], 包含非法二进制形式.", e);

        }

    }


    public static String int2Binary(int val) {

        return Integer.toBinaryString(val);

    }


    public static String hex2Binary(String val) throws HiException {

        StringBuffer ret = new StringBuffer();


        for (int i = 0; i < val.length(); ++i) {

            ret.append(hex2binary(val.charAt(i)));

        }

        return ret.toString();

    }


    public static String hex2binary(char hex) throws HiException {

        switch (hex) {
            case '0':

                return "0000";

            case '1':

                return "0001";

            case '2':

                return "0010";

            case '3':

                return "0011";

            case '4':

                return "0100";

            case '5':

                return "0101";

            case '6':

                return "0110";

            case '7':

                return "0111";

            case '8':

                return "1000";

            case '9':

                return "1001";

            case 'A':

            case 'a':

                return "1010";

            case 'B':

            case 'b':

                return "1011";

            case 'C':

            case 'c':

                return "1100";

            case 'D':

            case 'd':

                return "1101";

            case 'E':

            case 'e':

                return "1110";

            case 'F':

            case 'f':

                return "1111";

            case ':':

            case ';':

            case '<':

            case '=':

            case '>':

            case '?':

            case '@':

            case 'G':

            case 'H':

            case 'I':

            case 'J':

            case 'K':

            case 'L':

            case 'M':

            case 'N':

            case 'O':

            case 'P':

            case 'Q':

            case 'R':

            case 'S':

            case 'T':

            case 'U':

            case 'V':

            case 'W':

            case 'X':

            case 'Y':

            case 'Z':

            case '[':

            case '\\':

            case ']':

            case '^':

            case '_':

            case '`':
        }
        throw new HiException("", "十六进制转为二进制时, 有非法十六制字符:" + hex);

    }


    public static String binary2hex(String binary) {

        String hexString = "";

        int binLen = binary.length();

        if (binLen % 4 != 0) {

            binary = StringUtils.repeat("0", 4 - (binLen % 4)) + binary;

            binLen = binary.length();

        }

        for (int i = 0; i < binLen; i += 4) {

            hexString = hexString + Integer.toHexString(Integer.valueOf(binary.substring(i, i + 4), 2).intValue());

        }

        return hexString;

    }


    public static String byte2String(byte val) throws HiException {

        byte[] arrVal = {val};

        return byte2String(arrVal, "ISO-8859-1");

    }


    public static String byte2String(byte[] val, String charset) throws HiException {

        try {

            return new String(val, charset);

        } catch (UnsupportedEncodingException e) {

            throw new HiException("", "不支持指定的编码转换", e);

        }

    }


    public static String binToAscStr(String binStr) throws HiException {

        return binToAscStr(binStr.getBytes());

    }


    public static String binToAscStr(byte[] binBuf) {

        long ascVal = 0L;

        for (int i = 0; i < binBuf.length; ++i) {

            ascVal = (ascVal << 8) + (binBuf[i] & 0xFF);

        }


        return String.valueOf(ascVal);

    }


    public static String asc2bin(String strAsc) throws HiException {

        try {

            Integer deliInt = Integer.valueOf(strAsc);

            if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128)) {

                throw new HiException("EN0010", "转换为bin有误, asc=[" + strAsc + "]");

            }


            byte[] asc = {deliInt.byteValue()};

            return new String(asc, "ISO-8859-1");

        } catch (NumberFormatException e) {

            throw new HiException("", "asc2bin执行出错, 一个ASCII值 + [" + strAsc + "],转为对应的字符时失败.", e);

        } catch (UnsupportedEncodingException e) {

            throw new HiException("", "asc2bin执行出错, 不支持相应编码集.", e);

        }

    }


    public static String asc2bin(int intAsc) throws HiException {

        byte[] aryAsc = new byte[4];


        int2byte(aryAsc, 0, intAsc);


        return byte2String(aryAsc, "ISO-8859-1");

    }


    public static String asc2bin(int intAsc, int binLen) throws HiException {

        byte[] aryAsc = new byte[4];


        int2byte(aryAsc, 0, intAsc);

        String retStr = "";


        boolean start = true;

        for (int i = 0; i < 4; ++i) {

            if ((aryAsc[i] == 0) && (start)) {

                continue;

            }


            start = false;

            retStr = retStr + (char) aryAsc[i];

        }


        if (retStr.length() < binLen) {

            char fill_asc = '\0';

            String fill_str = String.valueOf(fill_asc);


            retStr = StringUtils.repeat(fill_str, binLen - retStr.length()) + retStr;

        } else if (retStr.length() > binLen) {

            throw new HiException("CO0010", "ascii2bin, 超过指定的宽度 " + binLen);

        }


        return retStr;

    }


    public static String bcd2AscStr(byte[] bytes) {

        return ascii2Str(bcd2Ascii(bytes));

    }


    public static byte[] ascStr2Bcd(String s) {

        return ascii2Bcd(str2Ascii(s));

    }


    public static byte[] bcd2Ascii(byte[] bytes) {

        byte[] temp = new byte[bytes.length * 2];


        for (int i = 0; i < bytes.length; ++i) {

            temp[(i * 2)] = (byte) (bytes[i] >> 4 & 0xF);

            temp[(i * 2 + 1)] = (byte) (bytes[i] & 0xF);

        }


        return temp;

    }


    public static byte[] str2Ascii(String s) {

        byte[] str = s.toUpperCase().getBytes();

        byte[] ascii = new byte[str.length];

        for (int i = 0; i < ascii.length; ++i) {

            ascii[i] = (byte) asciiValue(str[i]);

        }

        return ascii;

    }


    public static String ascii2Str(byte[] ascii) {

        StringBuffer res = new StringBuffer();

        for (int i = 0; i < ascii.length; ++i) {

            res.append(strValue(ascii[i]));

        }

        return res.toString();

    }


    private static char strValue(byte asc) {

        if ((asc < 0) || (asc > 15)) throw new InvalidParameterException();

        return ascii[asc];

    }


    public static byte[] ascii2Bcd(byte[] asc) {

        int len = asc.length / 2;

        byte[] bcd = new byte[len];

        for (int i = 0; i < len; ++i) {

            bcd[i] = (byte) (asc[(2 * i)] << 4 | asc[(2 * i + 1)]);

        }

        return bcd;

    }


    private static int asciiValue(byte b) {

        if ((b >= 48) && (b <= 57)) {

            return (b - 48);

        }

        if ((b >= 97) && (b <= 102)) {

            return (b - 97 + 10);

        }

        if ((b >= 65) && (b <= 70)) {

            return (b - 65 + 10);

        }


        throw new InvalidParameterException();

    }


    public static void printByte(byte[] b) {

        for (int i = 0; i < b.length; ++i) {

            System.out.print(b[i] + " ");

        }

        System.out.println();

    }


    public static short byte2short(byte[] bp, int index) {

        return (short) (((bp[index] & 0xFF) << 8) + (bp[(index + 1)] & 0xFF));

    }


    public static int byte2int(byte[] bp, int index) {

        return (((bp[index] & 0xFF) << 24) + ((bp[(index + 1)] & 0xFF) << 16) + ((bp[(index + 2)] & 0xFF) << 8) + (bp[(index + 3)] & 0xFF));

    }


    public static void short2byte(byte[] bp, int index, short value) {

        bp[index] = (byte) (value >> 8 & 0xFF);

        bp[(index + 1)] = (byte) (value & 0xFF);

    }


    public static void int2byte(byte[] bp, int index, int value) {

        bp[index] = (byte) (value >> 24 & 0xFF);

        bp[(index + 1)] = (byte) (value >> 16 & 0xFF);

        bp[(index + 2)] = (byte) (value >> 8 & 0xFF);

        bp[(index + 3)] = (byte) (value & 0xFF);

    }


    public static long int2uint(int x) {

        return (x << 32 >>> 32);

    }


    public static long byte2uint(byte[] x, int offs) {

        long z = 0L;

        for (int i = 0; i < 4; ++i) {

            z = (z << 8) + (x[(offs + i)] & 0xFF);

        }

        return z;

    }


    public static byte[] uint2byte(long[] x) {

        byte[] res = new byte[8];

        int2byte(res, 0, (int) x[0]);

        int2byte(res, 4, (int) x[1]);

        return res;

    }


    public static byte[] long2byte(long x) {

        byte[] res = new byte[8];

        int2byte(res, 0, (int) (x >> 32 & 0xFFFFFFFF));

        int2byte(res, 4, (int) (x & 0xFFFFFFFF));

        return res;

    }


    public static long byte2long(byte[] msg, int offs) {

        long high = byte2uint(msg, offs);

        offs += 4;

        long low = byte2uint(msg, offs);

        offs += 4;

        long ans = (high << 32) + low;

        return ans;

    }


    public static String boolean2String(boolean[] ba) {

        StringBuffer strb = new StringBuffer();

        int cnt = 0;


        if ((ba == null) || (ba.length == 0)) {

            return "(none)";

        }


        for (int i = 0; i < ba.length; ++i) {

            if (ba[i] == 0) continue;

            if (cnt++ != 0) {

                strb.append("+");

            }

            strb.append(i);

        }


        return strb.toString();

    }


    public static String convFlags(String equiv, byte flags) {

        char[] chs = new char[8];

        StringBuffer strb = new StringBuffer(" ");


        if (equiv.length() > 8) {

            return ">8?";

        }

        equiv.getChars(0, equiv.length(), chs, 0);


        int bit = 128;
        for (int i = 0; bit != 0; ++i) {

            if ((flags & bit) != 0) {

                strb.setCharAt(0, '*');

                strb.append(chs[i]);

            }

            bit >>= 1;

        }


        return strb.toString();

    }


    public static String timer2string(long time) {

        String timeString = null;


        long msec = time % 1000L;

        String ms = String.valueOf(msec);

        ms = fill(ms, 3, "0");


        long rem = time / 1000L;

        int xsec = (int) (rem % 60L);

        rem = (int) ((rem - xsec) / 60L);

        int xmin = (int) (rem % 60L);

        rem = (int) ((rem - xmin) / 60L);

        int xhour = (int) (rem % 24L);

        int xday = (int) ((rem - xhour) / 24L);


        String sday = String.valueOf(xday);

        String shour = String.valueOf(xhour);

        shour = fill(shour, 2, "0");

        String smin = String.valueOf(xmin);

        smin = fill(smin, 2, "0");

        String ssec = String.valueOf(xsec);

        ssec = fill(ssec, 2, "0");


        timeString = sday + " days, " + shour + ":" + smin + ":" + ssec + "." + ms;

        return timeString;

    }


    private static String fill(String str, int sz, String cfill) {

        while (str.length() < sz) {

            str = cfill + str;

        }

        return str;

    }


    public static byte[] ascByte2Bcd(byte[] bytes) throws HiException {

        Hex hex = new Hex();

        try {

            bytes = hex.decode(bytes);

            hex = null;

        } catch (DecoderException e) {

            throw new HiException(e);

        }


        return bytes;

    }


    public static byte[] bcd2AscByte(byte[] bytes) {

        Hex hex = new Hex();

        bytes = hex.encode(bytes);

        hex = null;

        return bytes;

    }

}