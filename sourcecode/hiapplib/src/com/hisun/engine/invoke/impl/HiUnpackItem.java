 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 
 public class HiUnpackItem extends HiAbstractPackItem
 {
   public void process(HiMessageContext messContext)
     throws HiException
   {
     try
     {
       HiMessage mess = messContext.getCurrentMsg();
       Logger log = HiLog.getLogger(mess);
 
       HiETF etfBody = (HiETF)mess.getBody();
       String item = etfBody.getGrandChildValue(HiItemHelper.getCurEtfLevel(mess) + getName());
 
       if (item == null)
       {
         throw new HiException("213142", HiItemHelper.getCurEtfLevel(mess) + this.name);
       }
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiUnpackItem.process00", HiEngineUtilities.getCurFlowStep(), this.name, item));
       }
 
       HiByteBuffer plainBuf = (HiByteBuffer)mess.getObjectHeadItem("PlainText");
 
       int plainOffset = HiItemHelper.getPlainOffset(mess);
       String msgType = mess.getHeadItem("ECT");
 
       initMsgState(mess, "text/plain", new HiByteBuffer(item.getBytes()));
 
       super.process(messContext);
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiUnpackItem.process01", HiEngineUtilities.getCurFlowStep(), this.name));
       }
       restoreMsgState(mess, plainBuf, plainOffset, msgType);
     }
     catch (Throwable te)
     {
       throw HiException.makeException("213143", this.name, te);
     }
   }
 
   public String getNodeName()
   {
     return "UnPackItem";
   }
 }