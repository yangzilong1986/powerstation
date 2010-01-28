 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.List;
 
 public class HiSwitchCase extends HiEngineModel
 {
   private String strValue;
 
   public void setValue(String strValue)
   {
     this.strValue = strValue;
   }
 
   public String getNodeName()
   {
     return "Case";
   }
 
   public String toString()
   {
     return super.toString() + ":value[" + this.strValue + "]";
   }
 
   public String getValue()
   {
     return this.strValue;
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiEngineModel.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), toString()));
     }
 
     if (getChilds() == null)
     {
       return;
     }
 
     HiEngineModel rsEn = null;
     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
     if (stack != null)
     {
       rsEn = (HiEngineModel)stack.pop();
     }
     for (int i = 0; i < getChilds().size(); ++i)
     {
       HiIAction child = (HiIAction)getChilds().get(i);
       if (rsEn != null)
       {
         if (child != rsEn)
           continue;
         rsEn = null;
         if (stack.size() == 0)
           continue;
       }
       HiEngineUtilities.setCurFlowStep(i);
       try
       {
         HiProcess.process(child, ctx);
       }
       catch (HiResponseException e)
       {
         HiEngineStack.getEngineStack(ctx).push(this);
         throw e;
       }
     }
   }
 }