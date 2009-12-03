/*     */ package com.hisun.loader;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public final class HiClassLoaderFactory
/*     */ {
/*  39 */   private static Logger log = HiLog.getLogger("loader.trc");
/*     */ 
/*  41 */   protected static final Integer IS_DIR = new Integer(0);
/*     */ 
/*  43 */   protected static final Integer IS_JAR = new Integer(1);
/*     */ 
/*  45 */   protected static final Integer IS_GLOB = new Integer(2);
/*     */ 
/*  47 */   protected static final Integer IS_URL = new Integer(3);
/*     */ 
/*     */   public static ClassLoader createClassLoader(File[] unpacked, File[] packed, ClassLoader parent)
/*     */     throws Exception
/*     */   {
/*  74 */     return createClassLoader(unpacked, packed, null, parent);
/*     */   }
/*     */ 
/*     */   public static ClassLoader createClassLoader(File[] unpacked, File[] packed, URL[] urls, ClassLoader parent)
/*     */     throws Exception
/*     */   {
/*     */     int i;
/* 107 */     ArrayList list = new ArrayList();
/*     */ 
/* 110 */     if (unpacked != null) {
/* 111 */       for (i = 0; i < unpacked.length; ++i) {
/* 112 */         File file = unpacked[i];
/* 113 */         if (!(file.exists())) continue; if (!(file.canRead()))
/*     */           continue;
/* 115 */         file = new File(file.getCanonicalPath() + File.separator);
/* 116 */         URL url = file.toURL();
/* 117 */         log.debug("  Including directory " + url);
/* 118 */         list.add(url);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 123 */     if (packed != null) {
/* 124 */       for (i = 0; i < packed.length; ++i) {
/* 125 */         File directory = packed[i];
/* 126 */         if ((!(directory.isDirectory())) || (!(directory.exists()))) continue; if (!(directory.canRead())) {
/*     */           continue;
/*     */         }
/* 129 */         String[] filenames = directory.list();
/* 130 */         for (int j = 0; j < filenames.length; ++j) {
/* 131 */           String filename = filenames[j].toLowerCase();
/* 132 */           if (!(filename.endsWith(".jar")))
/*     */             continue;
/* 134 */           File file = new File(directory, filenames[j]);
/* 135 */           log.debug("  Including jar file " + file.getAbsolutePath());
/*     */ 
/* 137 */           URL url = file.toURL();
/* 138 */           list.add(url);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 144 */     URL[] array = (URL[])(URL[])list.toArray(new URL[list.size()]);
/* 145 */     ClassLoader classLoader = null;
/* 146 */     if (parent == null)
/* 147 */       classLoader = new HiStandardClassLoader(array);
/*     */     else
/* 149 */       classLoader = new HiStandardClassLoader(array, parent);
/* 150 */     return classLoader;
/*     */   }
/*     */ 
/*     */   public static ClassLoader createClassLoader(String[] locations, Integer[] types, ClassLoader parent)
/*     */     throws Exception
/*     */   {
/* 178 */     ArrayList list = new ArrayList();
/*     */ 
/* 180 */     if ((locations != null) && (types != null) && (locations.length == types.length))
/*     */     {
/* 182 */       for (int i = 0; i < locations.length; ++i) {
/* 183 */         String location = locations[i];
/* 184 */         if (types[i] == IS_URL) {
/* 185 */           URL url = new URL(location);
/* 186 */           log.debug("  Including URL " + url);
/* 187 */           list.add(url);
/*     */         }
/*     */         else
/*     */         {
/*     */           File directory;
/*     */           URL url;
/* 188 */           if (types[i] == IS_DIR) {
/* 189 */             directory = new File(location);
/* 190 */             directory = new File(directory.getCanonicalPath());
/* 191 */             if ((!(directory.exists())) || (!(directory.isDirectory()))) continue; if (!(directory.canRead())) {
/*     */               continue;
/*     */             }
/* 194 */             url = directory.toURL();
/* 195 */             log.debug("  Including directory " + url);
/* 196 */             list.add(url);
/* 197 */           } else if (types[i] == IS_JAR) {
/* 198 */             File file = new File(location);
/* 199 */             file = new File(file.getCanonicalPath());
/* 200 */             if (!(file.exists())) continue; if (!(file.canRead()))
/*     */               continue;
/* 202 */             url = file.toURL();
/* 203 */             log.debug("  Including jar file " + url);
/* 204 */             list.add(url);
/* 205 */           } else if (types[i] == IS_GLOB) {
/* 206 */             directory = new File(location);
/* 207 */             if ((!(directory.exists())) || (!(directory.isDirectory()))) continue; if (!(directory.canRead())) {
/*     */               continue;
/*     */             }
/* 210 */             log.debug("  Including directory glob " + directory.getAbsolutePath());
/*     */ 
/* 212 */             String[] filenames = directory.list();
/* 213 */             for (int j = 0; j < filenames.length; ++j) {
/* 214 */               String filename = filenames[j].toLowerCase();
/* 215 */               if (!(filename.endsWith(".jar")))
/*     */                 continue;
/* 217 */               File file = new File(directory, filenames[j]);
/* 218 */               file = new File(file.getCanonicalPath());
/* 219 */               if (!(file.exists())) continue; if (!(file.canRead()))
/*     */                 continue;
/* 221 */               log.debug("    Including glob jar file " + file.getAbsolutePath());
/*     */ 
/* 223 */               URL url = file.toURL();
/* 224 */               list.add(url);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 231 */     URL[] array = (URL[])(URL[])list.toArray(new URL[list.size()]);
/* 232 */     for (int i = 0; i < array.length; ++i) {
/* 233 */       log.debug("  location " + i + " is " + array[i]);
/*     */     }
/* 235 */     ClassLoader classLoader = null;
/* 236 */     if (parent == null)
/* 237 */       classLoader = new HiStandardClassLoader(array);
/*     */     else
/* 239 */       classLoader = new HiStandardClassLoader(array, parent);
/* 240 */     return classLoader;
/*     */   }
/*     */ 
/*     */   public static ClassLoader createClassLoader(String name, String appName, ClassLoader parent)
/*     */     throws HiException
/*     */   {
/* 246 */     String value = HiICSProperty.getProperty(name + ".loader");
/* 247 */     log.debug("Creating new class loader:[" + name + "][" + value + "]");
/* 248 */     if ((value == null) || (value.equals(""))) {
/* 249 */       return parent;
/*     */     }
/* 251 */     ArrayList repositoryLocations = new ArrayList();
/* 252 */     ArrayList repositoryTypes = new ArrayList();
/*     */ 
/* 255 */     StringTokenizer tokenizer = new StringTokenizer(value, ",");
/* 256 */     while (tokenizer.hasMoreElements()) {
/* 257 */       String repository = tokenizer.nextToken();
/*     */       try
/*     */       {
/* 261 */         URL url = new URL(repository);
/* 262 */         repositoryLocations.add(repository);
/* 263 */         repositoryTypes.add(IS_URL);
/*     */       }
/*     */       catch (MalformedURLException e)
/*     */       {
/* 269 */         if (repository.endsWith("*.jar")) {
/* 270 */           repository = repository.substring(0, repository.length() - "*.jar".length());
/*     */ 
/* 272 */           repositoryLocations.add(repository);
/* 273 */           repositoryTypes.add(IS_GLOB);
/* 274 */         } else if (repository.endsWith(".jar")) {
/* 275 */           repositoryLocations.add(repository);
/* 276 */           repositoryTypes.add(IS_JAR);
/*     */         } else {
/* 278 */           repositoryLocations.add(repository);
/* 279 */           repositoryTypes.add(IS_DIR); }
/*     */       }
/*     */     }
/* 282 */     repositoryLocations.add(HiICSProperty.getAppDir() + File.separator + appName);
/* 283 */     repositoryTypes.add(IS_DIR);
/* 284 */     repositoryLocations.add(HiICSProperty.getAppDir() + File.separator + appName + File.separator + "applib/");
/* 285 */     repositoryTypes.add(IS_GLOB);
/*     */ 
/* 287 */     String[] locations = (String[])(String[])repositoryLocations.toArray(new String[0]);
/*     */ 
/* 289 */     for (int j = 0; j < locations.length; ++j) {
/* 290 */       log.debug("locations:{" + locations[j] + "}");
/*     */     }
/* 292 */     Integer[] types = (Integer[])(Integer[])repositoryTypes.toArray(new Integer[0]);
/*     */     try {
/* 294 */       return createClassLoader(locations, types, parent);
/*     */     } catch (Exception e) {
/* 296 */       throw new HiException("", "crate classloader failure", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ClassLoader createClassLoader(String name, ClassLoader parent)
/*     */     throws HiException
/*     */   {
/* 303 */     String value = HiICSProperty.getProperty(name + ".loader");
/*     */ 
/* 305 */     log.debug("Creating new class loader:[" + name + "][" + value + "]");
/* 306 */     if ((value == null) || (value.equals(""))) {
/* 307 */       return parent;
/*     */     }
/* 309 */     ArrayList repositoryLocations = new ArrayList();
/* 310 */     ArrayList repositoryTypes = new ArrayList();
/*     */ 
/* 313 */     StringTokenizer tokenizer = new StringTokenizer(value, ",");
/* 314 */     while (tokenizer.hasMoreElements()) {
/* 315 */       String repository = tokenizer.nextToken();
/*     */       try
/*     */       {
/* 319 */         URL url = new URL(repository);
/* 320 */         repositoryLocations.add(repository);
/* 321 */         repositoryTypes.add(IS_URL);
/*     */       }
/*     */       catch (MalformedURLException e)
/*     */       {
/* 327 */         if (repository.endsWith("*.jar")) {
/* 328 */           repository = repository.substring(0, repository.length() - "*.jar".length());
/*     */ 
/* 330 */           repositoryLocations.add(repository);
/* 331 */           repositoryTypes.add(IS_GLOB);
/* 332 */         } else if (repository.endsWith(".jar")) {
/* 333 */           repositoryLocations.add(repository);
/* 334 */           repositoryTypes.add(IS_JAR);
/*     */         } else {
/* 336 */           repositoryLocations.add(repository);
/* 337 */           repositoryTypes.add(IS_DIR);
/*     */         }
/*     */       }
/*     */     }
/* 341 */     String[] locations = (String[])(String[])repositoryLocations.toArray(new String[0]);
/*     */ 
/* 343 */     Integer[] types = (Integer[])(Integer[])repositoryTypes.toArray(new Integer[0]);
/*     */     try {
/* 345 */       return createClassLoader(locations, types, parent);
/*     */     } catch (Exception e) {
/* 347 */       throw new HiException("", "crate classloader failure", e);
/*     */     }
/*     */   }
/*     */ }