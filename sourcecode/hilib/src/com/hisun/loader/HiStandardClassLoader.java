/*    */ package com.hisun.loader;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ 
/*    */ public class HiStandardClassLoader extends URLClassLoader
/*    */ {
/*    */   public HiStandardClassLoader(URL[] repositories)
/*    */   {
/*  8 */     super(repositories);
/*    */   }
/*    */ 
/*    */   public HiStandardClassLoader(URL[] repositories, ClassLoader parent) {
/* 12 */     super(repositories, parent);
/*    */   }
/*    */ }