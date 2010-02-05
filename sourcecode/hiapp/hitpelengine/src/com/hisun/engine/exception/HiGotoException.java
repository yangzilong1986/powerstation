 package com.hisun.engine.exception;
 
 import com.hisun.exception.HiException;
 
 public class HiGotoException extends HiException
 {
   private static final long serialVersionUID = 1L;
   private String strGotoName;
 
   public void seGototName(String strGotoName)
   {
     this.strGotoName = strGotoName;
   }
 
   public String getGotoName()
   {
     return this.strGotoName;
   }
 }