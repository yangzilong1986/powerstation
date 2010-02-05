package com.hisun.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class HiStringUtils {
    public static String format(String format, String arg0) {
        return format(format, new String[]{arg0});
    }

    public static String format(String format, String arg0, String arg1) {
        return format(format, new String[]{arg0, arg1});
    }

    public static String format(String format, String arg0, String arg1, String arg2) {
        return format(format, new String[]{arg0, arg1, arg2});
    }

    public static String format(String format, String arg0, String arg1, String arg2, String arg3) {
        return format(format, new String[]{arg0, arg1, arg2, arg3});
    }

    public static String format(String format, String arg0, String arg1, String arg2, String arg3, String arg4) {
        return format(format, new String[]{arg0, arg1, arg2, arg3, arg4});
    }

    public static String format(String fmt, String[] args) {
        StringBuffer buffer = new StringBuffer(fmt);
        return format(buffer, args).toString();
    }

    public static String format(String fmt, List args) {
        StringBuffer buffer = new StringBuffer(fmt);
        return format(buffer, args).toString();
    }

    public static StringBuffer format(StringBuffer fmt, List args) {
        int idx = 0;
        if (args.size() == 0) return fmt;
        for (int i = 0; (i < fmt.length()) && (idx < args.size()); ++i) {
            if ((fmt.charAt(i) == '%') && (fmt.charAt(i + 1) == 's')) {
                fmt.delete(i, i + 1 + 1);
                if (args.get(idx) != null) {
                    fmt.insert(i, args.get(idx));
                }
                ++idx;
            } else {
                if ((fmt.charAt(i) != '%') || (fmt.charAt(i + 1) != '%')) continue;
                fmt.deleteCharAt(i);
            }
        }
        return fmt;
    }

    public static StringBuffer format(StringBuffer fmt, String[] args) {
        int idx = 0;
        if (args.length == 0) return fmt;
        for (int i = 0; (i < fmt.length()) && (idx < args.length); ++i) {
            if ((fmt.charAt(i) == '%') && (fmt.charAt(i + 1) == 's')) {
                fmt.delete(i, i + 1 + 1);
                if (args[idx] != null) fmt.insert(i, args[idx]);
                ++idx;
            } else {
                if ((fmt.charAt(i) != '%') || (fmt.charAt(i + 1) != '%')) continue;
                fmt.deleteCharAt(i);
            }
        }
        return fmt;
    }

    public static String leftPad(long l, int len) {
        return StringUtils.leftPad(String.valueOf(l), len, '0');
    }

    public static String leftPad(int i, int len) {
        return StringUtils.leftPad(String.valueOf(i), len, '0');
    }

    public static String leftPad(short s, int len) {
        return StringUtils.leftPad(String.valueOf(s), len, '0');
    }
}