/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiHeadGet extends HiEngineModel
/*    */ {
/*    */   private String strHead_name;
/*    */   private String strName;
/*    */ 
/*    */   public void setHead_name(String strHead_name)
/*    */   {
/* 23 */     this.strHead_name = strHead_name;
/*    */   }
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 28 */     this.strName = strName;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 34 */     return "GetHead";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 40 */     return super.toString() + ":name[" + this.strName + "] head_name[" + this.strHead_name + "]";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 46 */     HiMessage mess = messContext.getCurrentMsg();
/* 47 */     String strValue = mess.getHeadItem(this.strHead_name);
/* 48 */     if (StringUtils.isEmpty(strValue)) {
/* 49 */       throw new HiException("213328", this.strHead_name);
/*    */     }
/* 51 */     HiETF etf = (HiETF)mess.getBody();
/* 52 */     etf.setGrandChildNode(this.strName, strValue);
/* 53 */     Logger log = HiLog.getLogger(mess);
/* 54 */     if (log.isInfoEnabled())
/* 55 */       log.info(sm.getString("HiHeadGet.process00", HiEngineUtilities.getCurFlowStep(), this.strHead_name, strValue));
/*    */   }
/*    */ }