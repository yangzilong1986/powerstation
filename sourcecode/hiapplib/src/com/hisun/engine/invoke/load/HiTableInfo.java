/*     */ package com.hisun.engine.invoke.load;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class HiTableInfo
/*     */ {
/*     */   private ArrayList _columns;
/*     */   private HashMap _infos;
/*     */   private HashMap _defaultValue;
/*     */   private int type;
/*     */ 
/*     */   public HiTableInfo()
/*     */   {
/*   7 */     this._columns = new ArrayList();
/*   8 */     this._infos = new HashMap();
/*   9 */     this._defaultValue = new HashMap();
/*     */ 
/*  15 */     this.type = 0;
/*     */   }
/*     */ 
/*     */   public void addName(String name, String value)
/*     */   {
/*     */     ArrayList values;
/*  22 */     if (this._infos.containsKey(name)) {
/*  23 */       values = (ArrayList)this._infos.get(name);
/*  24 */       values.add(value);
/*     */     } else {
/*  26 */       values = new ArrayList();
/*  27 */       values.add(value);
/*  28 */       this._infos.put(name, values);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setColumns(ArrayList columns)
/*     */   {
/*  37 */     this._columns = columns;
/*     */   }
/*     */ 
/*     */   public void addDefault(String name, String value)
/*     */   {
/*  46 */     this._defaultValue.put(name, value);
/*     */   }
/*     */ 
/*     */   public String getColValue(String col1_name, String col1_value)
/*     */   {
/*  57 */     int idx = this._columns.indexOf(col1_name);
/*  58 */     idx = (idx == 0) ? 1 : 0;
/*  59 */     return getColValue(col1_name, col1_value, (String)this._columns.get(idx));
/*     */   }
/*     */ 
/*     */   public String getColValue(String col1_name, String col1_value, String col2_name)
/*     */   {
/*  71 */     if ((!(this._infos.containsKey(col1_name))) || (!(this._infos.containsKey(col2_name)))) {
/*  72 */       return NotFoundProc(col2_name, col1_value);
/*     */     }
/*     */ 
/*  75 */     ArrayList values1 = (ArrayList)this._infos.get(col1_name);
/*  76 */     ArrayList values2 = (ArrayList)this._infos.get(col2_name);
/*  77 */     int idx = values1.indexOf(col1_value);
/*  78 */     if ((idx == -1) || (values2.get(idx) == null)) {
/*  79 */       return NotFoundProc(col2_name, col1_value);
/*     */     }
/*     */ 
/*  83 */     return ((String)values2.get(idx));
/*     */   }
/*     */ 
/*     */   public String NotFoundProc(String col_name, String col_value) {
/*  87 */     switch (this.type)
/*     */     {
/*     */     case 0:
/*  89 */       return ((String)this._defaultValue.get(col_name));
/*     */     case 1:
/*  91 */       return col_value;
/*     */     }
/*  93 */     return ((String)this._defaultValue.get(col_name));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  98 */     StringBuffer result = new StringBuffer();
/*  99 */     result.append("_columns：");
/* 100 */     result.append(this._columns);
/* 101 */     result.append("_infos：");
/* 102 */     result.append(this._infos);
/* 103 */     result.append("_defaultValue：");
/* 104 */     result.append(this._defaultValue);
/* 105 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public int getType() {
/* 109 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 113 */     this.type = type;
/*     */   }
/*     */ }