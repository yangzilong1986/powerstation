 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessageContext;
 
 public class HiProcess
 {
   public static void process(HiIAction action, HiMessageContext messContext)
     throws HiException
   {
     action.beforeProcess(messContext);
     action.process(messContext);
     action.afterProcess(messContext);
   }
 }