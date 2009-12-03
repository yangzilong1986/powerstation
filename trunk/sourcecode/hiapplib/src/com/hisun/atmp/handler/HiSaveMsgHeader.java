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
/*    */ public class HiSaveMsgHeader
/*    */   implements IHandler
/*    */ {
/*    */   private ArrayList _keyNameList;
/*    */   private int _tmOut;
/*    */ 
/*    */   public HiSaveMsgHeader()
/*    */   {
/* 13 */     this._keyNameList = new ArrayList();
/* 14 */     this._tmOut = 30; }
/*    */ 
/*    */   public void setKeyName(String keyName) {
/* 17 */     this._keyNameList.add(keyName);
/*    */   }
/*    */ 
/*    */   public int getTmOut() {
/* 21 */     return this._tmOut;
/*    */   }
/*    */ 
/*    */   public void setTmOut(int tmOut) {
/* 25 */     this._tmOut = tmOut;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext) throws HiException {
/* 29 */     HiMessage mess = messContext.getCurrentMsg();
/* 30 */     HiETF etf = (HiETF)mess.getBody();
/* 31 */     StringBuffer value = new StringBuffer();
/* 32 */     for (int i = 0; i < this._keyNameList.size(); ++i) {
/* 33 */       value.append(etf.getChildValue((String)this._keyNameList.get(i)));
/*    */     }
/* 35 */     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
/* 36 */     if (mp == null)
/* 37 */       HiMessagePool.setMessagePool(messContext);
/* 38 */     mp = HiMessagePool.getMessagePool(messContext);
/* 39 */     messContext.setProperty("_MSG_TIME_OUT", new Integer(this._tmOut * 1000));
/* 40 */     mp.saveHeader(value.toString(), mess);
/*    */   }
/*    */ }