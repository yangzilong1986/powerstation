 package com.hisun.engine;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.invoke.impl.HiTransactionCTLImpl;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import java.util.Stack;
 
 public class HiEngineStack extends Stack
   implements Runnable
 {
   private static final String KEY = "ENGINE_STACK";
   private HiMessageContext ctx;
 
   public static HiEngineStack getEngineStack(HiMessageContext ctx)
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     Object obj = ctx.getProperty("ENGINE_STACK");
     if (obj == null)
     {
       if (log.isInfoEnabled())
       {
         log.info("HiEngineStack is start");
       }
       HiEngineStack esk = new HiEngineStack();
       ctx.setProperty("ENGINE_STACK", esk);
       return esk;
     }
 
     return ((HiEngineStack)obj);
   }
 
   public void setMessContext(HiMessageContext ctx)
   {
     this.ctx = ctx;
   }
 
   public void run()
   {
     this.ctx.setProperty("ENGINE_STACK", null);
     HiMessageContext.setCurrentMessageContext(this.ctx);
     Logger log = HiLog.getLogger(this.ctx.getCurrentMsg());
     if (log.isDebugEnabled())
     {
       for (int i = 0; i < size(); ++i)
       {
         log.debug("stack:" + get(i).getClass());
       }
 
     }
 
     HiEngineModel lastEn = (HiEngineModel)pop();
     this.ctx.setProperty("NoResponseObj", this);
     try {
       if (lastEn instanceof HiTransactionCTLImpl)
         ((HiTransactionCTLImpl)lastEn).doProcess(this.ctx, this);
     }
     catch (Throwable e) {
       log.warn(e);
     } finally {
       if (this.ctx.getDataBaseUtil() != null)
         this.ctx.getDataBaseUtil().closeAll();
     }
   }
 
   public static HiEngineModel getCurrentEngineModel(HiMessageContext ctx)
   {
     HiEngineStack stack = (HiEngineStack)ctx.getProperty("NoResponseObj");
 
     HiEngineModel en = (HiEngineModel)stack.pop();
     return en;
   }
 
   public static HiEngineStack getCurrentStack(HiMessageContext ctx) {
     HiEngineStack stack = (HiEngineStack)ctx.getProperty("NoResponseObj");
 
     if ((stack != null) && (stack.isEmpty()))
       return null;
     return stack;
   }
 }