/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiByteCutHandler
/*    */   implements IHandler
/*    */ {
/*    */   private int length;
/*    */   private String key;
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 13 */     HiByteBuffer buffer = (HiByteBuffer)ctx.getCurrentMsg().getBody();
/* 14 */     byte[] head = buffer.subbyte(0, this.length);
/* 15 */     byte[] data = buffer.subbyte(this.length, buffer.length() - this.length);
/*    */ 
/* 17 */     ctx.getCurrentMsg().setHeadItem(this.key, head);
/* 18 */     ctx.getCurrentMsg().setBody(new HiByteBuffer(data));
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 23 */     return this.length; }
/*    */ 
/*    */   public void setLength(int length) {
/* 26 */     this.length = length; }
/*    */ 
/*    */   public String getKey() {
/* 29 */     return this.key; }
/*    */ 
/*    */   public void setKey(String key) {
/* 32 */     this.key = key;
/*    */   }
/*    */ }