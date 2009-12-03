/*     */ package com.hisun.atc.bat;
/*     */ 
/*     */ import com.hisun.engine.HiITFEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiAbstractFMT extends HiITFEngineModel
/*     */ {
/*  12 */   private HiContext context = HiContext.getCurrentContext();
/*     */ 
/*  17 */   private boolean line_wrap = true;
/*     */ 
/*  22 */   private int max_length = -1;
/*     */   private String pro_dll;
/*     */   private String pro_func;
/*  38 */   private int record_length = -1;
/*     */ 
/*     */   public HiAbstractFMT()
/*     */   {
/*  42 */     super.setItemAttribute(this.context);
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  47 */     return "End";
/*     */   }
/*     */ 
/*     */   public void Pro_func(String pro_func)
/*     */   {
/*  52 */     this.pro_func = pro_func;
/*     */   }
/*     */ 
/*     */   public void setLine_wrap(String line_wrap)
/*     */   {
/*  57 */     if (StringUtils.equalsIgnoreCase(line_wrap, "N"))
/*  58 */       this.line_wrap = false;
/*     */   }
/*     */ 
/*     */   public boolean isLineWrap() {
/*  62 */     return this.line_wrap;
/*     */   }
/*     */ 
/*     */   public void setMax_length(int max_length) {
/*  66 */     this.max_length = max_length;
/*     */   }
/*     */ 
/*     */   public void setPro_dll(String pro_dll)
/*     */   {
/*  71 */     this.pro_dll = pro_dll;
/*     */   }
/*     */ 
/*     */   public void setRecord_length(int record_length)
/*     */     throws HiException
/*     */   {
/*  80 */     this.record_length = record_length;
/*     */   }
/*     */ 
/*     */   public int getRecordLength()
/*     */   {
/*  85 */     return this.record_length;
/*     */   }
/*     */ 
/*     */   public int getMaxLength()
/*     */   {
/*  90 */     return this.max_length;
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException
/*     */   {
/*  95 */     if (this.record_length == -1)
/*  96 */       this.record_length = this.max_length;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 105 */     StringBuffer result = new StringBuffer();
/* 106 */     result.append("line_wrap:" + this.line_wrap);
/* 107 */     result.append(";max_length:" + this.max_length);
/* 108 */     result.append(";pro_dll:" + this.pro_dll);
/* 109 */     result.append(";pro_func:" + this.pro_func);
/* 110 */     result.append(";record_length:" + this.record_length);
/* 111 */     return result.toString();
/*     */   }
/*     */ }