 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class HiIfProcess extends HiEngineModel
 {
   private static final HiStringManager sm = HiStringManager.getManager();
 
   private ArrayList controlNodes = new ArrayList();
   private String strCondition;
   private HiExpression exp = null;
 
   public void addControlNodes(HiIEngineModel control)
   {
     this.controlNodes.add(control);
   }
 
   public String getNodeName()
   {
     return "If";
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiIfProcess.process00", HiEngineUtilities.getCurFlowStep(), this.strCondition));
     }
 
     boolean isSuccess = true;
 
     HiEngineModel rsEn = null;
     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
     if (stack != null)
     {
       rsEn = (HiEngineModel)stack.pop();
     }
 
     if (rsEn == null)
     {
       isSuccess = isSuccess(mess, ctx);
     }
     else if (getChilds().indexOf(rsEn) != -1) {
       isSuccess = false;
     }
 
     if (isSuccess)
     {
       List childs = getChilds();
       if ((childs == null) || (childs.size() == 0)) {
         return;
       }
       for (int i = 0; i < childs.size(); ++i)
       {
         HiIAction child = (HiIAction)childs.get(i);
 
         if (rsEn != null)
         {
           if (child != rsEn)
             continue;
           rsEn = null;
           if (stack.size() == 0) {
             continue;
           }
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
     else
     {
       for (int i = 0; i < this.controlNodes.size(); ++i)
       {
         HiIAction child = (HiIAction)this.controlNodes.get(i);
 
         if ((child instanceof HiIfProcess) || (child instanceof HiElseIfProcess))
         {
           HiIfProcess pro = (HiIfProcess)child;
           if (pro.isSuccess(mess, ctx))
           {
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
         else
         {
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
   }
 
   public void setCondition(String strCondition)
   {
     this.strCondition = strCondition;
     this.exp = HiExpFactory.createIfExp(strCondition);
   }
 
   protected boolean isSuccess(HiMessage mess, HiMessageContext tranData)
     throws HiException
   {
     try
     {
       Logger log = HiLog.getLogger(mess);
 
       boolean b = this.exp.isReturnTrue(tranData);
 
       if (log.isInfoEnabled())
       {
         log.info(HiStringManager.getManager().getString("HiIfProcess.isSuccess", this.strCondition, String.valueOf(b)));
       }
 
       return b;
     }
     catch (Throwable e)
     {
       throw HiException.makeException("213330", HiStringManager.getManager().getString("HiIfProcess.isSuccess1", this.strCondition), e);
     }
   }
 
   public void loadAfter()
     throws HiException
   {
     if (StringUtils.isBlank(this.strCondition))
       throw new HiException("if condition empty");
   }
 }