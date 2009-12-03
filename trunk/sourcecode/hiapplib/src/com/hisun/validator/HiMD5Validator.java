/*    */ package com.hisun.validator;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import org.apache.commons.codec.digest.DigestUtils;
/*    */ 
/*    */ public class HiMD5Validator
/*    */   implements IHandler
/*    */ {
/*    */   public boolean validate(HiMessageContext ctx)
/*    */   {
/* 16 */     HiByteBuffer buffer = (HiByteBuffer)ctx.getCurrentMsg().getBody();
/* 17 */     int length = buffer.length();
/* 18 */     byte[] data1 = buffer.subbyte(length - 16, 16);
/* 19 */     byte[] data2 = DigestUtils.md5(buffer.subbyte(0, length - 16));
/* 20 */     int i = 0;
/* 21 */     if (data1.length != data2.length) {
/* 22 */       return false;
/*    */     }
/* 24 */     for (; i < data1.length; ++i) {
/* 25 */       if (data1[i] != data2[i]) {
/* 26 */         return false;
/*    */       }
/*    */     }
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ }