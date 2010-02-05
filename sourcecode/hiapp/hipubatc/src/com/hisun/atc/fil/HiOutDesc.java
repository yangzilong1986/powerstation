 package com.hisun.atc.fil;
 
 import com.hisun.engine.HiITFEngineModel;
 
 public class HiOutDesc extends HiITFEngineModel
 {
   private String _name;
 
   public void setName(String name)
   {
     this._name = name;
   }
 
   public String getNodeName() {
     return "Out";
   }
 }