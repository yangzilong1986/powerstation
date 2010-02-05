package com.hisun.hiexpression;

import java.text.DecimalFormat;

public class COMP3Util {
    public static double composeDouble(String tmp, int limit) {
        double amt = 0.0D;
        int ch = 0;
        int high = 0;
        int low = 0;

        for (int idx = 0; idx < tmp.length() - 1; ++idx) {
            ch = tmp.charAt(idx);
            high = (ch & 0xF0) >> 4;
            low = ch & 0xF;
            amt = amt * 100.0D + high * 10 + low;
        }
        ch = tmp.charAt(idx);
        high = (ch & 0xF0) >> 4;
        low = ch & 0xF;
        amt = amt * 10.0D + high;
        amt /= Math.pow(10.0D, limit);
        if (low == 13) {
            return (-1.0D * amt);
        }
        return amt;
    }

    public static byte[] composeCOMP3(double value, int length, int limit) {
        String vstr = getFormatorStr("0.00", limit, value);
        StringBuffer buf = new StringBuffer();
        int len = (length + 1) / 2 + 1;
        int num_zero = len * 2 - 1 - vstr.length();
        for (int i = 0; i < num_zero; ++i) {
            buf.append("0");
        }
        buf.append(vstr);
        if ((value > 1.E-005D) && (value < 0.0D)) buf.append("D");
        else {
            buf.append("C");
        }
        vstr = buf.toString();
        byte[] b = new byte[len];
        int high = 0;
        int low = 0;
        for (int idx = 0; idx < len * 2; idx += 2) {
            if (idx + 2 == vstr.length()) {
                high = vstr.charAt(idx) - '0';
                low = vstr.charAt(idx + 1);
                if (low == 68) low = 13;
                else low = 12;
            } else {
                high = vstr.charAt(idx) - '0';
                low = vstr.charAt(idx + 1) - '0';
            }
            int ch = (high << 4) + low;
            int chv = 0;
            if (ch > 127) chv = -1 * (256 - ch);
            else {
                chv = ch;
            }
            b[(idx / 2)] = (byte) chv;
        }
        return b;
    }

    public static void main(String[] args) {
        byte[] b = null;
        b = composeCOMP3(-99999999.989999995D, 11, 2);
        System.out.println(b);
        b = composeCOMP3(88888888.989999995D, 11, 2);
        System.out.println(b);
        b = composeCOMP3(99999999.989999995D, 11, 2);
        System.out.println(b);
        b = composeCOMP3(1.99D, 11, 2);
        System.out.println(b);
        b = composeCOMP3(0.99D, 11, 2);
        System.out.println(b);
        b = composeCOMP3(0.01D, 11, 2);
        System.out.println(b);
    }

    private static String getFormatorStr(String pattern, int fraction, double value) {
        DecimalFormat nf = new DecimalFormat();
        nf.applyPattern(pattern);
        nf.setMaximumFractionDigits(fraction);
        nf.setMinimumFractionDigits(fraction);
        return nf.format(value);
    }
}