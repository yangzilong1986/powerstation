/*    */ package com.hisun.protocol.tcp.imp;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.protocol.tcp.HiAbstractALMClient;
/*    */ import com.hisun.protocol.tcp.HiMessageInOut;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiALMClient extends HiAbstractALMClient
/*    */ {
/*    */   private String _checkData;
/* 25 */   private HiMessageInOut messageInOut = new HiMessageInOut();
/*    */ 
/*    */   public HiALMClient() {
/* 28 */     setMsginout(this.messageInOut); }
/*    */ 
/*    */   public void setCheckData(String checkData) {
/* 31 */     this._checkData = checkData;
/*    */   }
/*    */ 
/*    */   public String getCheckData() {
/* 35 */     return this._checkData;
/*    */   }
/*    */ 
/*    */   public void setPreLenType(String preLenType) {
/* 39 */     this.messageInOut.setPreLenType(preLenType);
/*    */   }
/*    */ 
/*    */   public String getPreLenType() {
/* 43 */     return this.messageInOut.getPreLenType(); }
/*    */ 
/*    */   public void setPreLen(int preLen) {
/* 46 */     this.messageInOut.setPreLen(preLen);
/*    */   }
/*    */ 
/*    */   public int getPreLen() {
/* 50 */     return this.messageInOut.getPreLen(); }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException {
/* 53 */     HiCheckConnImpl checkConn = new HiCheckConnImpl();
/* 54 */     if (StringUtils.isEmpty(this._checkData)) {
/* 55 */       int len = getMsginout().getPreLen();
/* 56 */       checkConn.setCheckData(StringUtils.repeat("0", len));
/*    */     } else {
/* 58 */       checkConn.setCheckData(this._checkData);
/*    */     }
/* 60 */     setCheckConn(checkConn);
/* 61 */     super.serverInit(arg0);
/*    */   }
/*    */ }