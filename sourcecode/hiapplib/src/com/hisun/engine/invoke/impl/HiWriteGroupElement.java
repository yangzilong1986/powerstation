/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiWriteGroupElement extends HiAbstractDoElement
/*     */ {
/*     */   public HiWriteGroupElement()
/*     */     throws HiException
/*     */   {
/*  43 */     super.initAtt();
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  48 */     return "WriteGroupElement";
/*     */   }
/*     */ 
/*     */   protected int doElement(HiMessageContext messContext) throws HiException
/*     */   {
/*  53 */     HiMessage mess = messContext.getCurrentMsg();
/*  54 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(mess);
/*  55 */     if (xmlContent == null)
/*     */     {
/*  57 */       throw new HiException("213197", this.name);
/*     */     }
/*     */ 
/*  60 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  62 */     List childNodes = new ArrayList();
/*     */ 
/*  64 */     HiETF etfBody = mess.getETFBody();
/*  65 */     for (int i = 1; ; ++i) {
/*  66 */       String groupName = this.ETF_name + "_" + i;
/*     */ 
/*  68 */       HiETF tmpEtf = HiItemHelper.getEtfItemNode(mess, groupName);
/*  69 */       if (tmpEtf == null) {
/*     */         break;
/*     */       }
/*  72 */       childNodes.add(tmpEtf);
/*     */     }
/*     */ 
/*  75 */     if ((childNodes == null) || (childNodes.isEmpty()))
/*     */     {
/*  77 */       if (!(this.necessary))
/*     */       {
/*  79 */         if (log.isInfoEnabled())
/*     */         {
/*  81 */           log.info("WriteGroupElement:不存在指定ETF[" + this.ETF_name + "],necessary=no,本节点不构造");
/*     */         }
/*  83 */         return -2;
/*     */       }
/*  85 */       throw new HiException("213203", this.name, this.ETF_name);
/*     */     }
/*     */ 
/*  89 */     int repeatNum = 0;
/*     */ 
/*  91 */     if (StringUtils.isNotBlank(this.repeat_name))
/*     */     {
/*  93 */       repeatNum = getRepeat_num(mess);
/*     */     }
/*     */ 
/*  96 */     if (repeatNum == 0)
/*     */     {
/*  98 */       repeatNum = this.repeat_num;
/*     */     }
/*     */ 
/* 101 */     if (repeatNum > 0)
/*     */     {
/* 103 */       if (log.isInfoEnabled()) {
/* 104 */         log.info("Group: 获取RepeateNum=" + repeatNum);
/*     */       }
/* 106 */       if (repeatNum != childNodes.size())
/*     */       {
/* 108 */         throw new HiException("213204", this.name, String.valueOf(repeatNum));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 115 */     HiETF xmlParent = HiItemHelper.getCurXmlRoot(mess);
/*     */ 
/* 118 */     if (this.xml_level == 1)
/*     */     {
/* 120 */       xmlParent = xmlContent;
/*     */     }
/* 122 */     else if (this.xml_level == 2)
/*     */     {
/* 124 */       HiETF xmlGrandParent = xmlParent.getParent();
/* 125 */       if (xmlGrandParent == null)
/*     */       {
/* 127 */         throw new HiException("213205", this.name);
/*     */       }
/* 129 */       xmlParent = xmlGrandParent;
/*     */     }
/*     */ 
/* 132 */     if (xmlParent == null)
/*     */     {
/* 134 */       xmlParent = xmlContent;
/*     */     }
/* 136 */     String curEtfLevel = HiItemHelper.getCurEtfLevel(mess);
/*     */ 
/* 138 */     for (Iterator iter = childNodes.iterator(); iter.hasNext(); )
/*     */     {
/* 140 */       HiETF recNode = (HiETF)iter.next();
/*     */ 
/* 142 */       HiETF xmlNode = xmlParent.addNodeBase(this.xml_name, recNode.getValue(), this.ns, this.ns_uri);
/*     */ 
/* 144 */       if (log.isInfoEnabled()) {
/* 145 */         log.info("WriteGroupElement:构造XML节点[" + this.ETF_name + "]");
/*     */       }
/* 147 */       List child = super.getChilds();
/* 148 */       if (child == null) continue; if (child.isEmpty())
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 153 */       HiItemHelper.setCurEtfLevel(mess, curEtfLevel + recNode.getName() + ".");
/*     */ 
/* 156 */       HiItemHelper.setCurXmlRoot(mess, xmlNode);
/* 157 */       super.doProcess(messContext);
/*     */     }
/*     */ 
/* 160 */     HiItemHelper.setCurEtfLevel(mess, curEtfLevel);
/* 161 */     HiItemHelper.setCurXmlRoot(mess, xmlParent);
/*     */ 
/* 163 */     return -2;
/*     */   }
/*     */ 
/*     */   private int getRepeat_num(HiMessage msg)
/*     */   {
/* 168 */     String strNum = HiItemHelper.getEtfItem(msg, this.repeat_name);
/*     */     try
/*     */     {
/* 172 */       return Integer.parseInt(strNum.trim());
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*     */     }
/*     */ 
/* 178 */     return 0;
/*     */   }
/*     */ 
/*     */   public void loadCheck()
/*     */     throws HiException
/*     */   {
/* 188 */     if (StringUtils.isBlank(this.name))
/*     */     {
/* 190 */       throw new HiException("213201", this.name);
/*     */     }
/*     */ 
/* 193 */     if (!(StringUtils.isBlank(this.ETF_name)))
/*     */       return;
/* 195 */     throw new HiException("213202", this.name);
/*     */   }
/*     */ }