/*     */ package com.hisun.ibs.comm;
/*     */ 
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
/*     */ public class HiIBSNativeComm
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   private static HiStringManager _sm;
/*     */   private String _cSideInfo;
/*     */   private int _iMaxConn;
/*     */   private int _iCurrConn;
/*     */   private int _iTimeOut;
/*     */   private String _cicsTxn;
/*     */   private ReentrantLock _mSumLock;
/*     */   private Logger _log;
/*     */ 
/*     */   public HiIBSNativeComm()
/*     */   {
/*  52 */     this._iMaxConn = 100;
/*     */ 
/*  56 */     this._iCurrConn = 0;
/*     */ 
/*  61 */     this._iTimeOut = 10;
/*     */ 
/*  66 */     this._cicsTxn = null;
/*     */ 
/*  71 */     this._mSumLock = new ReentrantLock();
/*     */ 
/*  73 */     this._log = null; } 
/*     */   public native int ibscommSendReceive(String paramString1, String paramString2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
/*     */ 
/*     */   public String getSideInfo() { return this._cSideInfo;
/*     */   }
/*     */ 
/*     */   public void setSideInfo(String sideInfo) {
/*  80 */     this._cSideInfo = sideInfo;
/*     */   }
/*     */ 
/*     */   public int getMaxConn() {
/*  84 */     return this._iMaxConn;
/*     */   }
/*     */ 
/*     */   public void setMaxConn(int maxConn) {
/*  88 */     this._iMaxConn = maxConn;
/*     */   }
/*     */ 
/*     */   public int getTimeOut() {
/*  92 */     return this._iTimeOut;
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/*  96 */     this._iTimeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public String getCicsTxn() {
/* 100 */     return this._cicsTxn;
/*     */   }
/*     */ 
/*     */   public void setCicsTxn(String cicsTxn) {
/* 104 */     this._cicsTxn = cicsTxn;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/* 108 */     HiMessage msg = ctx.getCurrentMsg();
/* 109 */     this._mSumLock.lock();
/* 110 */     if (this._iCurrConn < this._iMaxConn) {
/* 111 */       this._iCurrConn += 1;
/* 112 */       this._mSumLock.unlock();
/*     */     } else {
/* 114 */       this._mSumLock.unlock();
/* 115 */       throw new HiException("231207");
/*     */     }
/* 117 */     if (this._log.isInfoEnabled()) {
/* 118 */       this._log.info(_sm.getString("ibs.currConn", msg.getRequestId(), String.valueOf(this._iCurrConn)));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 123 */       sendReceive(ctx);
/*     */     } finally {
/* 125 */       this._mSumLock.lock();
/* 126 */       this._iCurrConn -= 1;
/* 127 */       this._mSumLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sendReceive(HiMessageContext ctx) throws HiException {
/* 132 */     HiMessage msg = ctx.getCurrentMsg();
/* 133 */     HiByteBuffer sendData = (HiByteBuffer)msg.getBody();
/* 134 */     byte[] recvBuffer = new byte[16384];
/* 135 */     String tpName = null;
/* 136 */     if (this._cicsTxn != null) {
/* 137 */       if (msg.hasHeadItem(this._cicsTxn))
/* 138 */         tpName = msg.getHeadItem(this._cicsTxn);
/*     */       else
/* 140 */         tpName = this._cicsTxn;
/*     */     }
/*     */     else {
/* 143 */       tpName = sendData.substr(0, 4);
/*     */     }
/* 145 */     long l1 = 0L; long l2 = 0L;
/* 146 */     String txnCode = null;
/* 147 */     if (this._log.isInfoEnabled()) {
/* 148 */       this._log.info(_sm.getString("ibs.send", msg.getRequestId(), tpName, String.valueOf(sendData.length()), sendData));
/*     */ 
/* 150 */       txnCode = msg.getHeadItem("STC");
/* 151 */       l1 = System.currentTimeMillis();
/*     */     }
/* 153 */     int ret = ibscommSendReceive(this._cSideInfo, tpName, sendData.getBytes(), recvBuffer);
/*     */ 
/* 155 */     switch (ret)
/*     */     {
/*     */     case -1:
/* 157 */       throw new HiException("231207");
/*     */     case -2:
/* 159 */       throw new HiException("231205");
/*     */     case -3:
/* 161 */       throw new HiException("231206");
/*     */     }
/* 163 */     HiByteBuffer buffer = new HiByteBuffer(ret);
/* 164 */     buffer.append(recvBuffer, 0, ret);
/* 165 */     if (this._log.isInfoEnabled()) {
/* 166 */       l2 = System.currentTimeMillis();
/* 167 */       this._log.info(_sm.getString("ibs.recv", msg.getRequestId(), txnCode, String.valueOf(l2 - l1), String.valueOf(buffer.length()), buffer));
/*     */     }
/*     */ 
/* 170 */     msg.setHeadItem("SCH", "rp");
/* 171 */     msg.setBody(buffer);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 175 */     this._log = arg0.getLog();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  31 */     HiNativeHelper.loadLibrary("ibscomm");
/*     */ 
/*  44 */     _sm = HiStringManager.getManager();
/*     */   }
/*     */ }