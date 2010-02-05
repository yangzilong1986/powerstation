 package com.hisun.engine.exception;
 
 import com.hisun.exception.HiException;
 
 public class HiErrorException extends HiException
 {
   private static final long serialVersionUID = 1L;
 
   public HiErrorException(HiException e)
   {
     clone(e);
   }
 }