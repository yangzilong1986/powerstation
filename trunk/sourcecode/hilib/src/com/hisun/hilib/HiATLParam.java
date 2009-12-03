/*     */ package com.hisun.hilib;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiATLParam
/*     */ {
/*     */   private ArrayList nameList;
/*     */   private ArrayList valueList;
/*     */   private LinkedHashMap map;
/*     */ 
/*     */   public HiATLParam()
/*     */   {
/*  15 */     this.nameList = new ArrayList(2);
/*     */ 
/*  17 */     this.valueList = new ArrayList(2);
/*     */ 
/*  19 */     this.map = null;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*  26 */     this.nameList.clear();
/*  27 */     this.valueList.clear();
/*  28 */     if (this.map != null)
/*  29 */       this.map.clear();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  37 */     return this.nameList.size();
/*     */   }
/*     */ 
/*     */   public boolean contains(String name)
/*     */   {
/*  46 */     return this.nameList.contains(name.toUpperCase());
/*     */   }
/*     */ 
/*     */   public String get(String name)
/*     */   {
/*  55 */     return ((String)getObject(name));
/*     */   }
/*     */ 
/*     */   public int getInt(String name)
/*     */   {
/*  64 */     return NumberUtils.toInt(get(name));
/*     */   }
/*     */ 
/*     */   public int getInt(int idx)
/*     */   {
/*  73 */     return NumberUtils.toInt(StringUtils.trim(getValue(idx)));
/*     */   }
/*     */ 
/*     */   public boolean getBoolean(String name)
/*     */   {
/*  82 */     String value = get(name);
/*     */ 
/*  86 */     return ((!(StringUtils.equalsIgnoreCase(value, "1"))) && (!(StringUtils.equalsIgnoreCase(value, "true"))) && (!(StringUtils.equalsIgnoreCase(value, "yes"))));
/*     */   }
/*     */ 
/*     */   public Object getObject(String name)
/*     */   {
/*  98 */     int idx = this.nameList.indexOf(name.toUpperCase());
/*  99 */     if (idx == -1)
/* 100 */       return null;
/* 101 */     return this.valueList.get(idx);
/*     */   }
/*     */ 
/*     */   public String getValue(int index)
/*     */   {
/* 110 */     return ((String)getValueObject(index));
/*     */   }
/*     */ 
/*     */   public Object getValueObject(int index)
/*     */   {
/* 119 */     return this.valueList.get(index);
/*     */   }
/*     */ 
/*     */   public String getName(int index)
/*     */   {
/* 128 */     return ((String)this.nameList.get(index));
/*     */   }
/*     */ 
/*     */   public void put(String name, Object value)
/*     */   {
/* 137 */     this.nameList.add(name.toUpperCase());
/* 138 */     this.valueList.add(value);
/*     */   }
/*     */ 
/*     */   public ArrayList values()
/*     */   {
/* 147 */     return this.valueList;
/*     */   }
/*     */ 
/*     */   public ArrayList names()
/*     */   {
/* 155 */     return this.nameList;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap toMap()
/*     */   {
/* 163 */     if (this.map == null) {
/* 164 */       this.map = new LinkedHashMap();
/*     */     }
/* 166 */     this.map.clear();
/* 167 */     for (int i = 0; i < size(); ++i) {
/* 168 */       this.map.put(getName(i), getValue(i));
/*     */     }
/* 170 */     return this.map;
/*     */   }
/*     */ }