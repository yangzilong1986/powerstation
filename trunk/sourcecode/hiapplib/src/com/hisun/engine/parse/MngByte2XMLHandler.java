/*    */ package com.hisun.engine.parse;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class MngByte2XMLHandler
/*    */ {
/*    */   public static final String MNGTYPE = "MNG_TYPE";
/*    */ 
/*    */   public HiMessage process(HiMessage msg)
/*    */     throws HiException
/*    */   {
/* 18 */     byte[] data = (byte[])(byte[])msg.getBody();
/* 19 */     String sdata = new String(data);
/* 20 */     String mngtype = StringUtils.substringBefore(sdata, "<");
/*    */ 
/* 22 */     msg.setHeadItem("MNG_TYPE", mngtype);
/*    */ 
/* 25 */     msg.setHeadItem("ECT", "text/xml");
/*    */ 
/* 27 */     sdata = StringUtils.substringAfter(sdata, mngtype);
/*    */ 
/* 29 */     msg.setBody(sdata.getBytes());
/*    */ 
/* 33 */     return msg;
/*    */   }
/*    */ }