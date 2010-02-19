package com.hzjbbis.util;

import java.util.Calendar;

public class FasProperties {
    private static final String PROPS_FILE = "/fas.properties";
    private static final String PROP_FILE_STORE_PATH = "fas.file.store.path";
    private static final String DEFAULT_FILE_STORE_PATH = "fas";
    private static FileBasedProperties props = null;

    public static FileBasedProperties getProperties() {
        if (props == null) {
            synchronized (FasProperties.class) {
                if (props == null) {
                    props = new FileBasedProperties("/fas.properties");
                }
            }
        }

        return props;
    }

    public static String getFileStorePath() {
        String path = getProperties().getProperty("fas.file.store.path");
        if ((path == null) || (path.trim().length() == 0)) {
            path = "fas";
        }

        return FileUtil.getAbsolutePath(path);
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getProperties().getBoolean(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getProperties().getInt(key, defaultValue);
    }

    public static long getSize(String key, long defaultValue) {
        return getProperties().getSize(key, defaultValue);
    }

    public static Calendar getDate(String key) {
        return getProperties().getDate(key);
    }
}