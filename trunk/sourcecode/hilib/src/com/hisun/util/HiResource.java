/*    */ package com.hisun.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class HiResource
/*    */ {
/*    */   public static InputStream getResourceAsStream(String name)
/*    */   {
/* 19 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 20 */     return loader.getResourceAsStream(name);
/*    */   }
/*    */ 
/*    */   public static URL getResource(String name)
/*    */   {
/* 29 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 30 */     return loader.getResource(name);
/*    */   }
/*    */ 
/*    */   public static Class loadClass(String name)
/*    */     throws ClassNotFoundException
/*    */   {
/* 40 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 41 */     return loader.loadClass(name);
/*    */   }
/*    */ 
/*    */   public static Class loadClassPrefix(String name)
/*    */     throws ClassNotFoundException
/*    */   {
/* 51 */     return loadClass("com.hisun.specproc." + name);
/*    */   }
/*    */ }