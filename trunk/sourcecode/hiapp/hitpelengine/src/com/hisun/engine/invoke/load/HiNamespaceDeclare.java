 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 
 public class HiNamespaceDeclare extends HiEngineModel
 {
   public String getNodeName()
   {
     return "NamespaceDeclare";
   }
 
   public void setItem(String strName, String value) throws HiException {
     HiContext.getCurrentContext().setProperty("NSDECLARE." + strName, value);
   }
 }