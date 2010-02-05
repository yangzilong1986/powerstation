package com.hisun.util;

import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class PropertyMessageResources extends MessageResources {
    protected static final Logger log = HiLog.getLogger("SYS.trc");

    protected long lastModified = -1L;

    protected long lastLoaded = -1L;
    private int checkInterval;
    protected HashMap messages = new HashMap();

    public PropertyMessageResources(MessageResourcesFactory factory, String config) {
        super(factory, config);
        log.debug("Initializing, config='" + config + "'");
    }

    public PropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);
        loadLocale(localeKey(this.defaultLocale));
        log.debug("Initializing, config='" + config + "', returnNull=" + returnNull);
    }

    public void setMode(String mode) {
        String value = (mode == null) ? null : mode.trim();
    }

    public String getMessage(Locale locale, String key) {
        if (log.isDebugEnabled()) {
            log.debug("getMessage(" + locale + "," + key + ")");
        }

        String localeKey = localeKey(locale);
        String originalKey = messageKey(localeKey, key);
        String message = null;

        message = findMessage(locale, key, originalKey);
        if (message != null) {
            return message;
        }

        if (this.returnNull) {
            return null;
        }
        return "???" + messageKey(locale, key) + "???";
    }

    public synchronized void reload(Locale locale) {
        clear();
        loadLocale(localeKey(locale));
    }

    protected synchronized void loadLocale(String localeKey) {
        if (log.isDebugEnabled()) {
            log.debug("loadLocale(" + localeKey + ")");
        }

        String name = this.config.replace('.', '/');

        if (localeKey.length() > 0) {
            name = name + "_" + localeKey;
        }

        name = name + ".properties";

        InputStream is = null;
        Properties props = new Properties();

        if (log.isDebugEnabled()) {
            log.debug("  Loading resource '" + name + "'");
        }

        if (log.isInfoEnabled()) {
            log.info("load:{" + HiICSProperty.getWorkDir() + File.separator + name + "}");
            log.info(this.messages);
            log.info(this.formats);
        }
        File f = new File(HiICSProperty.getWorkDir() + File.separator + name);
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (is != null) {
            try {
                props.load(is);
            } catch (IOException e) {
                log.error("loadLocale()", e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("loadLocale()", e);
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("  Loading resource completed");
            }
        } else if (log.isWarnEnabled()) {
            log.warn("  Resource " + name + " Not Found.");
        }

        if (props.size() < 1) {
            return;
        }

        synchronized (this.messages) {
            Iterator names = props.keySet().iterator();

            while (names.hasNext()) {
                String key = (String) names.next();

                if (log.isDebugEnabled()) {
                    log.debug("  Saving message key '" + messageKey(localeKey, key));
                }

                this.messages.put(messageKey(localeKey, key), props.getProperty(key));
            }
        }

        this.lastLoaded = System.currentTimeMillis();
        this.lastModified = f.lastModified();
    }

    private String findMessage(Locale locale, String key, String originalKey) {
        String localeKey = localeKey(locale);
        String message = null;
        message = findMessage(localeKey, key, originalKey);
        return message;
    }

    private String findMessage(String localeKey, String key, String originalKey) {
        String messageKey = messageKey(localeKey, key);

        boolean addIt = !(messageKey.equals(originalKey));

        synchronized (this.messages) {
            String message = (String) this.messages.get(messageKey);
            if ((message != null) && (addIt)) {
                this.messages.put(originalKey, message);
            }

            return message;
        }
    }

    public boolean isModified(Locale locale) {
        String localeKey = localeKey(locale);

        return isModified(localeKey);
    }

    public void clear() {
        this.formats.clear();
        this.messages.clear();
    }

    protected boolean isModified(String localeKey) {
        if (System.currentTimeMillis() - this.lastLoaded < this.checkInterval * 1000) {
            return false;
        }
        String name = this.config.replace('.', '/');
        if (localeKey.length() > 0) {
            name = name + "_" + localeKey;
        }

        name = name + ".properties";

        File f = new File(HiICSProperty.getWorkDir() + File.separator + name);
        if ((this.lastModified != -1L) && (f.lastModified() != -1L) && (this.lastModified != f.lastModified())) {
            this.lastLoaded = System.currentTimeMillis();
            this.lastModified = f.lastModified();
            log.info("file:[" + name + "] modified");
            return true;
        }
        return false;
    }

    public int getCheckInterval() {
        return this.checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
    }
}