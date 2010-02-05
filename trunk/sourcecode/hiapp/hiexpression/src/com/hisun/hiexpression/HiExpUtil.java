package com.hisun.hiexpression;

import com.hisun.exception.HiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HiExpUtil {
    private static String[] getArguments(Object[] values) {
        if ((values == null) || (values.length == 0)) {
            return null;
        }
        String[] args = new String[values.length];
        for (int i = 0; i < values.length; ++i) {
            args[i] = ((String) values[i]);
        }

        return args;
    }

    public static Object invokeStaticMethod(Method thisMethod, Object data, Object[] args) throws Exception {
        try {
            String[] rargs = getArguments(args);
            if (rargs != null) {
                aa = new Object[]{data, rargs};
                returnValue = thisMethod.invoke(null, aa);
                return returnValue;
            }
            Object[] aa = {data};
            Object returnValue = thisMethod.invoke(null, aa);
            return returnValue;
        } catch (InvocationTargetException e) {
            throw HiException.makeException(e);
        }
    }

    public static Method getStaticMethod(String className, String staticMethodName, int nargs) throws Exception {
        return HiExpMethodSource.getMethod(staticMethodName, nargs);
    }
}