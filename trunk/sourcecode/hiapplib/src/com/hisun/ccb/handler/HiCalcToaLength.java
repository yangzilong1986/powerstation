/*    */ package com.hisun.ccb.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import com.hisun.util.HiStringUtils;
/*    */ 
/*    */ public class HiCalcToaLength
/*    */   implements IHandler
/*    */ {
/*    */   private int _offset;
/*    */   private int _length;
/*    */ 
/*    */   public HiCalcToaLength()
/*    */   {
/* 15 */     this._offset = 283;
/* 16 */     this._length = 6; }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 19 */     HiMessage msg = ctx.getCurrentMsg();
/* 20 */     byte[] data = ((HiByteBuffer)msg.getBody()).getBytes();
/*    */ 
/* 22 */     int fillLength = data.length - (this._offset + this._length);
/*    */ 
/* 24 */     if (fillLength < 0);
/* 29 */     String tmpstr = HiStringUtils.leftPad(fillLength, this._length);
/*    */ 
/* 31 */     System.arraycopy(tmpstr.getBytes(), 0, data, this._offset, this._length);
/*    */ 
/* 33 */     msg.setBody(new HiByteBuffer(data));
/*    */   }
/*    */ 
/*    */   public void setLength(int length) {
/* 37 */     this._length = length;
/*    */   }
/*    */ 
/*    */   public int getLength() {
/* 41 */     return this._length;
/*    */   }
/*    */ 
/*    */   public void setOffset(int offset) {
/* 45 */     this._offset = offset;
/*    */   }
/*    */ 
/*    */   public int getOffset() {
/* 49 */     return this._offset;
/*    */   }
/*    */ }