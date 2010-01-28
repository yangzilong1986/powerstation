package com.hisun.util;


import com.hisun.message.HiContext;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;


public class HiICSProperty {

    public static boolean containsProperty(String name) {

        HiContext ctx = HiContext.getRootContext();

        return ctx.containsProperty("@SYS." + name);

    }


    public static void setProperty(String name, String value) {

        HiContext ctx = HiContext.getRootContext();

        ctx.setProperty("@SYS", name, StringUtils.trim(value));

    }


    public static String getProperty(String name) {

        HiContext ctx = HiContext.getRootContext();

        return ctx.getStrProp("@SYS", name);

    }


    public static String getProperty(String name, String defaultValue) {

        HiContext ctx = HiContext.getRootContext();

        String value = ctx.getStrProp("@SYS", name);

        if (value == null) return defaultValue;

        return value;

    }


    public static int getInt(String name) {

        return NumberUtils.toInt(getProperty(name));

    }


    public static int getInt(String name, int defaultValue) {

        return NumberUtils.toInt(getProperty(name), defaultValue);

    }


    public static boolean getBoolean(String name) {

        return BooleanUtils.toBoolean(getProperty(name));

    }


    public static boolean getBoolean(String name, boolean defaultValue) {

        String value = getProperty(name);

        if (value == null) {

            return defaultValue;

        }

        return BooleanUtils.toBoolean(getProperty(name));

    }


    public static void reload() {

        loadProperties();

        initialize();

    }


