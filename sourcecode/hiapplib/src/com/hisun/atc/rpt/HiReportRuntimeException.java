 package com.hisun.atc.rpt;
 
 public class HiReportRuntimeException extends RuntimeException
 {
   private final int errCode;
 
   public HiReportRuntimeException(Throwable cause)
   {
     super(cause);
     this.errCode = -1;
   }
 
   public HiReportRuntimeException(int errcode, String msg) {
     super(msg);
     this.errCode = errcode;
   }
 
   public int getErrCode() {
     return this.errCode;
   }
 }