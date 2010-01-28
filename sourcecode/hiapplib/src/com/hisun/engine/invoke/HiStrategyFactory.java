 package com.hisun.engine.invoke;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 import com.hisun.util.HiResource;
 
 public class HiStrategyFactory
 {
   public static final String STRATEGY_KEY = "STRATEGY_KEY";
 
   public static HiIStrategy getStrategyInstance(String strClassName)
     throws HiException
   {
     HiIStrategy ins = null;
     HiContext context = HiContext.getCurrentContext();
     if (context.containsProperty("STRATEGY_KEY")) {
       ins = (HiIStrategy)context.getProperty("STRATEGY_KEY");
     }
     else {
       try
       {
         ins = (HiIStrategy)HiResource.loadClass(strClassName).newInstance();
 
         context.setProperty("STRATEGY_KEY", ins);
       }
       catch (ClassNotFoundException e)
       {
         throw new HiException(e);
       }
       catch (InstantiationException e)
       {
         throw new HiException(e);
       }
       catch (IllegalAccessException e)
       {
         throw new HiException(e);
       }
 
     }
 
     return ins;
   }
 }