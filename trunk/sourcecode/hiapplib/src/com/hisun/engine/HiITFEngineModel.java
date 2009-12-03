/*     */ package com.hisun.engine;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public abstract class HiITFEngineModel extends HiEngineModel
/*     */ {
/*     */   private static final String _KEY = "_ITFEngineModel";
/*     */   protected String _type;
/*     */   protected byte _deli_asc;
/*     */   protected int _head_len;
/*     */   protected String _len_type;
/*     */   protected boolean _include_len;
/*     */   protected String _deli_str;
/*     */   protected boolean _necessary;
/*     */   protected String _align_mode;
/*     */   protected boolean _pass;
/*     */   protected int _seq_no;
/*     */   protected boolean _ignoreCase;
/*     */ 
/*     */   public HiITFEngineModel()
/*     */   {
/*  27 */     this._deli_asc = 0;
/*     */ 
/*  37 */     this._len_type = "char";
/*     */ 
/*  42 */     this._include_len = false;
/*     */ 
/*  51 */     this._necessary = true;
/*     */ 
/*  74 */     this._ignoreCase = false;
/*     */   }
/*     */ 
/*     */   public void cloneProperty(HiITFEngineModel itemAttribute)
/*     */   {
/*  79 */     this._type = itemAttribute._type;
/*  80 */     this._deli_asc = itemAttribute._deli_asc;
/*  81 */     this._head_len = itemAttribute._head_len;
/*  82 */     this._len_type = itemAttribute._len_type;
/*  83 */     this._include_len = itemAttribute._include_len;
/*  84 */     this._deli_str = itemAttribute._deli_str;
/*  85 */     this._necessary = itemAttribute._necessary;
/*  86 */     this._align_mode = itemAttribute._align_mode;
/*  87 */     this._pass = itemAttribute._pass;
/*  88 */     this._seq_no = itemAttribute._seq_no;
/*  89 */     this._ignoreCase = itemAttribute._ignoreCase;
/*     */   }
/*     */ 
/*     */   public void setItemAttribute(HiContext ctx)
/*     */   {
/*  94 */     ctx.setProperty("_ITFEngineModel", this);
/*     */   }
/*     */ 
/*     */   public void clearItemAttribute(HiContext ctx) {
/*  98 */     ctx.delProperty("_ITFEngineModel");
/*     */   }
/*     */ 
/*     */   public static HiITFEngineModel getItemAttribute(HiContext ctx) {
/* 102 */     return ((HiITFEngineModel)ctx.getProperty("_ITFEngineModel"));
/*     */   }
/*     */ 
/*     */   public String get_align_mode() {
/* 106 */     return this._align_mode;
/*     */   }
/*     */ 
/*     */   public void setAlign_mode(String align_mode) {
/* 110 */     this._align_mode = align_mode;
/*     */   }
/*     */ 
/*     */   public byte get_deli_asc() {
/* 114 */     return this._deli_asc;
/*     */   }
/*     */ 
/*     */   public void setDeli_asc(String deli_asc) {
/* 118 */     this._deli_asc = Byte.parseByte(deli_asc);
/* 119 */     this._type = "deli";
/*     */   }
/*     */ 
/*     */   public String get_deli_str() {
/* 123 */     return this._deli_str;
/*     */   }
/*     */ 
/*     */   public void setDeli_str(String _deli_str) {
/* 127 */     this._deli_str = _deli_str;
/* 128 */     this._type = "deli_str";
/*     */   }
/*     */ 
/*     */   public int get_head_len() {
/* 132 */     return this._head_len;
/*     */   }
/*     */ 
/*     */   public void setHead_len(String head_len) {
/* 136 */     this._head_len = NumberUtils.toInt(head_len);
/* 137 */     this._type = "ahead_len";
/*     */   }
/*     */ 
/*     */   public boolean is_include_len() {
/* 141 */     return this._include_len;
/*     */   }
/*     */ 
/*     */   public void setInclude_len(String include_len) {
/* 145 */     if (StringUtils.equalsIgnoreCase(include_len, "yes"))
/* 146 */       this._include_len = true;
/*     */   }
/*     */ 
/*     */   public String get_len_type()
/*     */   {
/* 155 */     return this._len_type;
/*     */   }
/*     */ 
/*     */   public void setLen_type(String len_type) {
/* 159 */     this._len_type = len_type;
/*     */   }
/*     */ 
/*     */   public boolean is_necessary() {
/* 163 */     return this._necessary;
/*     */   }
/*     */ 
/*     */   public void setNecessary(String necessary) {
/* 167 */     if (StringUtils.equalsIgnoreCase(necessary, "no"))
/* 168 */       this._necessary = false;
/*     */   }
/*     */ 
/*     */   public boolean is_ignoreCase() {
/* 172 */     return this._ignoreCase;
/*     */   }
/*     */ 
/*     */   public void setIgnoreCase(String ignoreCase) {
/* 176 */     if (StringUtils.equalsIgnoreCase(ignoreCase, "yes"))
/* 177 */       this._ignoreCase = true;
/*     */   }
/*     */ 
/*     */   public boolean is_pass() {
/* 181 */     return this._pass;
/*     */   }
/*     */ 
/*     */   public void setPass(String pass) {
/* 185 */     if (StringUtils.equalsIgnoreCase(pass, "yes"))
/* 186 */       this._pass = true;
/*     */   }
/*     */ 
/*     */   public int get_seq_no() {
/* 190 */     return this._seq_no;
/*     */   }
/*     */ 
/*     */   public void setSeq_no(String seq_no) {
/* 194 */     this._seq_no = NumberUtils.toInt(seq_no);
/*     */   }
/*     */ 
/*     */   public String get_type() {
/* 198 */     return this._type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 202 */     this._type = type;
/*     */   }
/*     */ }