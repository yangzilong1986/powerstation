/*    */ package com.hisun.protocol.tcp.imp;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.protocol.tcp.HiAbstractALMServer;
/*    */ import com.hisun.protocol.tcp.HiMessageInOut;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiALMServer extends HiAbstractALMServer
/*    */ {
/*    */   private String _checkData;
/* 40 */   private HiMessageInOut messageInOut = new HiMessageInOut();
/*    */ 
/*    */   public HiALMServer() {
/* 43 */     setMsginout(this.messageInOut); }
/*    */ 
/*    */   public void setCheckData(String checkData) {
/* 46 */     this._checkData = checkData;
/*    */   }
/*    */ 
/*    */   public String getCheckData() {
/* 50 */     return this._checkData;
/*    */   }
/*    */ 
/*    */   public void setPreLenType(String preLenType) {
/* 54 */     this.messageInOut.setPreLenType(preLenType);
/*    */   }
/*    */ 
/*    */   public String getPreLenType() {
/* 58 */     return this.messageInOut.getPreLenType(); }
/*    */ 
/*    */   public void setPreLen(int preLen) {
/* 61 */     this.messageInOut.setPreLen(preLen);
/*    */   }
/*    */ 
/*    */   public int getPreLen() {
/* 65 */     return this.messageInOut.getPreLen(); }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException {
/* 68 */     HiCheckConnImpl checkConn = new HiCheckConnImpl();
/* 69 */     if (StringUtils.isEmpty(this._checkData)) {
/* 70 */       int len = getMsginout().getPreLen();
/* 71 */       checkConn.setCheckData(StringUtils.repeat("0", len));
/*    */     } else {
/* 73 */       checkConn.setCheckData(this._checkData);
/*    */     }
/* 75 */     setCheckConn(checkConn);
/* 76 */     super.serverInit(arg0);
/*    */   }
/*    */ }