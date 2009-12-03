/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiByteAddHandler
/*    */   implements IHandler
/*    */ {
/*    */   private String key;
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 12 */     HiByteBuffer buffer = (HiByteBuffer)ctx.getCurrentMsg().getBody();
/*    */ 
/* 14 */     byte[] head = null;
/* 15 */     Object obj = ctx.getCurrentMsg().getObjectHeadItem(this.key);
/* 16 */     if (obj instanceof String)
/* 17 */       head = ((String)obj).getBytes();
/*    */     else {
/* 19 */       head = (byte[])(byte[])obj;
/*    */     }
/* 21 */     byte[] data = buffer.getBytes();
/*    */ 
/* 23 */     HiByteBuffer buf = new HiByteBuffer(head);
/* 24 */     buf.append(data);
/*    */ 
/* 26 */     ctx.getCurrentMsg().setBody(buf);
/*    */   }
/*    */ 
/*    */   public String getKey()
/*    */   {
/* 31 */     return this.key; }
/*    */ 
/*    */   public void setKey(String key) {
/* 34 */     this.key = key;
/*    */   }
/*    */ }