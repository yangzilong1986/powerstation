 package com.hisun.engine.invoke;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public abstract class HiAbstractStrategy
   implements HiIStrategy
 {
   public void beforeConverMess(HiMessageContext messContext)
     throws HiException
   {
     Object converBody;
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("beforeConverMess(HiMessageContext) - start[" + super.getClass() + "]");
     }
 
     if (HiEngineUtilities.isInnerMessage(mess))
     {
       converBody = createBeforeMess(mess, true);
 
       mess.setHeadItem("PlainText", converBody);
     }
     else
     {
       if (mess.getBody() instanceof HiETF) {
         return;
       }
       converBody = createBeforeMess(mess, false);
       mess.setHeadItem("PlainText", converBody);
 
       HiETF etf = HiETFFactory.createETF();
       mess.setBody(etf);
     }
     if (!(log.isDebugEnabled()))
       return;
     log.debug("beforeConverMess(HiMessageContext) - end");
   }
 
   public void afterConverMess(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("afterConverMess(HiMessageContext) - start[" + super.getClass() + "]");
     }
 
     if (HiEngineUtilities.isInnerMessage(mess))
     {
       Object body = createAfterMess(mess);
       mess.setBody(body);
       mess.setHeadItem("ECT", "text/plain");
     }
     else
     {
       mess.setHeadItem("ECT", "text/etf");
     }
 
     mess.delHeadItem("PlainText");
     mess.delHeadItem("PlainOffset");
   }
 
   public abstract Object createAfterMess(HiMessage paramHiMessage)
     throws HiException;
 
   public abstract Object createBeforeMess(HiMessage paramHiMessage, boolean paramBoolean)
     throws HiException;
 }