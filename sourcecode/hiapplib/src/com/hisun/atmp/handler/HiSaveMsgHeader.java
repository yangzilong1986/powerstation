 package com.hisun.atmp.handler;
 
 import com.hisun.engine.HiMessagePool;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import java.util.ArrayList;
 
 public class HiSaveMsgHeader
   implements IHandler
 {
   private ArrayList _keyNameList;
   private int _tmOut;
 
   public HiSaveMsgHeader()
   {
     this._keyNameList = new ArrayList();
     this._tmOut = 30; }
 
   public void setKeyName(String keyName) {
     this._keyNameList.add(keyName);
   }
 
   public int getTmOut() {
     return this._tmOut;
   }
 
   public void setTmOut(int tmOut) {
     this._tmOut = tmOut;
   }
 
   public void process(HiMessageContext messContext) throws HiException {
     HiMessage mess = messContext.getCurrentMsg();
     HiETF etf = (HiETF)mess.getBody();
     StringBuffer value = new StringBuffer();
     for (int i = 0; i < this._keyNameList.size(); ++i) {
       value.append(etf.getChildValue((String)this._keyNameList.get(i)));
     }
     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
     if (mp == null)
       HiMessagePool.setMessagePool(messContext);
     mp = HiMessagePool.getMessagePool(messContext);
     messContext.setProperty("_MSG_TIME_OUT", new Integer(this._tmOut * 1000));
     mp.saveHeader(value.toString(), mess);
   }
 }