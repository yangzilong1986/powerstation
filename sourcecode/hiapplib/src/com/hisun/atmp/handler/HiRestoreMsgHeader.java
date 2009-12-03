/*    */ package com.hisun.atmp.handler;
/*    */ 
/*    */ import com.hisun.engine.HiMessagePool;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiRestoreMsgHeader
/*    */   implements IHandler
/*    */ {
/*    */   private ArrayList _keyNameList;
/*    */ 
/*    */   public HiRestoreMsgHeader()
/*    */   {
/* 19 */     this._keyNameList = new ArrayList(); }
/*    */ 
/*    */   public void process(HiMessageContext messContext) throws HiException { HiMessage mess = messContext.getCurrentMsg();
/* 22 */     HiETF etf = (HiETF)mess.getBody();
/* 23 */     StringBuffer value = new StringBuffer();
/* 24 */     for (int i = 0; i < this._keyNameList.size(); ++i) {
/* 25 */       value.append(etf.getChildValue((String)this._keyNameList.get(i)));
/*    */     }
/* 27 */     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
/*    */ 
/* 29 */     HiMessage msg1 = (HiMessage)mp.getHeader(value.toString());
/* 30 */     if (msg1 == null) {
/* 31 */       throw new HiException("310009");
/*    */     }
/* 33 */     mess.setRequestId(msg1.getRequestId());
/* 34 */     mess.setHeadItem("STC", msg1.getHeadItem("STC"));
/*    */   }
/*    */ 
/*    */   public void setKeyName(String keyName)
/*    */   {
/* 44 */     this._keyNameList.add(keyName);
/*    */   }
/*    */ }