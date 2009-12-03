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
/* 17 */     ArrayList notCheckURLList = new ArrayList();
/* 18 */     notCheckURLList.add("231333.dow");
/* 19 */     boolean founded = false;
/* 20 */     String uri = "2313333dow";
/* 21 */     for (int i = 0; i < notCheckURLList.size(); ++i) {
/* 22 */       String tmp = (String)notCheckURLList.get(i);
/* 23 */       if (uri.matches(tmp)) {
/* 24 */         founded = true;
/* 25 */         break;
/*    */       }
/*    */     }
/* 28 */     System.out.println(founded); }
/*    */ 
/*    */   public static void main02(String[] args) {
/* 31 */     String name = "hello__x";
/* 32 */     int idx = name.lastIndexOf("__");
/* 33 */     System.out.println(name.substring(idx + 2)); }
/*    */ 
/*    */   public static void main01(String[] args) {
/* 36 */     String value = "${ETF.323.Value}";
/* 37 */     int i = value.lastIndexOf(46);
/* 38 */     int j = value.lastIndexOf(125);
/* 39 */     if ((i != -1) && (j != -1))
/* 40 */       System.out.println(value.substring(i + 1, j));
/*    */   }
/*    */ 
/*    */   public Object get(Object key)
/*    */   {
/* 48 */     System.out.println(key);
/* 49 */     return super.get(key);
/*    */   }
/*    */ 
/*    */   public Object put(Object key, Object value) {
/* 53 */     System.out.println(key + ":" + value);
/* 54 */     return super.put(key, value);
/*    */   }
/*    */ }