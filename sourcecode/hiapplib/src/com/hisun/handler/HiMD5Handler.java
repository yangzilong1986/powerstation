/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import org.apache.commons.codec.digest.DigestUtils;
/*    */ 
/*    */ public class HiMD5Handler
/*    */   implements IHandler
/*    */ {
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 18 */     HiByteBuffer buffer = (HiByteBuffer)ctx.getCurrentMsg().getBody();
/*    */ 
/* 20 */     byte[] data = DigestUtils.md5(buffer.getBytes());
/* 21 */     buffer.append(data);
/* 22 */     ctx.getCurrentMsg().setBody(buffer);
/*    */   }
/*    */ }