/*     */ package com.hisun.ibs.comm;
/*     */ 
/*     */ import COM.ibm.eNetwork.cpic.CPICReturnCode;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ public class HiIBSComm
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*  40 */   private static HiStringManager _sm = HiStringManager.getManager();
/*     */   private String _cSideInfo;
/*     */   private int _iMaxConn;
/*     */   private int _iCurrConn;
/*     */   private int _iTimeOut;
/*     */   private String _cicsTxn;
/*     */   private ReentrantLock _mSumLock;
/*     */   private Logger _log;
/*     */ 
/*     */   public HiIBSComm()
/*     */   {
/*  48 */     this._iMaxConn = 100;
/*     */ 
/*  52 */     this._iCurrConn = 0;
/*     */ 
/*  57 */     this._iTimeOut = 10;
/*     */ 
/*  62 */     this._cicsTxn = null;
/*     */ 
/*  67 */     this._mSumLock = new ReentrantLock();
/*     */ 
/*  69 */     this._log = null; }
/*     */ 
/*     */   public String getSideInfo() {
/*  72 */     return this._cSideInfo;
/*     */   }
/*     */ 
/*     */   public void setSideInfo(String sideInfo) {
/*  76 */     this._cSideInfo = sideInfo;
/*     */   }
/*     */ 
/*     */   public int getMaxConn() {
/*  80 */     return this._iMaxConn;
/*     */   }
/*     */ 
/*     */   public void setMaxConn(int maxConn) {
/*  84 */     this._iMaxConn = maxConn;
/*     */   }
/*     */ 
/*     */   public int getTimeOut() {
/*  88 */     return this._iTimeOut;
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/*  92 */     this._iTimeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public String getCicsTxn() {
/*  96 */     return this._cicsTxn;
/*     */   }
/*     */ 
/*     */   public void setCicsTxn(String cicsTxn) {
/* 100 */     this._cicsTxn = cicsTxn;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/* 104 */     HiMessage msg = ctx.getCurrentMsg();
/* 105 */     this._mSumLock.lock();
/* 106 */     if (this._iCurrConn < this._iMaxConn) {
/* 107 */       this._iCurrConn += 1;
/* 108 */       this._mSumLock.unlock();
/*     */     } else {
/* 110 */       this._mSumLock.unlock();
/* 111 */       throw new HiException("231207");
/*     */     }
/* 113 */     if (this._log.isInfoEnabled()) {
/* 114 */       this._log.info(_sm.getString("ibs.currConn", msg.getRequestId(), String.valueOf(this._iCurrConn)));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 119 */       sendReceive(ctx);
/*     */     } finally {
/* 121 */       this._mSumLock.lock();
/* 122 */       this._iCurrConn -= 1;
/* 123 */       this._mSumLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sendReceive(HiMessageContext ctx) throws HiException {
/* 128 */     HiMessage msg = ctx.getCurrentMsg();
/* 129 */     HiByteBuffer sendData = (HiByteBuffer)msg.getBody();
/* 130 */     byte[] recvBuffer = new byte[16384];
/* 131 */     String tpName = null;
/* 132 */     if (this._cicsTxn != null) {
/* 133 */       if (msg.hasHeadItem(this._cicsTxn))
/* 134 */         tpName = msg.getHeadItem(this._cicsTxn);
/*     */       else
/* 136 */         tpName = this._cicsTxn;
/*     */     }
/*     */     else {
/* 139 */       tpName = sendData.substr(0, 4);
/*     */     }
/* 141 */     long l1 = 0L; long l2 = 0L;
/* 142 */     String txnCode = null;
/* 143 */     if (this._log.isInfoEnabled()) {
/* 144 */       this._log.info(_sm.getString("ibs.send", msg.getRequestId(), tpName, String.valueOf(sendData.length()), sendData));
/*     */ 
/* 146 */       txnCode = msg.getHeadItem("STC");
/* 147 */       l1 = System.currentTimeMillis();
/*     */     }
/* 149 */     HiCPICComm cpic = new HiCPICComm();
/* 150 */     int ret = 0;
/*     */     try {
/* 152 */       ret = cpic.appcComm(this._cSideInfo, tpName, sendData.getBytes(), recvBuffer);
/*     */     }
/*     */     catch (CPICReturnCode e) {
/* 155 */       switch (cpic.getFlag()) { case -1:
/* 157 */         throw new HiException("231207", "APPC", e);
/*     */       case -2:
/*     */       case -3: } }
/* 159 */     throw new HiException("231207", "APPC", e);
/*     */ 
/* 161 */     throw new HiException("231205", "APPC", e);
/*     */ 
/* 164 */     HiByteBuffer buffer = new HiByteBuffer(ret);
/* 165 */     buffer.append(recvBuffer, 0, ret);
/* 166 */     if (this._log.isInfoEnabled()) {
/* 167 */       l2 = System.currentTimeMillis();
/* 168 */       this._log.info(_sm.getString("ibs.recv", msg.getRequestId(), txnCode, String.valueOf(l2 - l1), String.valueOf(buffer.length()), buffer));
/*     */     }
/*     */ 
/* 171 */     msg.setHeadItem("SCH", "rp");
/* 172 */     msg.setBody(buffer);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 176 */     this._log = arg0.getLog();
/*     */   }
/*     */ }