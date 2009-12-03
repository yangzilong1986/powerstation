/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiHeadAdd extends HiEngineModel
/*    */ {
/*    */   private String strHead_name;
/*    */   private String strName;
/*    */   private String strValue;
/*    */ 
/*    */   public void setHead_name(String strHead_name)
/*    */   {
/* 24 */     this.strHead_name = strHead_name;
/*    */   }
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 29 */     this.strName = strName;
/*    */   }
/*    */ 
/*    */   public void setValue(String strValue)
/*    */   {
/* 34 */     this.strValue = strValue;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 40 */     return "AddHead";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 46 */     return super.toString() + ":name[" + this.strName + "]value[" + this.strValue + "]";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 53 */     HiMessage mess = messContext.getCurrentMsg();
/* 54 */     Logger log = HiLog.getLogger(mess);
/* 55 */     if (StringUtils.isNotBlank(this.strName))
/*    */     {
/* 57 */       String val = HiItemHelper.getEtfItem(mess, this.strName);
/* 58 */       if (StringUtils.isNotEmpty(val))
/*    */       {
/* 60 */         this.strValue = val;
/*    */       }
/*    */     }
/*    */ 
/* 64 */     mess.setHeadItem(this.strHead_name, this.strValue);
/* 65 */     if (log.isInfoEnabled())
/* 66 */       log.info(sm.getString("HiHeadAdd.process00", HiEngineUtilities.getCurFlowStep(), this.strHead_name, this.strValue));
/*    */   }
/*    */ }