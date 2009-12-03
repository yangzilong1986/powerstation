/*    */ package com.hisun.cnaps.handler;
/*    */ 
/*    */ import com.hisun.cnaps.common.HiCnapsDataTypeHelper;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiCnapsPackerHandler
/*    */   implements IHandler
/*    */ {
/*    */   final Logger log;
/*    */ 
/*    */   public HiCnapsPackerHandler()
/*    */   {
/* 13 */     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext context) throws HiException {
/* 17 */     HiByteBuffer buffer = (HiByteBuffer)context.getCurrentMsg().getBody();
/* 18 */     StringBuffer sb = new StringBuffer();
/* 19 */     sb.append(buffer.toString());
/* 20 */     String len = String.valueOf(buffer.getBytes().length);
/* 21 */     String realLength = HiCnapsDataTypeHelper.lFullByte(len, len.length(), 6, '0');
/* 22 */     sb.replace(3, 9, realLength);
/* 23 */     buffer = new HiByteBuffer(sb.toString().getBytes());
/* 24 */     context.getCurrentMsg().setBody(buffer);
/*    */   }
/*    */ }