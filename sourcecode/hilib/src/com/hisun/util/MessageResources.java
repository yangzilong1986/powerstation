package com.hisun.util;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;


public abstract class MessageResources implements Serializable {
    protected static Logger log = HiLog.getLogger("SYS.trc");

    protected static MessageResourcesFactory defaultFactory = null;
    protected String config;
    protected Locale defaultLocale;
    protected MessageResourcesFactory factory;
    protected HashMap formats;
    protected boolean returnNull;
    private boolean escape;


    public MessageResources(MessageResourcesFactory factory, String config) {

        this(factory, config, false);

    }


    public MessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {

        this.config = null;


        this.defaultLocale = Locale.getDefault();


        this.factory = null;


        this.formats = new HashMap();


        this.returnNull = false;


        this.escape = true;


        this.factory = factory;

        this.config = config;

        this.returnNull = returnNull;

    }


    public String getConfig() {

        return this.config;

    }


    public MessageResourcesFactory getFactory() {

        return this.factory;

    }


    public boolean getReturnNull() {

        return this.returnNull;

    }


    public void setReturnNull(boolean returnNull) {

        this.returnNull = returnNull;

    }


    public boolean isEscape() {

        return this.escape;

    }


    public void setEscape(boolean escape) {

        this.escape = escape;

    }


    public String getMessage(String key) {

        return getMessage((Locale) null, key, null);

    }


    public String getMessage(String key, Object[] args) {

        return getMessage((Locale) null, key, args);

    }


    public String getMessage(String key, Object arg0) {

        return getMessage((Locale) null, key, arg0);

    }


    public String getMessage(String key, Object arg0, Object arg1) {

        return getMessage((Locale) null, key, arg0, arg1);

    }


    public String getMessage(String key, Object arg0, Object arg1, Object arg2) {

        return getMessage((Locale) null, key, arg0, arg1, arg2);

    }


    public String getMessage(String key, Object arg0, Object arg1, Object arg2, Object arg3) {

        return getMessage((Locale) null, key, arg0, arg1, arg2, arg3);

    }


    public String getMessage(String key, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {

        return getMessage((Locale) null, key, new Object[]{arg0, arg1, arg2, arg3, arg4});

    }


    public String getMessage(String key, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {

        return getMessage((Locale) null, key, new Object[]{arg0, arg1, arg2, arg3, arg4, arg5});

    }


    public abstract String getMessage(Locale paramLocale, String paramString);


    public abstract boolean isModified(Locale paramLocale);


    public abstract void reload(Locale paramLocale);


    public String getMessage(Locale locale, String key, Object[] args) {

        if (locale == null) {

            locale = this.defaultLocale;

        }

        MessageFormat format = null;

        String formatKey = messageKey(locale, key);


        synchronized (this.formats) {

            if (isModified(locale)) {

                reload(locale);

            }

            format = (MessageFormat) this.formats.get(formatKey);


            if (format == null) {

                String formatString = getMessage(locale, key);


                if (formatString == null) {

                    return "???" + formatKey + "???";

                }


                format = new MessageFormat(escape(formatString));

                format.setLocale(locale);

                this.formats.put(formatKey, format);

            }

        }


        return format.format(args);

    }


    public String getMessage(Locale locale, String key, Object arg0) {

        return getMessage(locale, key, new Object[]{arg0});

    }


    public String getMessage(Locale locale, String key, Object arg0, Object arg1) {

        return getMessage(locale, key, new Object[]{arg0, arg1});

    }


    public String getMessage(Locale locale, String key, Object arg0, Object arg1, Object arg2) {

        return getMessage(locale, key, new Object[]{arg0, arg1, arg2});

    }


    public String getMessage(Locale locale, String key, Object arg0, Object arg1, Object arg2, Object arg3) {

        return getMessage(locale, key, new Object[]{arg0, arg1, arg2, arg3});

    }


    public boolean isPresent(String key) {

        return isPresent(null, key);

    }


    public boolean isPresent(Locale locale, String key) {

        String message = getMessage(locale, key);


        if (message == null) {

            return false;

        }

        return ((message.startsWith("???")) && (message.endsWith("???")));

    }


    protected String escape(String string) {

        if (!(isEscape())) {

            return string;

        }


        if ((string == null) || (string.indexOf(39) < 0)) {

            return string;

        }


        int n = string.length();

        StringBuffer sb = new StringBuffer(n);


        for (int i = 0; i < n; ++i) {

            char ch = string.charAt(i);


            if (ch == '\'') {

                sb.append('\'');

            }


            sb.append(ch);

        }


        return sb.toString();

    }


    protected String localeKey(Locale locale) {

        return ((locale == null) ? "" : locale.toString());

    }


    protected String messageKey(Locale locale, String key) {

        return localeKey(locale) + "." + key;

    }


    protected String messageKey(String localeKey, String key) {

        return localeKey + "." + key;

    }


    public static synchronized MessageResources getMessageResources(String config) {

        if (defaultFactory == null) {

            defaultFactory = MessageResourcesFactory.createFactory();

        }


        return defaultFactory.createResources(config);

    }


    public void log(String message) {

        log.debug(message);

    }


    public void log(String message, Throwable throwable) {

        log.debug(message, throwable);

    }

}