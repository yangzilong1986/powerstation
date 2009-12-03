/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.engine.HiITFEngineModel;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.exception.HiSysException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.message.HiXmlETF;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.Node;
/*    */ 
/*    */ public class HiXMLItem extends HiITFEngineModel
/*    */ {
/*    */   private String _name;
/*    */   private String _xpath;
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 32 */     return "XMLItem";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/*    */     try {
/* 37 */       HiMessage msg = ctx.getCurrentMsg();
/* 38 */       Logger log = HiLog.getLogger(msg);
/* 39 */       if (log.isInfoEnabled()) {
/* 40 */         log.info(sm.getString("HiItem.process00", HiEngineUtilities.getCurFlowStep(), this._name));
/*    */       }
/*    */ 
/* 44 */       doProcess(ctx);
/*    */     } catch (HiException e) {
/* 46 */       throw e;
/*    */     } catch (Throwable te) {
/* 48 */       throw new HiSysException("213135", this._name, te);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doProcess(HiMessageContext ctx) throws HiException {
/* 53 */     HiMessage msg = ctx.getCurrentMsg();
/* 54 */     String ect = msg.getHeadItem("ECT");
/* 55 */     if (StringUtils.equals(ect, "text/etf"))
/*    */     {
/* 57 */       doPackProcess(ctx);
/* 58 */     } else if (StringUtils.equals(ect, "text/plain"))
/*    */     {
/* 60 */       doUnpackProcess(ctx);
/*    */     }
/*    */     else throw new HiException("213120", ect);
/*    */   }
/*    */ 
/*    */   public void doUnpackProcess(HiMessageContext ctx) throws HiException
/*    */   {
/* 67 */     HiMessage msg = ctx.getCurrentMsg();
/* 68 */     HiXmlETF etf = (HiXmlETF)HiItemHelper.getCurXmlRoot(msg);
/* 69 */     Node node = etf.getNode().selectSingleNode(this._xpath);
/* 70 */     node.getText();
/*    */   }
/*    */ 
/*    */   public void doPackProcess(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ }