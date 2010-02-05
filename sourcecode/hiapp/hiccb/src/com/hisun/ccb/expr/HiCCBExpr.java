package com.hisun.ccb.expr;

import com.hisun.exception.HiException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class HiCCBExpr {
    private static boolean isHaveInCharSet(String str, String char_set) {
        byte[] bytes1 = str.getBytes();
        byte[] bytes2 = char_set.getBytes();
        for (int i = 0; i < bytes1.length; ++i) {
            for (int j = 0; j < bytes2.length; ++j) {
                if (bytes1[i] == bytes2[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSeriesSameChar(String str, int num) {
        byte[] bytes = str.getBytes();
        byte c = bytes[0];

        if (num < 2) {
            return false;
        }

        int i = 1;
        for (int j = 1; i < bytes.length; ++i) {
            if (bytes[i] == c) {
                ++j;
                if (j < num) continue;
                return true;
            }

            j = 1;
            c = bytes[i];
        }

        return false;
    }

    public static String BINNOT(Object ctx, String[] args) throws HiException {
        if (args.length != 1) throw new HiException("215110", "BINNOT");
        byte[] bytes = args[0].getBytes();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] == 48) result.append('1');
            else {
                result.append('0');
            }
        }
        return result.toString();
    }

    public static String BINAND(Object ctx, String[] args) throws HiException {
        if (args.length != 2) {
            throw new HiException("215110", "BINAND");
        }
        byte[] bytes1 = args[0].getBytes();
        byte[] bytes2 = args[1].getBytes();
        int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            if ((bytes1[i] == 48) && (bytes2[i] == 48)) result.append('0');
            else {
                result.append('1');
            }
        }
        return result.toString();
    }

    public static String CPMAND(Object ctx, String[] args) throws HiException {
        if (args.length != 2) {
            throw new HiException("215110", "BINAND");
        }
        byte[] bytes1 = args[0].getBytes();
        byte[] bytes2 = args[1].getBytes();
        int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        for (int i = 0; i < length; ++i) {
            if ((bytes1[i] != 48) && (bytes2[i] != 48)) {
                return new String("1");
            }
        }
        return new String("0");
    }

    public static String ISSIMPLEPASSWD(Object ctx, String[] args) throws HiException {
        if (args.length != 1) {
            throw new HiException("215110", "ISSIMPLEPASSWD");
        }

        if (args[0].length() != 6) {
            return new String("1");
        }

        if (!(isHaveInCharSet(args[0], "0123456789"))) {
            return new String("2");
        }

        if (!(isHaveInCharSet(args[0], "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))) {
            return new String("3");
        }

        if (!(isHaveInCharSet(args[0], "!@#$%^&*()"))) {
            return new String("4");
        }

        if (isSeriesSameChar(args[0], 3)) {
            return new String("5");
        }

        return new String("0");
    }

    public static String GETRANDOM(Object ctx, String[] args) throws HiException {
        if (args.length < 1) {
            throw new HiException("215110", "GETRANDOM");
        }
        int len = NumberUtils.toInt(StringUtils.trim(args[0]));

        return RandomStringUtils.randomNumeric(len);
    }

    public static String DESENCRYPT(Object ctx, String[] args) throws HiException {
        if (args.length < 1) {
            throw new HiException("215110", "DESENCRYPT");
        }

        return args[0];
    }
}