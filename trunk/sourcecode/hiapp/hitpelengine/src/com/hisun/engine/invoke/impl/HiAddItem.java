 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.lang.reflect.Method;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 
 public class HiAddItem extends HiEngineModel
 {
   private static final HiStringManager sm = HiStringManager.getManager();
 
   private HiExpression exp = null;
   private String strName;
   private String strPro_dll;
   private String strPro_func;
   private String strValue;
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled()) {
       log.debug("HiAddItem:process(HiMessageContext) - start");
     }
 
     if (StringUtils.isNotEmpty(this.strValue))
     {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiAddItem.process00", HiEngineUtilities.getCurFlowStep(), this.strName, this.strValue));
       }
 
       HiItemHelper.addEtfItem(mess, this.strName, this.strValue);
     } else if (this.exp != null)
     {
       Object value = this.exp.getValue(messContext);
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiAddItem.process00", HiEngineUtilities.getCurFlowStep(), this.strName, value));
       }
       HiItemHelper.addEtfItem(mess, this.strName, value.toString());
     }
     else
     {
       try {
         Class cl = Class.forName(this.strPro_dll);
         Method method = cl.getDeclaredMethod(this.strPro_func, new Class[] { HiMessageContext.class, HashMap.class });
 
         HashMap map = new HashMap();
         map.put("name", this.strName);
         Object value = method.invoke(cl.newInstance(), new Object[] { messContext, map });
         if (log.isInfoEnabled()) {
           log.info(sm.getString("HiAddItem.process01", HiEngineUtilities.getCurFlowStep(), this.strPro_dll, this.strPro_func, this.strName, value));
         }
         HiItemHelper.addEtfItem(mess, this.strName, value.toString());
       } catch (Throwable t) {
         throw HiException.makeException(t);
       }
     }
 
     if (log.isDebugEnabled())
       log.debug("HiAddItem:process(HiMessageContext) - end");
   }
 
   public void setExpression(String strExpression)
   {
     this.exp = HiExpFactory.createExp(strExpression);
   }
 
   public void setName(String strName) {
     this.strName = strName;
   }
 
   public void setPro_dll(String strPro_dll) {
     this.strPro_dll = strPro_dll;
   }
 
   public void setPro_func(String strPro_func) {
     this.strPro_func = strPro_func;
   }
 
   public void setValue(String strValue) {
     this.strValue = strValue;
   }
 
   public String getNodeName() {
     return "AddItem";
   }
 }