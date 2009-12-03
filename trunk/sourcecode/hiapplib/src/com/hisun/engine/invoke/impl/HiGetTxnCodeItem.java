/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.invoke.HiStrategyFactory;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiGetTxnCodeItem extends HiItem
/*     */ {
/*     */   private HiExpression _valueExpr;
/*  26 */   private int pos = 0;
/*     */ 
/*     */   public void setPos(String pos) throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  32 */       this.pos = Integer.parseInt(pos.trim());
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  37 */       throw new HiException("213160", pos, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*  44 */     HiMessage mess = messContext.getCurrentMsg();
/*  45 */     String ect = mess.getHeadItem("ECT");
/*     */ 
/*  48 */     if (StringUtils.equals(ect, "text/plain"))
/*     */     {
/*  50 */       if (StringUtils.equals(get_type(), "xml"))
/*     */       {
/*  53 */         readXmlNode(mess);
/*     */       }
/*  55 */       else if (StringUtils.equals(get_type(), "etf"))
/*     */       {
/*  58 */         readXmlNode(mess);
/*     */       }
/*  60 */       else if (StringUtils.isNotEmpty(getValue()))
/*     */       {
/*  64 */         addObjectItem(mess, this._valueExpr.getValue(messContext));
/*     */       }
/*     */       else
/*     */       {
/*  68 */         if ((StringUtils.equals(get_type(), "deli")) && (this.pos > 0))
/*     */         {
/*  71 */           setDeliStartOffset(mess);
/*     */         }
/*  73 */         else if ((StringUtils.equals(get_type(), "deli_str")) && (this.pos > 0))
/*     */         {
/*  75 */           setDeliStrStartOffset(mess);
/*     */         }
/*  77 */         formatPlain(mess);
/*     */       }
/*     */ 
/*  80 */       HiItemHelper.setPlainOffset(mess, 0);
/*     */     }
/*  82 */     else if (StringUtils.equals(ect, "text/xml"))
/*     */     {
/*  84 */       if (!(StringUtils.equals(get_type(), "xml"))) {
/*     */         return;
/*     */       }
/*  87 */       readXmlNode(mess);
/*     */     }
/*     */     else
/*     */     {
/*  93 */       throw new HiException("213161", ect);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 102 */     if (StringUtils.equals(get_type(), "xml"))
/*     */     {
/* 104 */       if (StringUtils.isBlank(getValue()))
/*     */       {
/* 106 */         throw new HiException("213162", "");
/*     */       }
/*     */ 
/* 109 */       HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiXmlStategy");
/*     */ 
/* 112 */       return;
/*     */     }
/* 114 */     if (StringUtils.equals(get_type(), "etf"))
/*     */     {
/* 116 */       if (StringUtils.isBlank(getValue()))
/*     */       {
/* 118 */         throw new HiException("213162", "");
/*     */       }
/*     */ 
/* 121 */       HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiETFStategy");
/*     */ 
/* 124 */       return;
/*     */     }
/* 126 */     if (StringUtils.isNotEmpty(getValue()))
/*     */     {
/* 128 */       HiStrategyFactory.getStrategyInstance("com.hisun.engine.invoke.HiByteStategy");
/*     */ 
/* 130 */       String value = getValue();
/* 131 */       this._valueExpr = HiExpFactory.createExp(value);
/* 132 */       return;
/*     */     }
/*     */ 
/* 135 */     super.loadAfter();
/*     */   }
/*     */ 
/*     */   protected void addObjectItem(HiMessage strMsg, byte[] itemBytes)
/*     */     throws HiException
/*     */   {
/* 145 */     Logger log = HiLog.getLogger(strMsg);
/* 146 */     if (log.isDebugEnabled()) {
/* 147 */       log.debug("HiGetTxnCodItem:addObjectItem(HiMessage, byte[]) - start");
/*     */     }
/* 149 */     if (this.pro_method != null) {
/* 150 */       itemBytes = HiItemHelper.execMethod(this.pro_method, itemBytes, log);
/*     */     }
/*     */ 
/* 153 */     addObjectItem(strMsg, new String(itemBytes));
/*     */ 
/* 155 */     if (log.isDebugEnabled())
/* 156 */       log.debug("HiGetTxnCodItem:addObjectItem(HiMessage, byte[]) - end");
/*     */   }
/*     */ 
/*     */   protected void addObjectItem(HiMessage strMsg, String item)
/*     */     throws HiException
/*     */   {
/* 166 */     strMsg.setHeadItem("STC", item);
/*     */   }
/*     */ 
/*     */   private void setDeliStartOffset(HiMessage strMsg)
/*     */     throws HiException
/*     */   {
/* 177 */     HiByteBuffer plainText = HiItemHelper.getPlainText(strMsg);
/* 178 */     int startIdx = 0;
/* 179 */     for (int i = 1; i < this.pos; ++i)
/*     */     {
/* 182 */       startIdx = HiItemHelper.indexOfBytePlain(plainText, get_deli_asc(), startIdx);
/*     */ 
/* 184 */       if (startIdx < 0)
/*     */       {
/* 187 */         throw new HiException("213163", String.valueOf(this.pos));
/*     */       }
/* 189 */       ++startIdx;
/*     */     }
/* 191 */     HiItemHelper.setPlainOffset(strMsg, startIdx);
/*     */   }
/*     */ 
/*     */   private void setDeliStrStartOffset(HiMessage strMsg) throws HiException
/*     */   {
/* 196 */     HiByteBuffer plainText = HiItemHelper.getPlainText(strMsg);
/* 197 */     int startIdx = 0;
/* 198 */     for (int i = 1; i < this.pos; ++i)
/*     */     {
/* 201 */       startIdx = HiItemHelper.indexOfBytePlain(plainText, get_deli_str(), startIdx);
/*     */ 
/* 203 */       if (startIdx < 0)
/*     */       {
/* 206 */         throw new HiException("213163", String.valueOf(this.pos));
/*     */       }
/* 208 */       startIdx += HiItemHelper.getPlainByteLen(get_deli_str());
/*     */     }
/* 210 */     HiItemHelper.setPlainOffset(strMsg, startIdx);
/*     */   }
/*     */ 
/*     */   private void readXmlNode(HiMessage xmlMsg) throws HiException
/*     */   {
/* 215 */     HiETF xmlContent = (HiETF)HiItemHelper.getPlainObject(xmlMsg);
/* 216 */     if (xmlContent == null)
/*     */     {
/* 218 */       throw new HiException("213164", HiItemHelper.getPlainObject(xmlMsg).toString());
/*     */     }
/*     */ 
/* 221 */     String xmlVal = xmlContent.getGrandChildValueBase(getValue());
/* 222 */     if (StringUtils.isEmpty(xmlVal))
/*     */     {
/* 224 */       throw new HiException("213165", getValue());
/*     */     }
/*     */ 
/* 227 */     addObjectItem(xmlMsg, xmlVal);
/*     */   }
/*     */ }