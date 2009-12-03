/*    */ package com.hisun.engine.invoke;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiByteStategy extends HiAbstractStrategy
/*    */ {
/*    */   public Object createBeforeMess(HiMessage mess, boolean isInnerMess)
/*    */     throws HiException
/*    */   {
/* 16 */     if (isInnerMess)
/*    */     {
/* 18 */       HiByteBuffer bb = new HiByteBuffer(1024);
/* 19 */       return bb;
/*    */     }
/*    */ 
/* 23 */     Object body = mess.getBody();
/*    */ 
/* 25 */     return body;
/*    */   }
/*    */ 
/*    */   public Object createAfterMess(HiMessage mess)
/*    */     throws HiException
/*    */   {
/* 32 */     Object obj = mess.getObjectHeadItem("PlainText");
/* 33 */     Logger log = HiLog.getLogger(mess);
/*    */ 
/* 42 */     if (obj instanceof HiByteBuffer)
/*    */     {
/* 44 */       return obj;
/*    */     }
/* 46 */     if (obj instanceof byte[])
/*    */     {
/* 48 */       return new HiByteBuffer((byte[])(byte[])obj);
/*    */     }
/*    */ 
/* 52 */     return obj;
/*    */   }
/*    */ }