/*     */ package com.hisun.cnaps.tags;
/*     */ 
/*     */ import com.hisun.cnaps.common.HiCnapsDataTypeHelper;
/*     */ import com.hisun.cnaps.common.HiRepeatTagManager;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiCnapsTagImpl
/*     */   implements HiCnapsTag
/*     */ {
/*     */   protected String name;
/*     */   protected String etfName;
/*     */   protected int dataType;
/*     */   protected int length;
/*     */   protected String repeatName;
/*     */   protected String value;
/*     */   private HiRepeatTagManager manager;
/*     */ 
/*     */   protected HiRepeatTagManager getRepeatManager()
/*     */   {
/*  29 */     return this.manager;
/*     */   }
/*     */ 
/*     */   public HiCnapsTagImpl(Element element)
/*     */   {
/*  34 */     this.name = element.attributeValue("mark");
/*  35 */     String _dataType = element.attributeValue("etf_name");
/*  36 */     if (!(StringUtils.isBlank(_dataType)))
/*  37 */       this.dataType = (_dataType.charAt(0) & 0xFF);
/*     */     else
/*  39 */       this.dataType = 110;
/*  40 */     String _length = element.attributeValue("length");
/*     */ 
/*  42 */     if (!(StringUtils.isBlank(_length)))
/*     */       try
/*     */       {
/*  45 */         this.length = Integer.parseInt(_length);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*  49 */         this.length = -1;
/*     */       }
/*  51 */     this.repeatName = element.attributeValue("sub_repeat");
/*  52 */     this.etfName = element.attributeValue("etf_name");
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  57 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void setEtfName(String etfName)
/*     */   {
/*  62 */     this.etfName = etfName;
/*     */   }
/*     */ 
/*     */   public void setDataType(int dataType)
/*     */   {
/*  67 */     this.dataType = dataType;
/*     */   }
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/*  72 */     this.length = length;
/*     */   }
/*     */ 
/*     */   public void setRepeatName(String repeatName)
/*     */   {
/*  77 */     this.repeatName = repeatName;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  82 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String getEtfName()
/*     */   {
/*  87 */     return this.etfName;
/*     */   }
/*     */ 
/*     */   public int getDataType()
/*     */   {
/*  92 */     return this.dataType;
/*     */   }
/*     */ 
/*     */   public String getRepeatName()
/*     */   {
/*  97 */     return this.repeatName;
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 102 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getMarkName()
/*     */   {
/* 107 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setEtfname(String etfName)
/*     */   {
/* 112 */     this.etfName = etfName;
/*     */   }
/*     */ 
/*     */   public void parse(String buffer)
/*     */     throws HiException
/*     */   {
/* 118 */     this.value = buffer;
/*     */   }
/*     */ 
/*     */   public void unpack2Etf(HiETF etf)
/*     */     throws HiException
/*     */   {
/* 127 */     if (StringUtils.isNotBlank(this.repeatName))
/*     */     {
/* 129 */       String count = this.manager.nextTagEtfName(this.etfName);
/* 130 */       this.etfName = this.etfName.concat("_").concat(count);
/* 131 */       etf.setChildValue(this.repeatName, count);
/*     */     }
/* 133 */     etf.addNode(this.etfName, this.value);
/*     */   }
/*     */ 
/*     */   public String parse(HiETF etf)
/*     */     throws HiException
/*     */   {
/* 139 */     if (StringUtils.isNotBlank(this.repeatName))
/* 140 */       this.etfName = this.etfName.concat("_").concat(this.manager.nextTagEtfName(this.etfName));
/* 141 */     String aStr = etf.getGrandChildValue(this.etfName);
/* 142 */     this.value = HiCnapsDataTypeHelper.fullIfNeed(getDataType(), getLength(), (aStr != null) ? aStr : "");
/*     */ 
/* 144 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setRepeatManager(HiRepeatTagManager manager)
/*     */   {
/* 149 */     this.manager = manager;
/*     */   }
/*     */ }