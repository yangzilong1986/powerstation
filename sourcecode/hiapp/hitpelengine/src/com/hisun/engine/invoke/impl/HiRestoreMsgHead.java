 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.HiMessagePool;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiRestoreMsgHead extends HiEngineModel
 {
   private boolean ignore = false;
   private String strKey_name;
 
   public HiRestoreMsgHead()
   {
     HiContext ctx = HiContext.getCurrentContext();
     HiMessagePool.setMessagePool(ctx.getServerContext());
   }
 
   public String getNodeName()
   {
     return "RestoreMsgHead ";
   }
 
   public String toString()
   {
     return super.toString() + ":key_name[" + this.strKey_name + "]";
   }
 
   public void setKey_name(String strKey_name)
   {
     this.strKey_name = strKey_name;
   }
 
   public void setIgnore(boolean ignore)
   {
     this.ignore = ignore;
   }
 
   public boolean getIgnore()
   {
     return this.ignore;
   }
 
   public void process(HiMessageContext messContext) throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiRestoreMsgHead.process00", HiEngineUtilities.getCurFlowStep(), this.strKey_name));
     }
     HiETF etf = (HiETF)mess.getBody();
     String value = etf.getGrandChildValue(HiItemHelper.getCurEtfLevel(mess) + this.strKey_name);
 
     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
     if ((mp.restoreHeader(value, mess) != null) || 
       (this.ignore)) return;
     throw new HiException("231428");
   }
 }