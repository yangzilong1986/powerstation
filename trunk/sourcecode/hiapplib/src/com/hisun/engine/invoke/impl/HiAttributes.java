/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.w3c.dom.NamedNodeMap;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class HiAttributes extends HiEngineModel
/*    */ {
/* 16 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 23 */     return "Attributes";
/*    */   }
/*    */ 
/*    */   public void setProperty(Object value)
/*    */   {
/* 32 */     if (this.logger.isDebugEnabled()) {
/* 33 */       this.logger.debug("setPropertys(Object) - start");
/*    */     }
/*    */ 
/* 36 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(HiContext.getCurrentContext());
/*    */ 
/* 38 */     Node node = (Node)value;
/* 39 */     NamedNodeMap att = node.getAttributes();
/* 40 */     String name = null;
/* 41 */     for (int i = 0; i < att.getLength(); ++i) {
/* 42 */       Node n = att.item(i);
/* 43 */       String strKey = n.getNodeName();
/* 44 */       if (StringUtils.equalsIgnoreCase(strKey, "name")) {
/* 45 */         name = n.getNodeValue();
/*    */       }
/*    */       else
/*    */       {
/* 49 */         if (StringUtils.equalsIgnoreCase(strKey, "value")) {
/* 50 */           strKey = name;
/*    */         }
/*    */ 
/* 53 */         String strValue = n.getNodeValue();
/* 54 */         attr.put(strKey, strValue);
/*    */       }
/*    */     }
/* 57 */     if (this.logger.isDebugEnabled()) {
/* 58 */       this.logger.debug("setPropertys(Object) - " + attr);
/* 59 */       this.logger.debug("setPropertys(Object) - end");
/*    */     }
/*    */   }
/*    */ }