 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.invoke.HiIStrategy;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.List;
 
 public class HiGetTxnCode extends HiEngineModel
 {
   public void beforeProcess(HiMessageContext messContext)
     throws HiException
   {
     HiIStrategy strategy = (HiIStrategy)messContext.getProperty("STRATEGY_KEY");
 
     if (strategy != null)
       strategy.beforeConverMess(messContext);
   }
 
   public String getNodeName()
   {
     return "GetTxnCode";
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     String strCode = "";
 
     List childs = getChilds();
 
     if (childs.size() == 1)
     {
       HiGetTxnCodeItem child = (HiGetTxnCodeItem)childs.get(0);
 
       child.process(messContext);
     }
     else
     {
       for (int i = 0; i < childs.size(); ++i)
       {
         HiGetTxnCodeItem child = (HiGetTxnCodeItem)childs.get(i);
 
         child.process(messContext);
 
         strCode = strCode + mess.getHeadItem("STC");
       }
 
       mess.setHeadItem("STC", strCode);
     }
 
     if (log.isDebugEnabled())
       log.debug(HiStringManager.getManager().getString("HiGetTxnCode.process", strCode));
   }
 }