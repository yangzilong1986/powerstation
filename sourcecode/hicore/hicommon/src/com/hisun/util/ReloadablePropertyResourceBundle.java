package com.hisun.util;

import sun.misc.SoftCache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

public class ReloadablePropertyResourceBundle extends PropertyResourceBundle {
    private static final String FILE_SUFIX = ".properties";
    private static SoftCache cacheList = new SoftCache();
    private static final AGResourceCacheKey cacheKey = new AGResourceCacheKey(null);

    private static final Object NOLOADER_NOTFOUND = new Integer(-1);
    private long lastModified = 0L;
    private String fileName = "";

    public ReloadablePropertyResourceBundle(InputStream stream) throws IOException {
        super(stream);
    }

    public ReloadablePropertyResourceBundle(InputStream stream, String fileName, long lastModified) throws IOException {
        super(stream);
        this.fileName = fileName;
        this.lastModified = lastModified;
    }

    private static ClassLoader getLoader() {
        Class[] stack = new SecurityManager() {
            public Class[] getClassContext() {
                return super.getClassContext();
            }
        }.getClassContext();

        Class c = stack[2];
        ClassLoader cl = (c == null) ? null : c.getClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        return cl;
    }

    public static ResourceBundle getResourceBundle(String baseName) throws MissingResourceException {
        return getResourceBundle(baseName, Locale.getDefault());
    }

    public static ResourceBundle getResourceBundle(String baseName, Locale locale) throws MissingResourceException {
        return getResourceBundle(baseName, locale, getLoader());
    }

    public static ResourceBundle getResourceBundle(String baseName, Locale locale, ClassLoader loader) throws MissingResourceException {
        StringBuffer localeName = new StringBuffer("_").append(locale.toString());

        if (locale.toString().equals("")) {
            localeName.setLength(0);
        }
        ResourceBundle lookup = searchBundle(baseName, localeName, loader, false);

        if (lookup == null) {
            localeName.setLength(0);
            localeName.append("_").append(Locale.getDefault().toString());
            lookup = searchBundle(baseName, localeName, loader, true);
            if (lookup == null) {
                throw new MissingResourceException("Can't find resource for base name " + baseName + ", locale " + locale, baseName + "_" + locale, "");
            }

        }

        return lookup;
    }

    private static URL getUrl(String name, ClassLoader loader) {
        URL url = (URL) AccessController.doPrivileged(new PrivilegedAction(loader, name) {
            private final ClassLoader val$loader;
            private final String val$name;

            public Object run() {
                if (this.val$loader != null) {
                    return this.val$loader.getResource(this.val$name);
                }
                return ClassLoader.getSystemResource(this.val$name);
            }
        });
        return url;
    }

    private static ResourceBundle searchBundle(String baseName, StringBuffer localeName, ClassLoader loader, boolean includeBase) {
        Object NOTFOUND;
        String localeStr = localeName.toString();
        String baseFileName = baseName.replace('.', '/');
        Object lookup = null;

        Vector cacheCandidates = new Vector();

        InputStream stream = null;
        URL url = null;
        long lastDate = 0L;
        URLConnection urlCon = null;

        if (loader == null) NOTFOUND = NOLOADER_NOTFOUND;
        else NOTFOUND = loader;
        while (true) {
            int lastUnderbar;
            String searchName = baseName + localeStr;

            searchName = baseFileName + localeStr + ".properties";
            String resName = searchName;

            synchronized (cacheList) {
                cacheKey.setKeyValues(loader, searchName);
                lookup = cacheList.get(cacheKey);

                if (lookup == NOTFOUND) {
                    localeName.setLength(0);
                    break label490:
                }
                if (lookup != null) {
                    if (lookup instanceof ReloadablePropertyResourceBundle) {
                        String fileName = ((ReloadablePropertyResourceBundle) lookup).fileName;
                        url = getUrl(fileName, loader);
                        if (url != null) {
                            lastDate = new File(url.getFile()).lastModified();
                        }
                        if (((url != null) && (((ReloadablePropertyResourceBundle) lookup).lastModified == lastDate)) || (lastDate == 0L)) {
                            localeName.setLength(0);
                            break label490:
                        }
                        url = null;
                        lookup = null;
                        cacheList.remove(cacheKey);
                    } else {
                        localeName.setLength(0);
                        break label490:
                    }
                }
                cacheCandidates.addElement(cacheKey.clone());
                cacheKey.setKeyValues(null, "");
            }

            if (url == null) {
                url = getUrl(resName, loader);
            }
            if (url != null) try {
                urlCon = url.openConnection();
            } catch (Exception e) {
            }
            if (urlCon != null) try {
                stream = urlCon.getInputStream();
                lastDate = new File(url.getFile()).lastModified();
            } catch (IOException e) {
            }
            if (stream != null) {
                stream = new BufferedInputStream(stream);
            }
            try {
                lookup = new ReloadablePropertyResourceBundle(stream, resName, lastDate);
                try {
                    stream.close();
                } catch (Exception e) {
                }
            } catch (Exception e) {
                try {
                    stream.close();
                } catch (Exception e) {
                }
            } finally {
                try {
                    stream.close();
                } catch (Exception e) {
                }

            }

            if (lastUnderbar == -1) {
                break;
            }
            localeStr = localeStr.substring(0, lastUnderbar);
            localeName.setLength(lastUnderbar);
        }

        if ((lookup == null) && (includeBase == true)) {
            label490:
            lookup = NOTFOUND;
        }
        if (lookup != null) {
            synchronized (cacheList) {
                for (int i = 0; i < cacheCandidates.size(); ++i) {
                    cacheList.put(cacheCandidates.elementAt(i), lookup);
                }
            }
        }
        if ((lookup == NOTFOUND) || (lookup == null)) {
            return null;
        }
        return ((ResourceBundle) lookup);
    }

    private static class AGResourceCacheKey implements Cloneable {
        private SoftReference loaderRef;
        private String searchName;
        private int hashCodeCache;

        private AGResourceCacheKey() {
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (null == other) {
                return false;
            }
            if (!(other instanceof AGResourceCacheKey)) {
                return false;
            }
            AGResourceCacheKey otherEntry = (AGResourceCacheKey) other;
            boolean result = this.hashCodeCache == otherEntry.hashCodeCache;
            if (result) {
                result = this.searchName.equals(otherEntry.searchName);
                if (result) {
                    boolean hasLoaderRef = this.loaderRef != null;

                    result = ((hasLoaderRef) && (otherEntry.loaderRef != null)) || (this.loaderRef == otherEntry.loaderRef);

                    if ((result) && (hasLoaderRef)) {
                        result = this.loaderRef.get() == otherEntry.loaderRef.get();
                    }
                }
            }
            return result;
        }

        public int hashCode() {
            return this.hashCodeCache;
        }

        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                throw new InternalError();
            }
        }

        public void setKeyValues(ClassLoader loader, String searchName) {
            this.searchName = searchName;
            this.hashCodeCache = searchName.hashCode();
            if (loader == null) {
                this.loaderRef = null;
            } else {
                this.loaderRef = new SoftReference(loader);
                this.hashCodeCache ^= loader.hashCode();
            }
        }

        AGResourceCacheKey(ReloadablePropertyResourceBundle.1x0) {
        }
    }
}