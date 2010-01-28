 package com.hisun.ccb.atc;
 
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.List;
 
 public class MsgNormal extends MsgBase
 {
   private List<String> values = new ArrayList();
 
   public MsgNormal(String appMmo, String msgCode, String msgInfo)
   {
     super(appMmo, msgCode, msgInfo);
   }
 
   public void addValue(String value)
   {
     this.values.add(value);
   }
 
   public Collection<String> getValues()
   {
     return this.values;
   }
 
   public String getMsg()
   {
     return null;
   }
 }