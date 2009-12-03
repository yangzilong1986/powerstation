/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiDeamon01Handler
/*    */   implements IHandler
/*    */ {
/*    */   private int _code_begin;
/*    */   private int _code_end;
/*    */   private int _tmOut;
/*    */ 
/*    */   public HiDeamon01Handler()
/*    */   {
/* 15 */     this._code_begin = 0;
/* 16 */     this._code_end = 0;
/* 17 */     this._tmOut = 0; }
/*    */ 
/*    */   public void setTmOut(int tmOut) {
/* 20 */     this._tmOut = tmOut;
/*    */   }
/*    */ 
/*    */   public void setCode_begin(int code_begin) {
/* 24 */     this._code_begin = code_begin;
/*    */   }
/*    */ 
/*    */   public void setCode_end(int code_end) {
/* 28 */     this._code_end = code_end;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 32 */     HiMessage msg1 = ctx.getCurrentMsg();
/*    */ 
/* 34 */     if (this._tmOut != 0) {
/*    */       try {
/* 36 */         Thread.currentThread(); Thread.sleep(this._tmOut);
/*    */       } catch (InterruptedException e) {
/* 38 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 41 */     HiByteBuffer byteBuffer = new HiByteBuffer(300);
/* 42 */     byteBuffer.append("00000000NSC00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 ");
/* 43 */     msg1.setBody(byteBuffer);
/* 44 */     msg1.setHeadItem("SCH", "rp");
/* 45 */     ctx.setCurrentMsg(msg1);
/*    */   }
/*    */ }