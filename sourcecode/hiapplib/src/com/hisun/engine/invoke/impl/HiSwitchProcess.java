 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiBreakException;
 import com.hisun.engine.exception.HiReturnException;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class HiSwitchProcess extends HiEngineModel
 {
   private HiExpression exp;
   private String strExpression;
 
   public HiSwitchProcess()
   {
     this.exp = null;
   }
 
   public void setExpression(String strExpression)
   {
     this.strExpression = strExpression;
     this.exp = HiExpFactory.createExp(strExpression);
   }
 
   public String getNodeName()
   {
     return "Switch";
   }
 
   public String toString()
   {
     return super.toString() + ":expression[" + this.strExpression + "]";
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     String strValue = this.exp.getValue(ctx);
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiSwitchProcess.process00", HiEngineUtilities.getCurFlowStep(), this.strExpression + "[" + strValue + "]"));
     }
 
     HiEngineModel rsEn = null;
     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
     if (stack != null)
     {
       rsEn = (HiEngineModel)stack.pop();
     }
     List childs = getChilds();
     boolean isJudge = true;
     for (int i = 0; i < childs.size(); ++i)
     {
       HiIAction child = (HiIAction)childs.get(i);
       try
       {
         if (rsEn != null)
         {
           if (child == rsEn)
           {
             rsEn = null;
             if (stack.size() == 0) {
               break label238:
             }
           }
         }
         if ((isJudge) && (child instanceof HiSwitchCase))
         {
           if (StringUtils.equals(((HiSwitchCase)child).getValue(), strValue))
           {
             isJudge = false;
           }
         }
 
         HiEngineUtilities.setCurFlowStep(i);
         label238: HiProcess.process(child, ctx);
       }
       catch (HiResponseException e)
       {
         HiEngineStack.getEngineStack(ctx).push(this);
         throw e;
       }
       catch (HiBreakException e)
       {
         return;
       }
       catch (HiReturnException e)
       {
         throw e;
       }
     }
   }
 }