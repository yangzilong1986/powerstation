package com.hisun.util;


import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public final class CopyOfHiStringManager {
    private ResourceBundle bundle;
    private Locale locale;
    private static Hashtable managers = new Hashtable();
    private final String bundleName;


    private CopyOfHiStringManager(String packageName) {

        this(packageName, Locale.getDefault());

    }


    private CopyOfHiStringManager(String packageName, Locale loc) {

        this.bundleName = packageName + ".message";


        this.bundle = ReloadablePropertyResourceBundle.getResourceBundle(this.bundleName, loc);


        this.locale = loc;

    }


    public String getString(String key) {

        if (key == null) {

            String msg = "key may not have a null value";


            throw new IllegalArgumentException(msg);

        }


        String str = null;

        try {

            str = this.bundle.getString(key);

        } catch (MissingResourceException mre) {

            str = null;

        }


        return str;

    }


    public String getString(String key, Object[] args) {

        String iString = null;

        String value = getString(key);

        try {

            if (args == null) {

                args = new Object[1];

            }


            Object[] nonNullArgs = args;

            for (int i = 0; i < args.length; ++i) {

                if (args[i] == null) {

                    if (nonNullArgs == args) {

                        nonNullArgs = (Object[]) (Object[]) args.clone();

                    }

                    nonNullArgs[i] = "null";

                }

            }

            if (value == null) {

                value = key;

            }

            MessageFormat mf = new MessageFormat(value);

            mf.setLocale(this.locale);

            iString = mf.format(nonNullArgs, new StringBuffer(), null).toString();


            return iString;

        } catch (IllegalArgumentException iae) {

            StringBuffer buf = new StringBuffer();

            buf.append(value);

            for (int i = 0; i < args.length; ++i) {

                buf.append(" arg[" + i + "]=" + args[i]);

            }

            iString = buf.toString();

        }

        return iString;

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


    private static synchronized CopyOfHiStringManager getManager(String packageName) {

        CopyOfHiStringManager mgr = (CopyOfHiStringManager) managers.get(packageName);

        if (mgr == null) {

            mgr = new CopyOfHiStringManager(packageName);

            managers.put(packageName, mgr);

        }


        return mgr;

    }


    public static synchronized CopyOfHiStringManager getManager() {

        return getManager("conf");

    }


    public static synchronized CopyOfHiStringManager getManager(String packageName, Locale loc) {

        CopyOfHiStringManager mgr = (CopyOfHiStringManager) managers.get(packageName + "_" + loc.toString());


        if (mgr == null) {

            mgr = new CopyOfHiStringManager(packageName, loc);

            managers.put(packageName + "_" + loc.toString(), mgr);

        }

        return mgr;

    }


    public Locale getLocale() {

        return this.locale;

    }


    public void checkForUpdates() {

        if (this.bundle == null) return;

        this.bundle = ReloadablePropertyResourceBundle.getResourceBundle(this.bundleName, this.locale);

    }

}