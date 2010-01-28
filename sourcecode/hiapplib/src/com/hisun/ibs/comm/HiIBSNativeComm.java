 package com.hisun.ibs.comm;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
 
 public class HiIBSNativeComm
   implements IHandler, IServerInitListener
 {
   private static HiStringManager _sm;
   private String _cSideInfo;
   private int _iMaxConn;
   private int _iCurrConn;
   private int _iTimeOut;
   private String _cicsTxn;
   private ReentrantLock _mSumLock;
   private Logger _log;
 
   public HiIBSNativeComm()
   {
     this._iMaxConn = 100;
 
     this._iCurrConn = 0;
 
     this._iTimeOut = 10;
 
     this._cicsTxn = null;
 
     this._mSumLock = new ReentrantLock();
 
     this._log = null; } 
   public native int ibscommSendReceive(String paramString1, String paramString2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
 
   public String getSideInfo() { return this._cSideInfo;
   }
 
   public void setSideInfo(String sideInfo) {
     this._cSideInfo = sideInfo;
   }
 
   public int getMaxConn() {
     return this._iMaxConn;
   }
 
   public void setMaxConn(int maxConn) {
     this._iMaxConn = maxConn;
   }
 
   public int getTimeOut() {
     return this._iTimeOut;
   }
 
   public void setTimeOut(int timeOut) {
     this._iTimeOut = timeOut;
   }
 
   public String getCicsTxn() {
     return this._cicsTxn;
   }
 
   public void setCicsTxn(String cicsTxn) {
     this._cicsTxn = cicsTxn;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     this._mSumLock.lock();
     if (this._iCurrConn < this._iMaxConn) {
       this._iCurrConn += 1;
       this._mSumLock.unlock();
     } else {
       this._mSumLock.unlock();
       throw new HiException("231207");
     }
     if (this._log.isInfoEnabled()) {
       this._log.info(_sm.getString("ibs.currConn", msg.getRequestId(), String.valueOf(this._iCurrConn)));
     }
 
     try
     {
       sendReceive(ctx);
     } finally {
       this._mSumLock.lock();
       this._iCurrConn -= 1;
       this._mSumLock.unlock();
     }
   }
 
   private void sendReceive(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer sendData = (HiByteBuffer)msg.getBody();
     byte[] recvBuffer = new byte[16384];
     String tpName = null;
     if (this._cicsTxn != null) {
       if (msg.hasHeadItem(this._cicsTxn))
         tpName = msg.getHeadItem(this._cicsTxn);
       else
         tpName = this._cicsTxn;
     }
     else {
       tpName = sendData.substr(0, 4);
     }
     long l1 = 0L; long l2 = 0L;
     String txnCode = null;
     if (this._log.isInfoEnabled()) {
       this._log.info(_sm.getString("ibs.send", msg.getRequestId(), tpName, String.valueOf(sendData.length()), sendData));
 
       txnCode = msg.getHeadItem("STC");
       l1 = System.currentTimeMillis();
     }
     int ret = ibscommSendReceive(this._cSideInfo, tpName, sendData.getBytes(), recvBuffer);
 
     switch (ret)
     {
     case -1:
       throw new HiException("231207");
     case -2:
       throw new HiException("231205");
     case -3:
       throw new HiException("231206");
     }
     HiByteBuffer buffer = new HiByteBuffer(ret);
     buffer.append(recvBuffer, 0, ret);
     if (this._log.isInfoEnabled()) {
       l2 = System.currentTimeMillis();
       this._log.info(_sm.getString("ibs.recv", msg.getRequestId(), txnCode, String.valueOf(l2 - l1), String.valueOf(buffer.length()), buffer));
     }
 
     msg.setHeadItem("SCH", "rp");
     msg.setBody(buffer);
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     this._log = arg0.getLog();
   }
 
   static
   {
     HiNativeHelper.loadLibrary("ibscomm");
 
     _sm = HiStringManager.getManager();
   }
 }