 package com.hisun.atmp.handler;
 
 import com.hisun.engine.HiMessagePool;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import java.util.ArrayList;
 
 public class HiRestoreMsgHeader
   implements IHandler
 {
   private ArrayList _keyNameList;
 
   public HiRestoreMsgHeader()
   {
     this._keyNameList = new ArrayList(); }
 
   public void process(HiMessageContext messContext) throws HiException { HiMessage mess = messContext.getCurrentMsg();
     HiETF etf = (HiETF)mess.getBody();
     StringBuffer value = new StringBuffer();
     for (int i = 0; i < this._keyNameList.size(); ++i) {
       value.append(etf.getChildValue((String)this._keyNameList.get(i)));
     }
     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
 
     HiMessage msg1 = (HiMessage)mp.getHeader(value.toString());
     if (msg1 == null) {
       throw new HiException("310009");
     }
     mess.setRequestId(msg1.getRequestId());
     mess.setHeadItem("STC", msg1.getHeadItem("STC"));
   }
 
   public void setKeyName(String keyName)
   {
     this._keyNameList.add(keyName);
   }
 }