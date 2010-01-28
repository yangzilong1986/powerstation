package com.hisun.util;

public final class Copy_2_of_HiStringManager {
    private MessageResources resources;
    private static Copy_2_of_HiStringManager instance;

    public static synchronized Copy_2_of_HiStringManager getManager() {

        if (instance == null) {

            instance = new Copy_2_of_HiStringManager();
        }
        return instance;
    }

    public Copy_2_of_HiStringManager() {

        MessageResourcesFactory factory = PropertyMessageResourcesFactory.createFactory();

        this.resources = factory.createResources("conf/message");
    }

    public String getString(String key) {

        return this.resources.getMessage(key);
    }

    public String getString(String key, Object[] args) {

        return this.resources.getMessage(key, args);
    }

    public String getString(String key, Object arg) {

        Object[] args = {arg};

        return getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2) {

        Object[] args = {arg1, arg2};

        return getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3) {

        Object[] args = {arg1, arg2, arg3};

        return getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4) {

        Object[] args = {arg1, arg2, arg3, arg4};

        return getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {

        Object[] args = {arg1, arg2, arg3, arg4, arg5};

        return getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {

        Object[] args = {arg1, arg2, arg3, arg4, arg5, arg6};

        return getString(key, args);
    }
}