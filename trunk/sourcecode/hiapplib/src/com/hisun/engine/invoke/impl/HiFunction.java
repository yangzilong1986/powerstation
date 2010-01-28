 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiFunction extends HiTransactionCTLImpl
 {
   private String _strName;
   private String[] _inParams;
   private String[] _outParams;
 
   public HiFunction()
   {
     this.isFunc = true;
   }
 
   public void setName(String strName)
   {
     this._strName = strName;
     this.context.setId("FUNC." + strName);
   }
 
   public String getName()
   {
     return this._strName;
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF root = msg.getETFBody();
     if (log.isInfo2Enabled()) {
       log.info2(this.sm.getString("HiFunction.process00", HiEngineUtilities.getCurFlowStep(), this._strName));
     }
 
     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
     super.doProcess(ctx, stack);
   }
 
   public void beforeProcess(HiMessageContext ctx) throws HiException {
     if (this.context != null)
       ctx.pushParent(this.context);
   }
 
   public void afterProcess(HiMessageContext ctx) throws HiException
   {
     if (this.context != null)
       ctx.popParent();
   }
 
   public String toString() {
     return "Function:[" + this._strName + "]";
   }
 
   public String[] getInParams() {
     return this._inParams;
   }
 
   public void setInput(String input) {
     this._inParams = StringUtils.split(input, " |");
   }
 
   public String[] getOutParams() {
     return this._outParams;
   }
 
   public void setOutput(String output) {
     this._outParams = StringUtils.split(output, " |");
   }
 }