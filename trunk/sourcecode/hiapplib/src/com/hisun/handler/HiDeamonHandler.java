/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiDeamonHandler
/*    */   implements IHandler
/*    */ {
/*    */   private int _code_begin;
/*    */   private int _code_end;
/*    */ 
/*    */   public HiDeamonHandler()
/*    */   {
/* 15 */     this._code_begin = 0;
/* 16 */     this._code_end = 0; }
/*    */ 
/*    */   public void setCode_begin(int code_begin) {
/* 19 */     this._code_begin = code_begin;
/*    */   }
/*    */ 
/*    */   public void setCode_end(int code_end) {
/* 23 */     this._code_end = code_end;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 27 */     HiMessage msg1 = ctx.getCurrentMsg();
/*    */ 
/* 29 */     HiByteBuffer byteBuffer = new HiByteBuffer(10);
/* 30 */     byteBuffer.append("000000");
/* 31 */     msg1.setBody(byteBuffer);
/* 32 */     msg1.setHeadItem("SCH", "rp");
/* 33 */     ctx.setCurrentMsg(msg1);
/*    */   }
/*    */ }