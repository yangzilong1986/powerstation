 package com.hisun.atc.common;
 
 import com.hisun.message.HiContext;
 import java.util.ArrayList;
 
 public class HiAuthTable
 {
   private static final String KEY = "__AUTHTABLE";
   private ArrayList list;
 
   public HiAuthTable()
   {
     this.list = new ArrayList(); }
 
   public static HiAuthTable getAuthTable(HiContext ctx) {
     return ((HiAuthTable)ctx.getProperty("__AUTHTABLE"));
   }
 
   public static void setAuthTable(HiContext ctx, HiAuthTable authTable) {
     ctx.setProperty("__AUTHTABLE", authTable);
   }
 }