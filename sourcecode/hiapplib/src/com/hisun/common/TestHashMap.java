/*    */ package com.hisun.common;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class TestHashMap extends HashMap
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 15 */     ArrayList notCheckURLList = new ArrayList();
/* 16 */     notCheckURLList.add("231333.dow");
/* 17 */     boolean founded = false;
/* 18 */     String uri = "2313333dow";
/* 19 */     for (int i = 0; i < notCheckURLList.size(); ++i) {
/* 20 */       String tmp = (String)notCheckURLList.get(i);
/* 21 */       if (uri.matches(tmp)) {
/* 22 */         founded = true;
/* 23 */         break;
/*    */       }
/*    */     }
/* 26 */     System.out.println(founded); }
/*    */ 
/*    */   public static void main02(String[] args) {
/* 29 */     String name = "hello__x";
/* 30 */     int idx = name.lastIndexOf("__");
/* 31 */     System.out.println(name.substring(idx + 2)); }
/*    */ 
/*    */   public static void main01(String[] args) {
/* 34 */     String value = "${ETF.323.Value}";
/* 35 */     int i = value.lastIndexOf(46);
/* 36 */     int j = value.lastIndexOf(125);
/* 37 */     if ((i != -1) && (j != -1))
/* 38 */       System.out.println(value.substring(i + 1, j));
/*    */   }
/*    */ 
/*    */   public Object get(Object key)
/*    */   {
/* 46 */     System.out.println(key);
/* 47 */     return super.get(key);
/*    */   }
/*    */ 
/*    */   public Object put(Object key, Object value) {
/* 51 */     System.out.println(key + ":" + value);
/* 52 */     return super.put(key, value);
/*    */   }
/*    */ }