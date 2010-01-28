 package com.hisun.hilib;
 
 import java.lang.reflect.Method;
 
 class HiFunctionItem
 {
   Method method;
   Object object;
 
   public HiFunctionItem(Object object, Method method)
   {
     this.object = object;
     this.method = method;
   }
 }