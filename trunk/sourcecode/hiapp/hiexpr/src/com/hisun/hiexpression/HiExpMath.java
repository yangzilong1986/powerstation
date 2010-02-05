package com.hisun.hiexpression;

import com.hisun.exception.HiException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class HiExpMath {
    public static String OR(Object ctx, String[] args) throws HiException {
        if (args.length < 2) {
            throw new HiException("215110", "OR");
        }
        for (int i = 0; i < args.length; ++i) {
            if (StringUtils.equals(args[i], "1")) return "1";
        }
        return "0";
    }

    public static String NOT(Object ctx, String[] args) throws HiException {
        if (args.length != 1) throw new HiException("215110", "NOT");
        if (StringUtils.equals(args[0], "0")) {
            return "1";
        }
        return "0";
    }

    public static String AND(Object ctx, String[] args) throws HiException {
        if (args.length < 2) {
            throw new HiException("215110", "AND");
        }
        for (int i = 0; i < args.length; ++i) {
            if (StringUtils.equals(args[i], "0")) return "0";
        }
        return "1";
    }

    public static String ADD(Object ctx, String[] args) {
        long result = 0L;
        for (int i = 0; i < args.length; ++i) {
            result += NumberUtils.toLong(StringUtils.trim(args[i]));
        }
        return String.valueOf(result);
    }

    public static String SUB(Object ctx, String[] args) throws HiException {
        if (args.length < 2) {
            throw new HiException("215110", "SUB");
        }
        long result = NumberUtils.toLong(StringUtils.trim(args[0]));
        for (int i = 1; i < args.length; ++i) {
            result -= NumberUtils.toLong(StringUtils.trim(args[i]));
        }

        return String.valueOf(result);
    }

    public static String MUL(Object ctx, String[] args) throws HiException {
        if (args.length < 2) {
            throw new HiException("215110", "MUL");
        }
        long result = 1L;
        for (int i = 0; i < args.length; ++i) {
            result *= NumberUtils.toLong(StringUtils.trim(args[i]));
        }
        return String.valueOf(result);
    }

    public static String DIV(Object ctx, String[] args) throws HiException {
        if (args.length < 2) throw new HiException("215110", "DIV");
        long result = NumberUtils.toLong(StringUtils.trim(args[0]));
        for (int i = 1; i < args.length; ++i) {
            result /= NumberUtils.toLong(StringUtils.trim(args[i]));
        }
        return String.valueOf(result);
    }

    public static String MOD(Object ctx, String[] args) throws HiException {
        if (args.length < 2) throw new HiException("215110", "MOD");
        long result = NumberUtils.toLong(StringUtils.trim(args[0]));
        for (int i = 1; i < args.length; ++i) {
            result %= NumberUtils.toLong(StringUtils.trim(args[i]));
        }
        return String.valueOf(result);
    }
}