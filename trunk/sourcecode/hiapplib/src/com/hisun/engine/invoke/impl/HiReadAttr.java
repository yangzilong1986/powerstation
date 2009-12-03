/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiReadAttr extends HiAbstractDoElement
/*     */ {
/*     */   public HiReadAttr()
/*     */     throws HiException
/*     */   {
/*  37 */     super.initAtt();
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  43 */     return "ReadAttr";
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext) throws HiException
/*     */   {
/*  48 */     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
/*  49 */     if (log.isInfoEnabled()) {
/*  50 */       log.info(sm.getString("HiReadAttr.process00", HiEngineUtilities.getCurFlowStep(), this.name, this.ETF_name));
/*     */     }
/*     */     try
/*     */     {
/*  54 */       doElement(messContext);
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
/*     */   public int doElement(HiMessageContext messContext) throws HiException
/*     */   {
/*  68 */     HiMessage mess = messContext.getCurrentMsg();
/*  69 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
/*  70 */     if (xmlContent == null)
/*     */     {
/*  72 */       throw new HiException("213179", this.name);
/*     */     }
/*  74 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  85 */     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
/*  86 */     if (xmlParent == null)
/*     */     {
/*  88 */       throw new HiException("213180", this.name);
/*     */     }
/*     */ 
/*  91 */     String curXmlLevel = xmlParent.getName();
/*     */ 
/*  94 */     String attrVal = xmlParent.getAttrValue(this.name, this.ns, this.ns_uri);
/*     */ 
/*  96 */     if (attrVal == null)
/*     */     {
/*  99 */       if (!(this.necessary))
/*     */       {
/* 101 */         if (log.isInfoEnabled())
/*     */         {
/* 103 */           log.info("ReadAttr:从XML报文定位不到指定属性[" + this.name + "],necessary=no,本属性值不添加到ETF");
/*     */         }
/* 105 */         return -2;
/*     */       }
/*     */ 
/* 108 */       throw new HiException("213181", curXmlLevel, this.name);
/*     */     }
/*     */ 
/* 111 */     if (log.isInfoEnabled())
/* 112 */       log.info("ReadAttr: 从XML报文取得该节点[" + curXmlLevel + "] 属性名[" + this.name + "] 属性值[" + attrVal + "]");
/* 113 */     if (StringUtils.isBlank(this.ETF_name))
/*     */     {
/* 115 */       if (log.isInfoEnabled())
/* 116 */         log.info("ReadAttr: 没有配置ETF_name");
/* 117 */       return -2;
/*     */     }
/*     */ 
/* 120 */     HiETF etfBody = mess.getETFBody();
/*     */ 
/* 122 */     etfBody.setGrandChildNode(HiItemHelper.getCurEtfLevel(mess) + this.ETF_name, attrVal);
/* 123 */     if (log.isInfoEnabled()) {
/* 124 */       log.info("ReadAttr: 更新到ETF 域名[" + this.ETF_name + "] 值[" + attrVal + "]");
/*     */     }
/*     */ 
/* 127 */     if (this.exp != null)
/*     */     {
/*     */       try
/*     */       {
/* 131 */         HiItemHelper.execExpression(mess, this.exp, this.ETF_name);
/*     */       }
/*     */       catch (Throwable te)
/*     */       {
/* 135 */         throw HiException.makeException("213182", this.name, this.exp.getExp(), te);
/*     */       }
/*     */     }
/*     */ 
/* 139 */     return -2;
/*     */   }
/*     */ 
/*     */   public void loadCheck()
/*     */     throws HiException
/*     */   {
/* 148 */     if (!(StringUtils.isBlank(this.name)))
/*     */       return;
/* 150 */     throw new HiException("213178", this.name);
/*     */   }
/*     */ }