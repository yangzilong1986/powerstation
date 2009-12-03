/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiWriteAttr extends HiAbstractDoElement
/*     */ {
/*     */   public HiWriteAttr()
/*     */     throws HiException
/*     */   {
/*  37 */     super.initAtt();
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  43 */     return "WriteAttr";
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  47 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  48 */     if (log.isInfoEnabled()) {
/*  49 */       log.info(sm.getString("HiWriteAttr.process00", HiEngineUtilities.getCurFlowStep(), this.name, (this.ETF_name != null) ? this.ETF_name : ""));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  54 */       doElement(ctx);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*  58 */       throw e;
/*     */     }
/*     */     catch (Throwable te)
/*     */     {
/*  62 */       throw new HiSysException("213207", this.name, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int doElement(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*  69 */     HiMessage mess = messContext.getCurrentMsg();
/*     */ 
/*  71 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
/*  72 */     if (xmlContent == null)
/*     */     {
/*  74 */       throw new HiException("213197", this.name);
/*     */     }
/*     */ 
/*  85 */     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
/*  86 */     if (xmlParent == null)
/*     */     {
/*  92 */       xmlParent = xmlContent;
/*     */     }
/*  94 */     String curXmlLevel = xmlParent.getName();
/*     */ 
/*  97 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  99 */     HiETF etfBody = mess.getETFBody();
/*     */ 
/* 103 */     if (StringUtils.isNotBlank(this.ETF_name))
/*     */     {
/* 106 */       itemVal = getFitValue(mess, etfBody, log);
/* 107 */       if (itemVal != null) {
/*     */         break label146;
/*     */       }
/* 110 */       if (log.isInfoEnabled())
/*     */       {
/* 112 */         log.info("WriteAttr:不存在指定ETF[" + this.ETF_name + "],necessary=no,本属性不构造");
/*     */       }
/* 114 */       return -2;
/*     */     }
/*     */ 
/* 120 */     String itemVal = getXmlValue(mess, etfBody, log);
/*     */ 
/* 127 */     label146: xmlParent.setAttrValue(this.name, itemVal, this.ns, this.ns_uri);
/*     */ 
/* 130 */     if (log.isInfoEnabled()) {
/* 131 */       log.info("添加属性:[" + this.name + "]=[" + itemVal + "]");
/*     */     }
/* 133 */     return -2;
/*     */   }
/*     */ 
/*     */   protected String getFitValue(HiMessage mess, HiETF etfBody, Logger log)
/*     */     throws HiException
/*     */   {
/* 146 */     HiETF itemNode = HiItemHelper.getEtfItemNode(mess, this.ETF_name);
/*     */ 
/* 149 */     if (itemNode == null)
/*     */     {
/* 151 */       if (this.value != null)
/*     */       {
/* 154 */         itemVal = this.value;
/* 155 */         if (log.isInfoEnabled()) {
/* 156 */           log.info(sm.getString("HiItem.formatEtf3", this.ETF_name, this.value));
/*     */         }
/* 158 */         if (this.exp == null) {
/*     */           break label164;
/*     */         }
/* 161 */         HiItemHelper.addEtfItem(mess, this.ETF_name, itemVal); break label164:
/*     */       }
/*     */ 
/* 164 */       if (!(this.necessary))
/*     */       {
/* 166 */         return null;
/*     */       }
/*     */ 
/* 170 */       throw new HiException("213199", this.name, this.ETF_name);
/*     */     }
/*     */ 
/* 173 */     if (!(itemNode.isEndNode()))
/*     */     {
/* 175 */       throw new HiException("213200", this.name, this.ETF_name);
/*     */     }
/*     */ 
/* 179 */     String itemVal = itemNode.getValue();
/* 180 */     if (log.isInfoEnabled()) {
/* 181 */       log.info(sm.getString("HiItem.formatEtf2", this.ETF_name, itemVal));
/*     */     }
/*     */ 
/* 185 */     if (this.exp != null)
/*     */     {
/* 187 */       label164: itemVal = HiItemHelper.execExpression(mess, this.exp);
/*     */     }
/* 189 */     return itemVal;
/*     */   }
/*     */ 
/*     */   protected String getXmlValue(HiMessage mess, HiETF etfBody, Logger log)
/*     */     throws HiException
/*     */   {
/* 204 */     String itemVal = null;
/* 205 */     if (this.value != null)
/*     */     {
/* 208 */       itemVal = this.value;
/* 209 */       if (log.isDebugEnabled()) {
/* 210 */         log.debug(sm.getString("HiItem.formatEtf3", "", this.value));
/*     */       }
/*     */     }
/* 213 */     else if (this.exp != null)
/*     */     {
/* 216 */       itemVal = HiItemHelper.execExpression(mess, this.exp);
/*     */     }
/* 218 */     return itemVal;
/*     */   }
/*     */ 
/*     */   public void loadCheck()
/*     */     throws HiException
/*     */   {
/* 227 */     if (StringUtils.isBlank(this.name))
/*     */     {
/* 229 */       throw new HiException("213195", this.name);
/*     */     }
/*     */ 
/* 232 */     if ((this.exp == null) || (!(StringUtils.isBlank(this.ETF_name))))
/*     */       return;
/* 234 */     throw new HiException("213195", this.name);
/*     */   }
/*     */ }