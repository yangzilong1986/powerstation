 package com.hisun.mng.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mng.HiMonitor;
 import com.hisun.pubinterface.IHandler;
 
 public class HiMonPreProc
   implements IHandler, IServerDestroyListener
 {
   private String _code;
 
   public HiMonPreProc()
   {
     this._code = null; }
 
   public void setCode(String code) {
     this._code = code;
   }
 
   public void process(HiMessageContext arg0) throws HiException {
     HiMessage msg = arg0.getCurrentMsg();
     HiETF root = msg.getETFBody();
     String txnCod = root.getChildValue("TXN_CD");
     root.setChildValue("TxnCod", txnCod);
     msg.setHeadItem("STC", this._code);
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException {
     HiMonitor.closeAllMonitor(HiMonitor.getMonMap());
   }
 }