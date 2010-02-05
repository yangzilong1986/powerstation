 package com.hisun.hilib;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 
 public class HiLibManager
 {
   private static Logger log = HiLog.getLogger("lib.trc");
   private static final int LOCALLIB = 1;
   private static final int SPRINGLIB = 2;
   public static final String SPRINGCONF = "spring.config";
 
   public static synchronized int loadComponent()
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("libmanager->loadComponent");
     }
 
     boolean founded = HiLib.loadComponent();
 
     boolean founded2 = HiSpringLibManager.loadComponent();
 
     if ((!(founded)) && (!(founded2)) && (log.isWarnEnabled())) {
       log.warn("libmanager->loadComponent, not any lib Manager startup!");
     }
     return 0;
   }
 
   public static synchronized int loadComponent(String name)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("libmanager->loadComponent:[" + name + "]");
     }
 
     boolean founded = HiLib.loadComponent(name);
 
     if (founded) {
       return 1;
     }
 
     founded = HiSpringLibManager.loadComponent(name);
 
     if (!(founded)) {
       throw new HiException("215004", name);
     }
     return 2;
   }
 
   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx, int libType)
     throws HiException
   {
     if (libType == 1)
       return HiLib.invoke(name, args, ctx);
     if (libType == 2) {
       return HiSpringLibManager.invoke(name, args, ctx);
     }
 
     return HiLib.invoke(name, args, ctx);
   }
 
   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     if (HiLib.contains(name))
     {
       return HiLib.invoke(name, args, ctx);
     }
     if (HiSpringLibManager.contains(name))
     {
       return HiSpringLibManager.invoke(name, args, ctx);
     }
 
     throw new HiException("215006", name);
   }
 }