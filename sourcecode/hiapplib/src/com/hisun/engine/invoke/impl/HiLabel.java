 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 
 public class HiLabel extends HiEngineModel
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
     return "Lable";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.strName + "]";
   }
 }