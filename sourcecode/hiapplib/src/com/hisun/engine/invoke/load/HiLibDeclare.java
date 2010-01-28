 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiLib;
 import com.hisun.message.HiContext;
 
 public class HiLibDeclare extends HiEngineModel
 {
   public String getNodeName()
   {
     return "LibDeclares";
   }
 
   public void setLibrary(String strName, String value) throws HiException
   {
     HiContext.getCurrentContext().setProperty("LIBDECLARE." + strName, value);
 
     HiLib.load(strName, value);
   }
 }