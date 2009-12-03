/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiWriteElement extends HiAbstractDoElement
/*     */ {
/*     */   public HiWriteElement()
/*     */     throws HiException
/*     */   {
/*  40 */     super.initAtt();
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  45 */     return "WriteElement";
/*     */   }
/*     */ 
/*     */   public int doElement(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*     */     label204: HiETF xmlNode;
/*  49 */     HiMessage mess = messContext.getCurrentMsg();
/*  50 */     Logger log = HiLog.getLogger(mess);
/*  51 */     if (log.isInfoEnabled()) {
/*  52 */       log.info(sm.getString("HiAbstractDoElement.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), this.name, (this.ETF_name != null) ? this.ETF_name : ""));
/*     */     }
/*     */ 
/*  55 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
/*  56 */     if (xmlContent == null)
/*     */     {
/*  58 */       throw new HiException("213197", this.name);
/*     */     }
/*     */ 
/*  61 */     HiETF etfBody = mess.getETFBody();
/*     */ 
/*  65 */     if (StringUtils.isNotBlank(this.ETF_name))
/*     */     {
/*  68 */       itemVal = getFitValue(mess, etfBody, log);
/*  69 */       if (itemVal != null) {
/*     */         break label204;
/*     */       }
/*  72 */       if (log.isInfoEnabled())
/*     */       {
/*  74 */         log.info("WriteElement:不存在指定ETF[" + this.ETF_name + "],necessary=no,本节点不构造");
/*     */       }
/*  76 */       return -2;
/*     */     }
/*     */ 
/*  82 */     String itemVal = getXmlValue(mess, etfBody, log);
/*  83 */     if (log.isDebugEnabled()) {
/*  84 */       log.debug("WriteElement: 构造XML节点[" + this.name + "]");
/*     */     }
/*     */ 
/*  94 */     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
/*     */ 
/*  97 */     if (this.xml_level == 1)
/*     */     {
/*  99 */       xmlParent = xmlContent;
/*     */     }
/* 101 */     else if (this.xml_level == 2)
/*     */     {
/* 103 */       HiETF xmlGrandParent = xmlParent.getParent();
/* 104 */       if (xmlGrandParent == null)
/*     */       {
/* 106 */         throw new HiException("213194", this.name);
/*     */       }
/* 108 */       xmlParent = xmlGrandParent;
/*     */     }
/*     */ 
/* 111 */     if (xmlParent == null)
/*     */     {
/* 113 */       xmlNode = xmlContent.addNodeBase(this.xml_name, itemVal, this.ns, this.ns_uri);
/*     */     }
/*     */     else
/*     */     {
/* 117 */       xmlNode = xmlParent.addNodeBase(this.xml_name, itemVal, this.ns, this.ns_uri);
/*     */     }
/*     */ 
/* 121 */     messContext.setBaseSource("XML_ROOT", xmlNode);
/*     */ 
/* 125 */     if (log.isInfoEnabled()) {
/* 126 */       log.info("添加XML节点:[" + this.name + "]=[" + itemVal + "]");
/*     */     }
/* 128 */     return 0;
/*     */   }
/*     */ 
/*     */   protected String getFitValue(HiMessage mess, HiETF etfBody, Logger log)
/*     */     throws HiException
/*     */   {
/* 143 */     HiETF itemNode = HiItemHelper.getEtfItemNode(mess, this.ETF_name);
/*     */ 
/* 146 */     if (itemNode == null)
/*     */     {
/* 148 */       if (this.value != null)
/*     */       {
/* 151 */         itemVal = this.value;
/* 152 */         if (log.isDebugEnabled()) {
/* 153 */           log.debug(sm.getString("HiItem.formatEtf3", this.ETF_name, this.value));
/*     */         }
/* 155 */         if (this.exp == null) {
/*     */           break label164;
/*     */         }
/* 158 */         HiItemHelper.addEtfItem(mess, this.ETF_name, itemVal); break label164:
/*     */       }
/*     */ 
/* 161 */       if (!(this.necessary))
/*     */       {
/* 164 */         return null;
/*     */       }
/*     */ 
/* 168 */       throw new HiException("213192", this.name, this.ETF_name);
/*     */     }
/*     */ 
/* 172 */     if (!(itemNode.isEndNode()))
/*     */     {
/* 174 */       throw new HiException("213193", this.name, this.ETF_name);
/*     */     }
/*     */ 
/* 179 */     String itemVal = itemNode.getValue();
/* 180 */     if (log.isDebugEnabled()) {
/* 181 */       log.debug(sm.getString("HiItem.formatEtf2", this.ETF_name, itemVal));
/*     */     }
/*     */ 
/* 184 */     if (this.exp != null)
/*     */     {
/* 186 */       label164: itemVal = HiItemHelper.execExpression(mess, this.exp);
/*     */     }
/*     */ 
/* 189 */     return itemVal;
/*     */   }
/*     */ 
/*     */   protected String getXmlValue(HiMessage mess, HiETF etfBody, Logger log)
/*     */     throws HiException
/*     */   {
/* 205 */     String itemVal = null;
/* 206 */     if (this.value != null)
/*     */     {
/* 209 */       itemVal = this.value;
/* 210 */       if (log.isDebugEnabled()) {
/* 211 */         log.debug(sm.getString("HiItem.formatEtf3", "", this.value));
/*     */       }
/*     */     }
/* 214 */     else if (this.exp != null)
/*     */     {
/* 217 */       itemVal = HiItemHelper.execExpression(mess, this.exp);
/*     */     }
/* 219 */     return itemVal;
/*     */   }
/*     */ 
/*     */   public void loadCheck()
/*     */     throws HiException
/*     */   {
/* 228 */     if (StringUtils.isBlank(this.name))
/*     */     {
/* 230 */       throw new HiException("213190", this.name);
/*     */     }
/*     */ 
/* 233 */     if ((this.exp == null) || (!(StringUtils.isBlank(this.ETF_name))))
/*     */       return;
/* 235 */     throw new HiException("213191", this.name);
/*     */   }
/*     */ }