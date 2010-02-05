 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 
 public class HiPackItem extends HiAbstractPackItem
 {
   public String getNodeName()
   {
     return "PackItem";
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     try
     {
       HiMessage msg = ctx.getCurrentMsg();
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiPackItem.process00", HiEngineUtilities.getCurFlowStep(), this.name));
       }
 
       HiByteBuffer plainBuf = (HiByteBuffer)msg.getObjectHeadItem("PlainText");
 
       int plainOffset = HiItemHelper.getPlainOffset(msg);
       String msgType = msg.getHeadItem("ECT");
 
       initMsgState(msg, "text/etf", new HiByteBuffer(128));
 
       super.process(ctx);
 
       HiByteBuffer packValue = (HiByteBuffer)msg.getObjectHeadItem("PlainText");
       String packValStr = packValue.toString();
       HiItemHelper.addEtfItem(msg, getName(), packValStr);
       if (log.isInfoEnabled())
         log.info(sm.getString("HiPackItem.process01", HiEngineUtilities.getCurFlowStep(), this.name, packValStr));
       restoreMsgState(msg, plainBuf, plainOffset, msgType);
     }
     catch (Throwable te)
     {
       throw HiException.makeException("213141", this.name, te);
     }
   }
 }