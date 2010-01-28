 package com.hisun.engine.invoke.impl;
 
 import com.hisun.message.HiContext;
 
 public class HiErrorProcess extends HiAbstractTransaction
 {
   private String strName;
 
   public void setName(String strName)
   {
     this.strName = strName;
     this.context.setProperty("ERROR." + strName, this);
   }
 
   public String getNodeName()
   {
     return "Error";
   }
 }