 package com.hisun.engine.pojo;
 
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public abstract class HiPojoEngineModel
   implements HiIAction
 {
   protected HiContext context;
   protected static final HiStringManager sm = HiStringManager.getManager();
 
   public HiPojoEngineModel()
   {
     this.context = null;
   }
 
   public void loadAfter()
     throws HiException
   {
   }
 
   public void afterProcess(HiMessageContext messContext)
     throws HiException
   {
     if (this.context != null)
       messContext.popParent();
   }
 
   public void beforeProcess(HiMessageContext messContext)
     throws HiException
   {
     if (this.context != null)
       messContext.pushParent(this.context);
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
   }
 
   public void popOwnerContext()
   {
     HiContext.popCurrentContext();
   }
 
   public String toString()
   {
     return getNodeName();
   }
 }