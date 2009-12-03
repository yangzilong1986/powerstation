/*     */ package com.hisun.cnaps.messages;
/*     */ 
/*     */ import com.hisun.cnaps.CnapsTag;
/*     */ import com.hisun.cnaps.HiCnapsCodeTable;
/*     */ import com.hisun.cnaps.common.HiRepeatTagManager;
/*     */ import com.hisun.cnaps.tags.HiCnapsTag;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiCnapsTagMessageArea extends HiCnapsMessageArea
/*     */ {
/*  15 */   private static String BODY_TAG_SEP = ":";
/*     */   private String buffer;
/*     */ 
/*     */   public HiCnapsTagMessageArea()
/*     */   {
/*  17 */     this.buffer = ""; }
/*     */ 
/*     */   public void unpack(String bodyBuffer) throws HiException {
/*  20 */     int index = 3;
/*  21 */     int endInx = -1;
/*  22 */     int tmpInx = 3;
/*  23 */     HiRepeatTagManager manager = new HiRepeatTagManager();
/*     */     while (true)
/*     */     {
/*     */       String value;
/*  25 */       index = bodyBuffer.indexOf(BODY_TAG_SEP, tmpInx);
/*  26 */       endInx = bodyBuffer.indexOf(BODY_TAG_SEP, ++index);
/*  27 */       if (endInx == -1) {
/*  28 */         throw new HiException("241098", bodyBuffer);
/*     */       }
/*     */ 
/*  32 */       String tagName = bodyBuffer.substring(index, endInx++);
/*  33 */       HiCnapsTag tag = getCnapsCodeTable().getTagFromMark(tagName);
/*  34 */       if (tag == null) {
/*  35 */         throw new HiException("241099", tagName);
/*     */       }
/*     */ 
/*  39 */       tag.setRepeatManager(manager);
/*  40 */       index = tmpInx = endInx;
/*     */ 
/*  44 */       endInx = bodyBuffer.indexOf(BODY_TAG_SEP, index);
/*  45 */       if (endInx != -1) {
/*  46 */         if (bodyBuffer.charAt(endInx + 1) == ':') {
/*  47 */           endInx -= 2;
/*  48 */           value = bodyBuffer.substring(index, endInx);
/*  49 */           this.buffer = bodyBuffer.substring(0, endInx);
/*  50 */           tag.parse(value);
/*  51 */           addTag(tag);
/*  52 */           return;
/*     */         }
/*  54 */         value = bodyBuffer.substring(index, endInx++);
/*     */ 
/*  56 */         if (tag == null)
/*     */           continue;
/*  58 */         tag.parse(value);
/*  59 */         addTag(tag);
/*     */       }
/*     */       else {
/*  62 */         value = bodyBuffer.substring(index, bodyBuffer.length() - 1);
/*  63 */         if (tag == null)
/*     */           continue;
/*  65 */         tag.parse(value);
/*  66 */         addTag(tag);
/*  67 */         this.buffer = bodyBuffer.substring(0, bodyBuffer.length());
/*  68 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */   {
/*  76 */     if (StringUtils.isBlank(this.buffer)) {
/*  77 */       this.buffer = toString();
/*     */     }
/*  79 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   public void packFromETF(String[] fileds, String[] optFileds, HiETF etf)
/*     */     throws HiException
/*     */   {
/*     */     HiCnapsTag tag;
/*  84 */     StringBuffer sb = new StringBuffer("{3:");
/*  85 */     HiRepeatTagManager manager = new HiRepeatTagManager();
/*  86 */     for (int i = 0; i < fileds.length; ++i) {
/*  87 */       tag = getCnapsCodeTable().getTagFromMark(fileds[i]);
/*  88 */       if (tag == null) {
/*  89 */         throw new HiException("241104", fileds[i]);
/*     */       }
/*  91 */       tag.setRepeatManager(manager);
/*  92 */       tag.parse(etf);
/*  93 */       sb.append(BODY_TAG_SEP).append(tag.getMarkName()).append(BODY_TAG_SEP).append(tag.getValue());
/*     */     }
/*     */ 
/*  96 */     if (optFileds != null) {
/*  97 */       for (int p = 0; p < optFileds.length; ++p) {
/*  98 */         tag = getCnapsCodeTable().getTagFromMark(optFileds[p]);
/*     */ 
/* 100 */         if (tag == null)
/*     */           continue;
/* 102 */         tag.setRepeatManager(manager);
/* 103 */         tag.parse(etf);
/* 104 */         sb.append(BODY_TAG_SEP).append(tag.getMarkName()).append(BODY_TAG_SEP).append(tag.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 108 */     sb.append("}");
/* 109 */     this.buffer = sb.toString();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 114 */     StringBuffer sb = new StringBuffer("{3:");
/* 115 */     int count = getTagCount();
/* 116 */     for (int i = 0; i < count; ++i) {
/* 117 */       CnapsTag tag = getTagByIndex(i);
/* 118 */       sb.append(BODY_TAG_SEP).append(tag.getMarkName()).append(BODY_TAG_SEP).append(tag.getValue());
/*     */     }
/*     */ 
/* 121 */     sb.append("}");
/* 122 */     return sb.toString();
/*     */   }
/*     */ }