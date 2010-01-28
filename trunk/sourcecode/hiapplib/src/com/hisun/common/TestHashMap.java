 package com.hisun.common;
 
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class TestHashMap extends HashMap
 {
   public static void main(String[] args)
   {
     ArrayList notCheckURLList = new ArrayList();
     notCheckURLList.add("231333.dow");
     boolean founded = false;
     String uri = "2313333dow";
     for (int i = 0; i < notCheckURLList.size(); ++i) {
       String tmp = (String)notCheckURLList.get(i);
       if (uri.matches(tmp)) {
         founded = true;
         break;
       }
     }
     System.out.println(founded); }
 
   public static void main02(String[] args) {
     String name = "hello__x";
     int idx = name.lastIndexOf("__");
     System.out.println(name.substring(idx + 2)); }
 
   public static void main01(String[] args) {
     String value = "${ETF.323.Value}";
     int i = value.lastIndexOf(46);
     int j = value.lastIndexOf(125);
     if ((i != -1) && (j != -1))
       System.out.println(value.substring(i + 1, j));
   }
 
   public Object get(Object key)
   {
     System.out.println(key);
     return super.get(key);
   }
 
   public Object put(Object key, Object value) {
     System.out.println(key + ":" + value);
     return super.put(key, value);
   }
 }