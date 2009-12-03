/*    */ package com.hisun.ibs.comm;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import java.io.File;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public final class HiNativeHelper
/*    */ {
/* 14 */   private static String[] libraries = null;
/*    */ 
/*    */   private static void loadLibrary() {
/* 17 */     libraries = initializePath("library.path");
/* 18 */     if (libraries == null)
/* 19 */       return;
/* 20 */     for (int i = 0; i < libraries.length; ++i)
/* 21 */       if (!(StringUtils.isEmpty(libraries[i])))
/* 22 */         loadLibrary(libraries[i]);
/*    */   }
/*    */ 
/*    */   public static void setLibLoadStatus(String prop_name, boolean status)
/*    */   {
/* 28 */     if (status)
/* 29 */       System.setProperty(prop_name, Boolean.TRUE.toString());
/*    */     else
/* 31 */       System.setProperty(prop_name, Boolean.FALSE.toString());
/*    */   }
/*    */ 
/*    */   public static boolean getLibLoadStatus(String prop_name) {
/* 35 */     String sValue = System.getProperty(prop_name);
/*    */ 
/* 38 */     return ((sValue == null) || (!(sValue.equalsIgnoreCase(Boolean.TRUE.toString()))));
/*    */   }
/*    */ 
/*    */   public static void loadLibrary(String libName)
/*    */   {
/* 44 */     if (!(getLibLoadStatus(libName))) {
/* 45 */       System.loadLibrary(libName);
/* 46 */       setLibLoadStatus(libName, true);
/*    */     }
/*    */   }
/*    */ 
/*    */   private static String[] initializePath(String propname) {
/* 51 */     String ldpath = HiICSProperty.getProperty(propname, "");
/* 52 */     if (StringUtils.isEmpty(ldpath))
/* 53 */       return null;
/* 54 */     String ps = File.pathSeparator;
/* 55 */     int ldlen = ldpath.length();
/*    */ 
/* 58 */     int i = ldpath.indexOf(ps);
/* 59 */     int n = 0;
/* 60 */     while (i >= 0) {
/* 61 */       ++n;
/* 62 */       i = ldpath.indexOf(ps, i + 1);
/*    */     }
/*    */ 
/* 66 */     String[] paths = new String[n + 1];
/*    */ 
/* 69 */     n = i = 0;
/* 70 */     int j = ldpath.indexOf(ps);
/* 71 */     while (j >= 0) {
/* 72 */       if (j - i > 0)
/* 73 */         paths[(n++)] = ldpath.substring(i, j);
/* 74 */       else if (j - i == 0) {
/* 75 */         paths[(n++)] = ".";
/*    */       }
/* 77 */       i = j + 1;
/* 78 */       j = ldpath.indexOf(ps, i);
/*    */     }
/* 80 */     paths[n] = ldpath.substring(i, ldlen);
/* 81 */     return paths;
/*    */   }
/*    */ }