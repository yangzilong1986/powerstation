 package com.hisun.atc;
 
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
 import java.util.Iterator;
 
 class HiDataSource
 {
   public static Object get(HiMessageContext ctx, String name)
   {
     return ctx.getBaseSource(name);
   }
 
   public static Object create(HiMessageContext ctx, String name, String type)
   {
     Object o;
     if ("ETF".equalsIgnoreCase(type))
       o = HiETFFactory.createETF();
     else if ("XML".equalsIgnoreCase(type))
       o = HiETFFactory.createETF();
     else if ("MSG".equalsIgnoreCase(type))
       o = new HiMessage();
     else if ("NAMED".equalsIgnoreCase(type))
       o = new ConcurrentHashMap();
     else {
       o = HiETFFactory.createETF();
     }
 
     ctx.setBaseSource(name, o);
     return o;
   }
 
   public static void delete(HiMessageContext ctx, String name) {
     ctx.removeBaseSource(name);
   }
 
   public static void set(HiMessageContext ctx, String name, Object o) {
     ctx.setBaseSource(name, o);
   }
 
   public static String getName(HiMessageContext ctx, Object o) {
     Iterator iter = ctx.getAllPropertyNames();
     while (iter.hasNext()) {
       String name = (String)iter.next();
       if (ctx.getBaseSource(name) == o) {
         return name;
       }
     }
     return null;
   }
 }