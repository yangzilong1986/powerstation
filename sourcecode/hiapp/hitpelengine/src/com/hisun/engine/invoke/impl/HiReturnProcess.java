 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.exception.HiReturnException;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessageContext;
 
 public class HiReturnProcess extends HiEngineModel
 {
   public void process(HiMessageContext messContext)
     throws HiException
   {
     throw new HiReturnException();
   }
 
   public String getNodeName()
   {
     return "Return";
   }
 }