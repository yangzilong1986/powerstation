/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiXmlETF;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.Node;
/*     */ 
/*     */ public class HiReadElement extends HiAbstractDoElement
/*     */ {
/*     */   public String getNodeName()
/*     */   {
/*  45 */     return "ReadElement";
/*     */   }
/*     */ 
/*     */   public HiReadElement() throws HiException {
/*  49 */     super.initAtt();
/*     */   }
/*     */ 
/*     */   public int doElement(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*     */     HiETF xmlNode;
/*  53 */     HiMessage mess = messContext.getCurrentMsg();
/*  54 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
/*  55 */     if (xmlContent == null)
/*     */     {
/*  57 */       throw new HiException("213174", this.name);
/*     */     }
/*  59 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  67 */     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
/*     */ 
/*  70 */     if (this.xml_level == 1)
/*     */     {
/*  72 */       xmlParent = xmlContent;
/*     */     }
/*  74 */     else if (this.xml_level == 2)
/*     */     {
/*  76 */       HiETF xmlGrandParent = xmlParent.getParent();
/*  77 */       if (xmlGrandParent == null)
/*     */       {
/*  79 */         throw new HiException("213175", this.name);
/*     */       }
/*     */ 
/*  82 */       xmlParent = xmlGrandParent;
/*     */     }
/*     */ 
/*  85 */     if (xmlParent == null)
/*     */     {
/*  87 */       xmlNode = xmlContent.getGrandChildNodeBase(this.xml_name, this.ns, this.ns_uri);
/*     */     }
/*     */     else
/*     */     {
/*  91 */       xmlNode = xmlParent.getGrandChildNodeBase(this.xml_name, this.ns, this.ns_uri);
/*     */     }
/*     */ 
/*  94 */     if (this.xml_level == 3)
/*     */     {
/*  96 */       Element element = ((HiXmlETF)xmlParent).getNode();
/*  97 */       Node currNode = ((HiXmlETF)xmlParent).getNode().selectSingleNode(this.xml_name);
/*  98 */       if (currNode != null) {
/*  99 */         xmlNode = new HiXmlETF((Element)(Element)currNode);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     if (xmlNode == null)
/*     */     {
/* 108 */       if (!(this.necessary))
/*     */       {
/* 110 */         if (log.isInfoEnabled())
/*     */         {
/* 112 */           log.info("ReadElement:从XML报文定位不到指定节点名[" + this.name + "],necessary=no,本节点及子节点不解析");
/*     */         }
/* 114 */         return -2;
/*     */       }
/*     */ 
/* 117 */       throw new HiException("213176", this.name);
/*     */     }
/*     */ 
/* 120 */     if (StringUtils.isBlank(this.ETF_name))
/*     */     {
/* 123 */       if (log.isDebugEnabled()) {
/* 124 */         log.debug("ReadElement:从XML报文定位节点名[" + this.name + "]");
/*     */       }
/* 126 */       messContext.setBaseSource("XML_ROOT", xmlNode);
/* 127 */       return 1;
/*     */     }
/* 129 */     if (!(xmlNode.isEndNode()))
/*     */     {
/* 131 */       throw new HiException("213177", this.name);
/*     */     }
/*     */ 
/* 134 */     String xmlVal = xmlNode.getValue();
/* 135 */     if (log.isDebugEnabled()) {
/* 136 */       log.debug("ReadElement: 从XML报文读取节点名[" + this.name + "] 节点值[" + xmlVal + "]");
/*     */     }
/*     */ 
/* 139 */     HiETF etfBody = mess.getETFBody();
/*     */ 
/* 141 */     etfBody.setGrandChildNode(HiItemHelper.getCurEtfLevel(mess) + this.ETF_name, xmlVal);
/*     */ 
/* 143 */     if (log.isDebugEnabled()) {
/* 144 */       log.debug("ReadElement: 更新到ETF 域名[" + this.ETF_name + "] 值[" + xmlVal + "]");
/*     */     }
/*     */ 
/* 147 */     if (log.isInfoEnabled()) {
/* 148 */       log.info(sm.getString("HiAbstractDoElement.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), this.name, this.ETF_name, xmlVal));
/*     */     }
/*     */ 
/* 152 */     messContext.setBaseSource("XML_ROOT", xmlNode);
/*     */ 
/* 154 */     if (this.exp != null)
/*     */     {
/*     */       try
/*     */       {
/* 158 */         HiItemHelper.execExpression(mess, this.exp, this.ETF_name);
/*     */       }
/*     */       catch (Throwable te)
/*     */       {
/* 162 */         throw HiException.makeException("213183", this.name, this.exp.getExp(), te);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 168 */     return 0;
/*     */   }
/*     */ 
/*     */   public void loadCheck()
/*     */     throws HiException
/*     */   {
/* 178 */     if (!(StringUtils.isBlank(this.name)))
/*     */       return;
/* 180 */     throw new HiException("213173", this.name);
/*     */   }
/*     */ }