    private static void loadProperties() {

        InputStream is = null;

        Throwable error = null;

        Properties properties = new Properties();

        try {

            is = HiResource.getResourceAsStream("conf/env.properties");

            if (is != null) properties.load(is);

            is = null;

            Enumeration enumeration = properties.propertyNames();


            while (enumeration.hasMoreElements()) {

                String name = (String) enumeration.nextElement();

                String value = properties.getProperty(name);

                System.setProperty(name, value);

            }

            properties.clear();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        String icsHome = System.getProperty("HWORKDIR");

        if (icsHome == null) {

            icsHome = System.getenv("HWORKDIR");

        }

        if (icsHome == null) {

            icsHome = "./";

        }

        setProperty("HWORKDIR", icsHome);


        if (is == null) {

            try {

                is = HiResource.getResourceAsStream("conf/sys.properties");

            } catch (Throwable t) {

            }

        }


        if (is == null) {

            try {

                is = HiICSProperty.class.getResourceAsStream("sys.properties");

            } catch (Throwable t) {

            }

        }


        if (is != null) {

            try {

                properties.load(is);

                is.close();

            } catch (Throwable t) {

                error = t;

            }

        }


        if ((is != null) && (error != null)) ;

        Enumeration enumeration = properties.propertyNames();


        HiSymbolExpander symbolExpander = new HiSymbolExpander(HiContext.getRootContext(), "@SYS");


        while (enumeration.hasMoreElements()) {

            String name = (String) enumeration.nextElement();

            String value = properties.getProperty(name);

            if (value != null) {

                setProperty(name, symbolExpander.expandSymbols(value));

            }

        }

        properties.clear();

        properties = null;

    }


    static void initialize() {

        String icsHome = getProperty("HWORKDIR");

        if (System.getProperty("dat.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("dat.dir", icsHome + "dat");

            else {

                setProperty("dat.dir", icsHome + File.separator + "dat");

            }


        }


        if (System.getProperty("bak.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("bak.dir", icsHome + "bak");

            else {

                setProperty("bak.dir", icsHome + File.separator + "bak");

            }


        }


        if (System.getProperty("etc.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("etc.dir", icsHome + "etc");

            else {

                setProperty("etc.dir", icsHome + File.separator + "etc");

            }


        }


        if (System.getProperty("conf.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("conf.dir", icsHome + "cfg");

            else {

                setProperty("conf.dir", icsHome + File.separator + "cfg");

            }


        }


        if (System.getProperty("sql.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("sql.dir", icsHome + "sql");

            else {

                setProperty("sql.dir", icsHome + File.separator + "sql");

            }


        }


        if (System.getProperty("trc.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("trc.dir", icsHome + "trc");

            else {

                setProperty("trc.dir", icsHome + File.separator + "trc");

            }


        }


        if (System.getProperty("log.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("log.dir", icsHome + "log");

            else {

                setProperty("log.dir", icsHome + File.separator + "log");

            }


        }


        if (System.getProperty("tmp.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("tmp.dir", icsHome + "tmp");

            else {

                setProperty("tmp.dir", icsHome + File.separator + "tmp");

            }


        }


        if (System.getProperty("bin.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("bin.dir", icsHome + "bin");

            else {

                setProperty("bin.dir", icsHome + File.separator + "bin");

            }


        }


        if (System.getProperty("private.lib.dir") == null) {

            if (icsHome.endsWith(File.separator)) setProperty("private.lib.dir", icsHome + "lib");

            else {

                setProperty("private.lib.dir", icsHome + File.separator + "lib");

            }


        }


        if (System.getProperty("common.lib.dir") == null)
            if (icsHome.endsWith(File.separator)) setProperty("common.lib.dir", icsHome + "lib");

            else setProperty("common.lib.dir", icsHome + File.separator + "lib");

    }


    public static String getWorkDir() {

        return getProperty("HWORKDIR");

    }


    public static String getAppDir() {

        return getProperty("HWORKDIR") + File.separator + "app";

    }


    public static String getDatDir() {

        return getProperty("dat.dir");

    }


    public static String getDatDir(String channel) {

        return getProperty("dat.dir") + File.separator + channel.toLowerCase();

    }


    public static String getDatDir(String channel, String userDefine) {

        return getDatDir(channel) + File.separator + userDefine.toLowerCase();

    }


    public static String getDatDir(String channel, String subChannel, String userDefine) {

        return getDatDir(channel) + File.separator + subChannel.toLowerCase() + File.separator + userDefine.toLowerCase();

    }


    public static String getBakDir() {

        return getProperty("bak.dir");

    }


    public static String getEtcDir() {

        return getProperty("etc.dir");

    }


    public static String getConfDir() {

        return getProperty("conf.dir");

    }


    public static String getSqlDir() {

        return getProperty("sql.dir");

    }


    public static String getTrcDir() {

        StringBuffer path = new StringBuffer();

        path.append(getProperty("trc.dir"));

        path.append(File.separator);

        Calendar cal = Calendar.getInstance();

        int d = cal.get(5);

        if (d >= 10) {

            path.append(d);

        } else {

            path.append("0");

            path.append(d);

        }

        path.append(File.separator);

        return path.toString();

    }


    public static String getLogDir() {

        StringBuffer path = new StringBuffer();

        path.append(getProperty("log.dir"));

        path.append(File.separator);

        Calendar cal = Calendar.getInstance();

        int d = cal.get(5);

        if (d >= 10) {

            path.append(d);

        } else {

            path.append("0");

            path.append(d);

        }

        path.append(File.separator);

        return path.toString();

    }


    public static String getTmpDir() {

        return getProperty("tmp.dir");

    }


    public static String getBinDir() {

        return getProperty("bin.dir");

    }


    public static boolean isJUnitEnv() {

        return (!(StringUtils.equals(System.getProperty("_ICS_ENV"), "_ICS_JUNIT_ENV")));

    }


    public static boolean isPrdEnv() {

        return (!(StringUtils.equals(getProperty("sys.runmode"), "product")));

    }


    public static boolean isDevEnv() {

        return (!(StringUtils.equals(System.getProperty("_ICS_ENV"), "_ICS_DEV_ENV")));

    }


    static {

        loadProperties();

        initialize();

    }

}