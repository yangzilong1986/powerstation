 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.message.HiContext;
 
 public class HiDynSentence extends HiEngineModel
 {
   private String strName;
   private String strSentence;
   private String strFields;
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public void setSentence(String strSentence)
   {
     this.strSentence = strSentence;
     HiContext.getCurrentContext().setProperty("SENTENCE." + this.strName.toUpperCase(), strSentence);
   }
 
   public void setFields(String strFields)
   {
     this.strFields = strFields;
 
     HiContext.getCurrentContext().setProperty("FIELDS." + this.strName.toUpperCase(), strFields);
   }
 
   public String getNodeName()
   {
     return "DynSentence";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.strName + "],sentence[" + this.strSentence + "],fields[" + this.strFields + "]";
   }
 }