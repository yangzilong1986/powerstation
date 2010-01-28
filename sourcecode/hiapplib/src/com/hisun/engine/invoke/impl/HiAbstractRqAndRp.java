 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.HiITFEngineModel;
 import com.hisun.engine.exception.HiReturnException;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.engine.invoke.HiIStrategy;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.List;
 
 public abstract class HiAbstractRqAndRp extends HiITFEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private HiContext context = HiContext.getCurrentContext();
 
   public HiAbstractRqAndRp()
   {
     super.setItemAttribute(HiContext.getCurrentContext());
   }
 
   public void beforeProcess(HiMessageContext messContext)
     throws HiException
   {
     HiIStrategy strategy = (HiIStrategy)messContext.getProperty("STRATEGY_KEY");
 
     if (strategy != null)
       strategy.beforeConverMess(messContext);
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     if (super.getChilds() == null) {
       return;
     }
     try
     {
       for (int i = 0; i < super.getChilds().size(); ++i)
       {
         HiIAction child = (HiIAction)super.getChilds().get(i);
 
         HiEngineUtilities.setCurFlowStep(i);
         HiProcess.process(child, ctx);
       }
     }
     catch (HiReturnException e)
     {
     }
   }
 
   public void afterProcess(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("HiAbstractRqAndRp:afterProcess(HiMessageContext) - start");
     }
 
     HiIStrategy strategy = (HiIStrategy)messContext.getProperty("STRATEGY_KEY");
 
     if (strategy != null) {
       strategy.afterConverMess(messContext);
     }
     if (!(log.isDebugEnabled()))
       return;
     log.debug("HiAbstractRqAndRp:afterProcess(HiMessageContext) - end" + mess.getBody().getClass());
   }
 }