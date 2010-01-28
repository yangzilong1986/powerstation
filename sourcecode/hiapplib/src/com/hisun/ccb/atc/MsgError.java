 package com.hisun.ccb.atc;
 
 import com.hisun.util.HiStringUtils;
 
 public class MsgError extends MsgBase
 {
   private String errorFieldName;
 
   public MsgError(String appMmo, String msgCode, String msgInfo)
   {
     super(appMmo, msgCode, msgInfo);
   }
 
   public String getErrorFieldName()
   {
     return this.errorFieldName;
   }
 
   public void setErrorFieldName(String errorFieldName)
   {
     this.errorFieldName = errorFieldName;
   }
 
   public String getMsg()
   {
     StringBuilder sb = new StringBuilder();
     int length = 0;
     if (this.msgInfo != null)
     {
       length = this.msgInfo.getBytes().length; }
     int totalLength = 16 + length;
 
     String tmpstr = HiStringUtils.leftPad(totalLength, 4);
     sb.append(tmpstr);
     sb.append("SCD04");
     sb.append("N");
     sb.append(this.appMmo);
     sb.append(this.msgCode);
 
     tmpstr = HiStringUtils.leftPad(length, 4);
     sb.append(tmpstr);
     sb.append(this.msgInfo);
     return sb.toString();
   }
 }