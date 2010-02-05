package com.hisun.hiexpression;

import com.hisun.exception.HiException;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class HiExpBasicHelper {
    static String[] addArgument(String[] array, String element) {
        String[] newArray = (String[]) (String[]) copyArrayGrow(array, String.class);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    static Object copyArrayGrow(Object array, Class newArrayComponentType) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);

            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }

    static int getLength(Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    static String formatDouble(double val, int precision) throws HiException {
        if (precision == 0) {
            return String.valueOf(()val);
        }

        String pattern = "0." + StringUtils.repeat("0", precision);
        try {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            df.applyPattern(pattern);
            return df.format(val);
        } catch (Exception e) {
            throw new HiException("", "", e);
        }
    }

    public static final String tosbc(String str) {
        StringBuffer outStr = new StringBuffer();
        String tStr = "";
        byte[] b = null;

        for (int i = 0; i < str.length(); ++i) {
            try {
                tStr = str.substring(i, i + 1);
                b = tStr.getBytes("unicode");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] != -1) {
                b[2] = (byte) (b[2] - 32);
                b[3] = -1;
                try {
                    outStr.append(new String(b, "unicode"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                outStr.append(tStr);
            }
        }
        return outStr.toString();
    }

    public static final String todbc(String str) {
        StringBuffer outStr = new StringBuffer();
        String tStr = "";
        byte[] b = null;

        for (int i = 0; i < str.length(); ++i) {
            try {
                tStr = str.substring(i, i + 1);
                b = tStr.getBytes("unicode");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr.append(new String(b, "unicode"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                outStr.append(tStr);
            }
        }
        return outStr.toString();
    }
}