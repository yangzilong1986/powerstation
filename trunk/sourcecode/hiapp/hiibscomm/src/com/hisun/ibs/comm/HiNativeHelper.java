package com.hisun.ibs.comm;

import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.StringUtils;

import java.io.File;

public final class HiNativeHelper {
    private static String[] libraries = null;

    private static void loadLibrary() {
        libraries = initializePath("library.path");
        if (libraries == null) return;
        for (int i = 0; i < libraries.length; ++i)
            if (!(StringUtils.isEmpty(libraries[i]))) loadLibrary(libraries[i]);
    }

    public static void setLibLoadStatus(String prop_name, boolean status) {
        if (status) System.setProperty(prop_name, Boolean.TRUE.toString());
        else System.setProperty(prop_name, Boolean.FALSE.toString());
    }

    public static boolean getLibLoadStatus(String prop_name) {
        String sValue = System.getProperty(prop_name);

        return ((sValue == null) || (!(sValue.equalsIgnoreCase(Boolean.TRUE.toString()))));
    }

    public static void loadLibrary(String libName) {
        if (!(getLibLoadStatus(libName))) {
            System.loadLibrary(libName);
            setLibLoadStatus(libName, true);
        }
    }

    private static String[] initializePath(String propname) {
        String ldpath = HiICSProperty.getProperty(propname, "");
        if (StringUtils.isEmpty(ldpath)) return null;
        String ps = File.pathSeparator;
        int ldlen = ldpath.length();

        int i = ldpath.indexOf(ps);
        int n = 0;
        while (i >= 0) {
            ++n;
            i = ldpath.indexOf(ps, i + 1);
        }

        String[] paths = new String[n + 1];

        n = i = 0;
        int j = ldpath.indexOf(ps);
        while (j >= 0) {
            if (j - i > 0) paths[(n++)] = ldpath.substring(i, j);
            else if (j - i == 0) {
                paths[(n++)] = ".";
            }
            i = j + 1;
            j = ldpath.indexOf(ps, i);
        }
        paths[n] = ldpath.substring(i, ldlen);
        return paths;
    }
}