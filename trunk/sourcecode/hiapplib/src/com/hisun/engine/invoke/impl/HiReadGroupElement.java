/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiReadGroupElement extends HiAbstractDoElement
/*     */ {
/*     */   public HiReadGroupElement()
/*     */     throws HiException
/*     */   {
/*  45 */     super.initAtt();
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  50 */     return "ReadGroupElement";
/*     */   }
/*     */ 
/*     */   protected int doElement(HiMessageContext messContext) throws HiException {
/*  54 */     HiMessage mess = messContext.getCurrentMsg();
/*  55 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
/*  56 */     if (xmlContent == null)
/*     */     {
/*  58 */       throw new HiException("213186", this.name);
/*     */     }
/*  60 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  64 */     List childNodes = null;
/*  65 */     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
/*     */ 
/*  69 */     if (this.xml_level == 1)
/*     */     {
/*  71 */       xmlParent = xmlContent;
/*     */     }
/*  73 */     else if (this.xml_level == 2)
/*     */     {
/*  75 */       HiETF xmlGrandParent = xmlParent.getParent();
/*  76 */       if (xmlGrandParent == null)
/*     */       {
/*  78 */         throw new HiException("213187", this.name);
/*     */       }
/*  80 */       xmlParent = xmlGrandParent;
/*     */     }
/*  82 */     if (xmlParent == null)
/*     */     {
/*  84 */       childNodes = xmlContent.getGrandChildNodesBase(this.xml_name, this.ns, this.ns_uri);
/*     */     }
/*     */     else
/*     */     {
/*  89 */       childNodes = xmlParent.getGrandChildNodesBase(this.xml_name, this.ns, this.ns_uri);
/*     */     }
/*     */ 
/*  92 */     if ((childNodes == null) || (childNodes.isEmpty()))
/*     */     {
/*  94 */       if (!(this.necessary))
/*     */       {
/*  96 */         if (log.isInfoEnabled())
/*     */         {
/*  98 */           log.info("ReadGroupElement:不存在XML节点名[" + this.name + "],necessary=no,本节点及子节点不解析");
/*     */         }
/* 100 */         return -2;
/*     */       }
/* 102 */       throw new HiException("213188", this.name);
/*     */     }
/*     */ 
/* 105 */     int repeatNum = 0;
/* 106 */     if (StringUtils.isNotBlank(this.repeat_name))
/*     */     {
/* 108 */       repeatNum = getRepeat_num(mess);
/*     */     }
/*     */ 
/* 111 */     if (repeatNum == 0)
/*     */     {
/* 113 */       repeatNum = this.repeat_num;
/*     */     }
/*     */ 
/* 116 */     if (repeatNum > 0)
/*     */     {
/* 118 */       if (log.isInfoEnabled()) {
/* 119 */         log.info("Group: 获取RepeateNum=" + repeatNum);
/*     */       }
/* 121 */       if (repeatNum != childNodes.size())
/*     */       {
/* 123 */         throw new HiException("213189", this.name, String.valueOf(repeatNum));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 128 */     int i = 1;
/* 129 */     HiETF etfBody = mess.getETFBody();
/* 130 */     String curEtfLevel = HiItemHelper.getCurEtfLevel(mess);
/* 131 */     for (Iterator iter = childNodes.iterator(); iter.hasNext(); )
/*     */     {
/* 133 */       HiETF xmlNode = (HiETF)iter.next();
/* 134 */       HiEngineUtilities.setCurFlowStep(i);
/*     */ 
/* 136 */       HiItemHelper.addEtfItem(mess, this.ETF_name + "_" + i, xmlNode.getValue());
/*     */ 
/* 138 */       if (log.isInfoEnabled()) {
/* 139 */         log.info("ReadGroupElement:构造ETF Group域[" + this.ETF_name + "]");
/*     */       }
/* 141 */       List child = super.getChilds();
/* 142 */       if ((child == null) || (child.isEmpty()))
/*     */       {
/* 144 */         ++i;
/*     */       }
/*     */ 
/* 147 */       HiItemHelper.setCurEtfLevel(mess, curEtfLevel + this.ETF_name + "_" + i + ".");
/*     */ 
/* 151 */       HiItemHelper.setCurXmlRoot(mess, xmlNode);
/* 152 */       super.doProcess(messContext);
/*     */ 
/* 154 */       ++i;
/*     */     }
/* 156 */     HiItemHelper.setCurEtfLevel(mess, curEtfLevel);
/* 157 */     HiItemHelper.setCurXmlRoot(mess, xmlParent);
/*     */ 
/* 159 */     return -2;
/*     */   }
/*     */ 
/*     */   public void loadCheck()
/*     */     throws HiException
/*     */   {
/* 169 */     if (StringUtils.isBlank(this.name))
/*     */     {
/* 171 */       throw new HiException("213184", this.name);
/*     */     }
/*     */ 
/* 174 */     if (!(StringUtils.isBlank(this.ETF_name)))
/*     */       return;
/* 176 */     throw new HiException("213185", this.name);
/*     */   }
/*     */ 
/*     */   private int getRepeat_num(HiMessage msg)
/*     */   {
/* 182 */     String strNum = HiItemHelper.getEtfItem(msg, this.repeat_name);
/*     */ 
/* 184 */     return NumberUtils.toInt(strNum);
/*     */   }
/*     */ }