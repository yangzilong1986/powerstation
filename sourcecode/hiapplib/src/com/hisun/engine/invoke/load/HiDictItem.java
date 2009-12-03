/*     */ package com.hisun.engine.invoke.load;
/*     */ 
/*     */ public class HiDictItem
/*     */ {
/*     */   public String _name;
/*     */   public String _etf_name;
/*     */   public String _type;
/*     */   public String _deli_asc;
/*     */   public String _head_len;
/*     */   public String _len_type;
/*     */   public String _include_len;
/*     */   public String _deli_str;
/*     */   public String _necessary;
/*     */   public String _align_mode;
/*     */   public String _pass;
/*     */   public String _seq_no;
/*     */   public String _length;
/*     */   public String _data_type;
/*     */   public String _must_input;
/*     */ 
/*     */   public HiDictItem()
/*     */   {
/*  10 */     this._name = null;
/*     */ 
/*  12 */     this._etf_name = null;
/*     */ 
/*  18 */     this._type = null;
/*     */ 
/*  23 */     this._deli_asc = null;
/*     */ 
/*  28 */     this._head_len = null;
/*     */ 
/*  33 */     this._len_type = null;
/*     */ 
/*  38 */     this._include_len = null;
/*     */ 
/*  47 */     this._necessary = null;
/*     */ 
/*  53 */     this._align_mode = null;
/*     */ 
/*  59 */     this._pass = null;
/*     */ 
/*  64 */     this._seq_no = null;
/*     */ 
/*  69 */     this._length = null;
/*     */ 
/*  74 */     this._data_type = null;
/*     */ 
/*  79 */     this._must_input = null; }
/*     */ 
/*     */   public String getName() {
/*  82 */     return this._name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  86 */     this._name = name.toUpperCase();
/*     */   }
/*     */ 
/*     */   public String getEtf_name() {
/*  90 */     return this._etf_name;
/*     */   }
/*     */ 
/*     */   public void setEtf_name(String name) {
/*  94 */     this._etf_name = name.toUpperCase();
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  98 */     return this._type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 102 */     this._type = type;
/*     */   }
/*     */ 
/*     */   public String getDeli_asc() {
/* 106 */     return this._deli_asc;
/*     */   }
/*     */ 
/*     */   public void setDeli_asc(String deli_asc) {
/* 110 */     this._deli_asc = deli_asc;
/*     */   }
/*     */ 
/*     */   public String getHead_len() {
/* 114 */     return this._head_len;
/*     */   }
/*     */ 
/*     */   public void setHead_len(String head_len) {
/* 118 */     this._head_len = head_len;
/*     */   }
/*     */ 
/*     */   public String getLen_type() {
/* 122 */     return this._len_type;
/*     */   }
/*     */ 
/*     */   public void setLen_type(String len_type) {
/* 126 */     this._len_type = len_type;
/*     */   }
/*     */ 
/*     */   public String getInclude_len() {
/* 130 */     return this._include_len;
/*     */   }
/*     */ 
/*     */   public void setInclude_len(String include_len) {
/* 134 */     this._include_len = include_len;
/*     */   }
/*     */ 
/*     */   public String getDeli_str() {
/* 138 */     return this._deli_str;
/*     */   }
/*     */ 
/*     */   public void setDeli_str(String deli_str) {
/* 142 */     this._deli_str = deli_str;
/*     */   }
/*     */ 
/*     */   public String getNecessary() {
/* 146 */     return this._necessary;
/*     */   }
/*     */ 
/*     */   public void setNecessary(String necessary) {
/* 150 */     this._necessary = necessary;
/*     */   }
/*     */ 
/*     */   public String getAlign_mode() {
/* 154 */     return this._align_mode;
/*     */   }
/*     */ 
/*     */   public void setAlign_mode(String align_mode) {
/* 158 */     this._align_mode = align_mode;
/*     */   }
/*     */ 
/*     */   public String getPass() {
/* 162 */     return this._pass;
/*     */   }
/*     */ 
/*     */   public void setPass(String pass) {
/* 166 */     this._pass = pass;
/*     */   }
/*     */ 
/*     */   public String getSeq_no() {
/* 170 */     return this._seq_no;
/*     */   }
/*     */ 
/*     */   public void setSeq_no(String seq_no) {
/* 174 */     this._seq_no = seq_no;
/*     */   }
/*     */ 
/*     */   public String getLength() {
/* 178 */     return this._length;
/*     */   }
/*     */ 
/*     */   public void setLength(String length) {
/* 182 */     this._length = length;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 186 */     StringBuffer result = new StringBuffer();
/* 187 */     if (this._name != null)
/* 188 */       result.append("[name=" + this._name + "];");
/* 189 */     if (this._etf_name != null)
/* 190 */       result.append("[etf_name=" + this._etf_name + "];");
/* 191 */     if (this._type != null)
/* 192 */       result.append("[type=" + this._type + "];");
/* 193 */     if (this._deli_asc != null)
/* 194 */       result.append("[deli_asc=" + this._deli_asc + "];");
/* 195 */     if (this._head_len != null)
/* 196 */       result.append("[head_len=" + this._head_len + "];");
/* 197 */     if (this._len_type != null)
/* 198 */       result.append("[len_type=" + this._len_type + "];");
/* 199 */     if (this._include_len != null)
/* 200 */       result.append("[include_len=" + this._include_len + "];");
/* 201 */     if (this._deli_str != null)
/* 202 */       result.append("[deli_str=" + this._include_len + "];");
/* 203 */     if (this._necessary != null)
/* 204 */       result.append("[necessary=" + this._necessary + "];");
/* 205 */     if (this._align_mode != null)
/* 206 */       result.append("[align_mode=" + this._align_mode + "];");
/* 207 */     if (this._pass != null)
/* 208 */       result.append("[pass=" + this._pass + "];");
/* 209 */     if (this._seq_no != null)
/* 210 */       result.append("[seq_no=" + this._seq_no + "];");
/* 211 */     if (this._length != null)
/* 212 */       result.append("[length=" + this._length + "];");
/* 213 */     if (this._must_input != null)
/* 214 */       result.append("[must_input=" + this._must_input + "];");
/* 215 */     if (this._data_type != null)
/* 216 */       result.append("[data_type=" + this._data_type + "];");
/* 217 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public String getData_type() {
/* 221 */     return this._data_type;
/*     */   }
/*     */ 
/*     */   public void setData_type(String type) {
/* 225 */     this._data_type = type;
/*     */   }
/*     */ 
/*     */   public String getMust_input() {
/* 229 */     return this._must_input;
/*     */   }
/*     */ 
/*     */   public void setMust_input(String input) {
/* 233 */     this._must_input = input;
/*     */   }
/*     */ }