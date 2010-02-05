 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.message.HiMessageContext;
 import java.util.List;
 import java.util.Vector;
 
 public class HiElseProcess extends HiEngineModel
 {
   private List conditions;
 
   public HiElseProcess()
   {
     this.conditions = new Vector();
   }
 
   public void addControl(HiIAction control)
   {
     this.conditions.add(control);
   }
 
   public String getNodeName()
   {
     return "Else";
   }
 
   public String toString()
   {
     String strNodeName = super.toString();
 
     return strNodeName;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     if ((getChilds() == null) || (getChilds().size() == 0)) {
       return;
     }
     for (int i = 0; i < getChilds().size(); ++i)
     {
       try
       {
         HiIAction child = (HiIAction)getChilds().get(i);
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