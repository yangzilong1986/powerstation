 package com.hisun.ibs.swcode;
 
 import java.util.HashMap;
 
 public class HiCharHashMap extends HashMap
 {
   private static final long serialVersionUID = 1L;
 
   public void put(int arg0, int arg1)
   {
     put(new HiChar(arg0), new HiChar(arg1));
   }
 
   public HiChar get(int arg0) {
     return get(new HiChar(arg0));
   }
 
   public HiChar get(HiChar arg0) {
     HiChar tmp = get(arg0);
     if (tmp == null) {
       return new HiChar(65535);
     }
     return tmp;
   }
 }