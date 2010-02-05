 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.exception.HiGotoException;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessageContext;
 
 public class HiGoto extends HiEngineModel
 {
   private String strName;
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public String getName()
   {
     return this.strName;
   }
 
   public String getNodeName()
   {
     return "Goto";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.strName + "]";
   }
 
   public void beforeProcess(HiMessageContext messContext)
     throws HiException
   {
     HiGotoException ex = new HiGotoException();
     ex.seGototName(this.strName);
     throw ex;
   }
 }