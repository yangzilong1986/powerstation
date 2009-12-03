/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.PropertyResourceBundle;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ import sun.misc.SoftCache;
/*     */ 
/*     */ public class ReloadablePropertyResourceBundle extends PropertyResourceBundle
/*     */ {
/*     */   private static final String FILE_SUFIX = ".properties";
/*  19 */   private static SoftCache cacheList = new SoftCache();
/*  20 */   private static final AGResourceCacheKey cacheKey = new AGResourceCacheKey(null);
/*     */ 
/*  81 */   private static final Object NOLOADER_NOTFOUND = new Integer(-1);
/*  82 */   private long lastModified = 0L;
/*  83 */   private String fileName = "";
/*     */ 
/*     */   public ReloadablePropertyResourceBundle(InputStream stream) throws IOException
/*     */   {
/*  87 */     super(stream);
/*     */   }
/*     */ 
/*     */   public ReloadablePropertyResourceBundle(InputStream stream, String fileName, long lastModified) throws IOException
/*     */   {
/*  92 */     super(stream);
/*  93 */     this.fileName = fileName;
/*  94 */     this.lastModified = lastModified;
/*     */   }
/*     */ 
/*     */   private static ClassLoader getLoader() {
/*  98 */     Class[] stack = new SecurityManager() {
/*     */       public Class[] getClassContext() {
/* 100 */         return super.getClassContext();
/*     */       }
/*     */     }
/*  98 */     .getClassContext();
/*     */ 
/* 104 */     Class c = stack[2];
/* 105 */     ClassLoader cl = (c == null) ? null : c.getClassLoader();
/* 106 */     if (cl == null) {
/* 107 */       cl = ClassLoader.getSystemClassLoader();
/*     */     }
/* 109 */     return cl;
/*     */   }
/*     */ 
/*     */   public static ResourceBundle getResourceBundle(String baseName) throws MissingResourceException
/*     */   {
/* 114 */     return getResourceBundle(baseName, Locale.getDefault());
/*     */   }
/*     */ 
/*     */   public static ResourceBundle getResourceBundle(String baseName, Locale locale) throws MissingResourceException
/*     */   {
/* 119 */     return getResourceBundle(baseName, locale, getLoader());
/*     */   }
/*     */ 
/*     */   public static ResourceBundle getResourceBundle(String baseName, Locale locale, ClassLoader loader)
/*     */     throws MissingResourceException
/*     */   {
/* 125 */     StringBuffer localeName = new StringBuffer("_").append(locale.toString());
/*     */ 
/* 127 */     if (locale.toString().equals("")) {
/* 128 */       localeName.setLength(0);
/*     */     }
/* 130 */     ResourceBundle lookup = searchBundle(baseName, localeName, loader, false);
/*     */ 
/* 132 */     if (lookup == null) {
/* 133 */       localeName.setLength(0);
/* 134 */       localeName.append("_").append(Locale.getDefault().toString());
/* 135 */       lookup = searchBundle(baseName, localeName, loader, true);
/* 136 */       if (lookup == null) {
/* 137 */         throw new MissingResourceException("Can't find resource for base name " + baseName + ", locale " + locale, baseName + "_" + locale, "");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 144 */     return lookup;
/*     */   }
/*     */ 
/*     */   private static URL getUrl(String name, ClassLoader loader) {
/* 148 */     URL url = (URL)AccessController.doPrivileged(new PrivilegedAction(loader, name) { private final ClassLoader val$loader;
/*     */       private final String val$name;
/*     */ 
/*     */       public Object run() { if (this.val$loader != null) {
/* 152 */           return this.val$loader.getResource(this.val$name);
/*     */         }
/* 154 */         return ClassLoader.getSystemResource(this.val$name);
/*     */       }
/*     */     });
/* 158 */     return url;
/*     */   }
/*     */ 
/*     */   private static ResourceBundle searchBundle(String baseName, StringBuffer localeName, ClassLoader loader, boolean includeBase)
/*     */   {
/*     */     Object NOTFOUND;
/* 164 */     String localeStr = localeName.toString();
/* 165 */     String baseFileName = baseName.replace('.', '/');
/* 166 */     Object lookup = null;
/*     */ 
/* 168 */     Vector cacheCandidates = new Vector();
/*     */ 
/* 170 */     InputStream stream = null;
/* 171 */     URL url = null;
/* 172 */     long lastDate = 0L;
/* 173 */     URLConnection urlCon = null;
/*     */ 
/* 176 */     if (loader == null)
/* 177 */       NOTFOUND = NOLOADER_NOTFOUND;
/*     */     else
/* 179 */       NOTFOUND = loader;
/*     */     while (true)
/*     */     {
/*     */       int lastUnderbar;
/* 183 */       String searchName = baseName + localeStr;
/*     */ 
/* 185 */       searchName = baseFileName + localeStr + ".properties";
/* 186 */       String resName = searchName;
/*     */ 
/* 189 */       synchronized (cacheList) {
/* 190 */         cacheKey.setKeyValues(loader, searchName);
/* 191 */         lookup = cacheList.get(cacheKey);
/*     */ 
/* 195 */         if (lookup == NOTFOUND) {
/* 196 */           localeName.setLength(0);
/* 197 */           break label490:
/*     */         }
/* 199 */         if (lookup != null)
/*     */         {
/* 201 */           if (lookup instanceof ReloadablePropertyResourceBundle) {
/* 202 */             String fileName = ((ReloadablePropertyResourceBundle)lookup).fileName;
/* 203 */             url = getUrl(fileName, loader);
/* 204 */             if (url != null) {
/* 205 */               lastDate = new File(url.getFile()).lastModified();
/*     */             }
/* 207 */             if (((url != null) && (((ReloadablePropertyResourceBundle)lookup).lastModified == lastDate)) || (lastDate == 0L))
/*     */             {
/* 209 */               localeName.setLength(0);
/* 210 */               break label490:
/*     */             }
/* 212 */             url = null;
/* 213 */             lookup = null;
/* 214 */             cacheList.remove(cacheKey);
/*     */           }
/*     */           else {
/* 217 */             localeName.setLength(0);
/* 218 */             break label490:
/*     */           }
/*     */         }
/* 221 */         cacheCandidates.addElement(cacheKey.clone());
/* 222 */         cacheKey.setKeyValues(null, "");
/*     */       }
/*     */ 
/* 225 */       if (url == null) {
/* 226 */         url = getUrl(resName, loader);
/*     */       }
/* 228 */       if (url != null)
/*     */         try {
/* 230 */           urlCon = url.openConnection();
/*     */         }
/*     */         catch (Exception e) {
/*     */         }
/* 234 */       if (urlCon != null)
/*     */         try {
/* 236 */           stream = urlCon.getInputStream();
/* 237 */           lastDate = new File(url.getFile()).lastModified();
/*     */         }
/*     */         catch (IOException e) {
/*     */         }
/* 241 */       if (stream != null)
/*     */       {
/* 243 */         stream = new BufferedInputStream(stream); }
/*     */       try {
/* 245 */         lookup = new ReloadablePropertyResourceBundle(stream, resName, lastDate);
/*     */         try
/*     */         {
/* 251 */           stream.close(); } catch (Exception e) { } } catch (Exception e) { try { stream.close(); } catch (Exception e) { } } finally { try { stream.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 266 */       if (lastUnderbar == -1) {
/*     */         break;
/*     */       }
/* 269 */       localeStr = localeStr.substring(0, lastUnderbar);
/* 270 */       localeName.setLength(lastUnderbar);
/*     */     }
/*     */ 
/* 276 */     if ((lookup == null) && (includeBase == true)) {
/* 277 */       label490: lookup = NOTFOUND;
/*     */     }
/* 279 */     if (lookup != null) {
/* 280 */       synchronized (cacheList)
/*     */       {
/* 283 */         for (int i = 0; i < cacheCandidates.size(); ++i) {
/* 284 */           cacheList.put(cacheCandidates.elementAt(i), lookup);
/*     */         }
/*     */       }
/*     */     }
/* 288 */     if ((lookup == NOTFOUND) || (lookup == null)) {
/* 289 */       return null;
/*     */     }
/* 291 */     return ((ResourceBundle)lookup);
/*     */   }
/*     */ 
/*     */   private static class AGResourceCacheKey
/*     */     implements Cloneable
/*     */   {
/*     */     private SoftReference loaderRef;
/*     */     private String searchName;
/*     */     private int hashCodeCache;
/*     */ 
/*     */     private AGResourceCacheKey()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean equals(Object other)
/*     */     {
/*  28 */       if (this == other) {
/*  29 */         return true;
/*     */       }
/*  31 */       if (null == other) {
/*  32 */         return false;
/*     */       }
/*  34 */       if (!(other instanceof AGResourceCacheKey)) {
/*  35 */         return false;
/*     */       }
/*  37 */       AGResourceCacheKey otherEntry = (AGResourceCacheKey)other;
/*  38 */       boolean result = this.hashCodeCache == otherEntry.hashCodeCache;
/*  39 */       if (result)
/*     */       {
/*  41 */         result = this.searchName.equals(otherEntry.searchName);
/*  42 */         if (result) {
/*  43 */           boolean hasLoaderRef = this.loaderRef != null;
/*     */ 
/*  45 */           result = ((hasLoaderRef) && (otherEntry.loaderRef != null)) || (this.loaderRef == otherEntry.loaderRef);
/*     */ 
/*  49 */           if ((result) && (hasLoaderRef)) {
/*  50 */             result = this.loaderRef.get() == otherEntry.loaderRef.get();
/*     */           }
/*     */         }
/*     */       }
/*  54 */       return result;
/*     */     }
/*     */ 
/*     */     public int hashCode() {
/*  58 */       return this.hashCodeCache;
/*     */     }
/*     */ 
/*     */     public Object clone() {
/*     */       try {
/*  63 */         return super.clone();
/*     */       } catch (CloneNotSupportedException e) {
/*  65 */         throw new InternalError();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void setKeyValues(ClassLoader loader, String searchName) {
/*  70 */       this.searchName = searchName;
/*  71 */       this.hashCodeCache = searchName.hashCode();
/*  72 */       if (loader == null) {
/*  73 */         this.loaderRef = null;
/*     */       } else {
/*  75 */         this.loaderRef = new SoftReference(loader);
/*  76 */         this.hashCodeCache ^= loader.hashCode();
/*     */       }
/*     */     }
/*     */ 
/*     */     AGResourceCacheKey(ReloadablePropertyResourceBundle.1 x0)
/*     */     {
/*     */     }
/*     */   }
/*     */ }