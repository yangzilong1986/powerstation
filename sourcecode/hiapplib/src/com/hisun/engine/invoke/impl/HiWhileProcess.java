 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiBreakException;
 import com.hisun.engine.exception.HiContinueException;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringManager;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiWhileProcess extends HiEngineModel
 {
   private HiExpression exp = null;
 
   private int MAX_TIMES = 10000;
 
   private String strCondition = "";
 
   public HiWhileProcess()
   {
     String tmp = HiICSProperty.getProperty("sys.max_loop_times");
     int num = NumberUtils.toInt(tmp);
     if (num > 0)
       this.MAX_TIMES = num;
   }
 
   public void setCondition(String strCondition)
   {
     this.strCondition = strCondition;
     this.exp = HiExpFactory.createExp(strCondition);
   }
 
   protected boolean isSuccess(HiMessage mess, HiMessageContext tranData, HiEngineModel rsEn, int degree)
     throws HiException
   {
     if (this.exp == null)
     {
       return true;
     }
     if ((rsEn != null) && (degree == 0)) {
       return true;
     }
 
     try
     {
       boolean b = this.exp.isReturnTrue(tranData);
 
       return b;
     }
     catch (HiException e)
     {
       e.addMsgStack("213326", this.strCondition);
       throw e;
     }
     catch (Exception e)
     {
       throw new HiSysException("213327", this.strCondition, e);
     }
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     int degree = 0;
 
     HiEngineModel rsEn = null;
     HiEngineStack stack = HiEngineStack.getCurrentStack(messContext);
     if (stack != null)
     {
       rsEn = (HiEngineModel)stack.pop();
     }
 
     while (isSuccess(mess, messContext, rsEn, degree))
     {
       if (Thread.currentThread().isInterrupted()) {
         label37: throw new HiException("241149", Thread.currentThread().getName());
       }
       HiEngineUtilities.timeoutCheck(mess);
 
       ++degree;
       if (degree > this.MAX_TIMES)
       {
         throw new HiException("213341", String.valueOf(this.MAX_TIMES));
       }
 
       if (log.isInfoEnabled())
       {
         log.info(HiStringManager.getManager().getString("HiWhileProcess.process2", String.valueOf(degree)));
       }
 
       List childs = getChilds();
       try
       {
         for (int i = 0; i < childs.size(); ++i)
         {
           HiIAction child = (HiIAction)childs.get(i);
 
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
             HiProcess.process(child, messContext);
           }
           catch (HiResponseException e)
           {
             HiEngineStack.getEngineStack(messContext).push(this);
             throw e;
           }
           catch (HiException e)
           {
             throw e;
           }
           catch (Exception e)
           {
             throw new HiSysException("213325", new String[] { HiEngineUtilities.getCurFlowStep(), String.valueOf(degree) }, e);
           }
 
         }
 
       }
       catch (HiBreakException e)
       {
         return;
       }
       catch (HiContinueException e)
       {
         break label37:
       }
     }
   }
 
   public String getNodeName()
   {
     return "While";
   }
 
   public String toString()
   {
     if (StringUtils.isNotEmpty(this.strCondition))
       return getNodeName() + ":condition[" + this.strCondition + "]";
     return super.toString();
   }
 }