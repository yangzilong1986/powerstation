 package com.hisun.hilib;
 
 import com.hisun.bean.HiBeanManager;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.component.HiIComponent;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import org.springframework.context.ApplicationContext;
 
 public class HiSpringLibManager
 {
   private static Logger log = HiLog.getLogger("lib.trc");
 
   public static synchronized boolean loadComponent(String name)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("springlibmanager->loadComponent:[" + name + "]");
     }
 
     ApplicationContext springCtx = getSpringCtx();
     if (springCtx == null)
     {
       if (log.isDebugEnabled()) {
         log.debug("springlibmanager->loadComponent, can't startup");
       }
       return false;
     }
 
     if (!(springCtx.containsBean(name)))
     {
       if (log.isDebugEnabled()) {
         log.debug("springlibmanager->loadComponent: can't find [" + name + "]");
       }
       return false;
     }
     return true;
   }
 
   public static synchronized boolean loadComponent()
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("springlibmanager->loadComponent");
     }
 
     ApplicationContext springCtx = getSpringCtx();
     if (springCtx == null)
     {
       if (log.isDebugEnabled()) {
         log.debug("springlibmanager->loadComponent, applicationcontext is null");
       }
       return false;
     }
 
     return true;
   }
 
   public static boolean contains(String name)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("springlibmanager->contains:[" + name + "]");
     }
 
     ApplicationContext springCtx = getSpringCtx();
     if (springCtx == null)
     {
       return false;
     }
     return springCtx.containsBean(name);
   }
 
   public static ApplicationContext getSpringCtx()
   {
     ApplicationContext springCtx = HiBeanManager.getServerSpringCtx();
     if (springCtx == null)
     {
       String config = HiICSProperty.getProperty("spring.config");
       if (config == null)
       {
         log.warn("springlibmanager->Read File sys.properties: spring.config is null!");
         return null;
       }
 
       springCtx = HiBeanManager.loadSpringCtxAndBindServer(config);
     }
     return springCtx;
   }
 
   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     ApplicationContext springCtx = HiBeanManager.getServerSpringCtx(ctx);
     return invoke(springCtx, name, args, ctx);
   }
 
   public static Object invoke(ApplicationContext springCtx, String name, HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     if (springCtx != null)
     {
       HiIComponent cmp = (HiIComponent)springCtx.getBean(name);
       if (cmp == null)
       {
         throw new HiException("215006", name);
       }
       return Integer.valueOf(cmp.execute(args, ctx));
     }
 
     throw new HiException("215006", name);
   }
 }