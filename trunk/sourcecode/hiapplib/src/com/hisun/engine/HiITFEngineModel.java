 package com.hisun.engine;
 
 import com.hisun.message.HiContext;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public abstract class HiITFEngineModel extends HiEngineModel
 {
   private static final String _KEY = "_ITFEngineModel";
   protected String _type;
   protected byte _deli_asc;
   protected int _head_len;
   protected String _len_type;
   protected boolean _include_len;
   protected String _deli_str;
   protected boolean _necessary;
   protected String _align_mode;
   protected boolean _pass;
   protected int _seq_no;
   protected boolean _ignoreCase;
 
   public HiITFEngineModel()
   {
     this._deli_asc = 0;
 
     this._len_type = "char";
 
     this._include_len = false;
 
     this._necessary = true;
 
     this._ignoreCase = false;
   }
 
   public void cloneProperty(HiITFEngineModel itemAttribute)
   {
     this._type = itemAttribute._type;
     this._deli_asc = itemAttribute._deli_asc;
     this._head_len = itemAttribute._head_len;
     this._len_type = itemAttribute._len_type;
     this._include_len = itemAttribute._include_len;
     this._deli_str = itemAttribute._deli_str;
     this._necessary = itemAttribute._necessary;
     this._align_mode = itemAttribute._align_mode;
     this._pass = itemAttribute._pass;
     this._seq_no = itemAttribute._seq_no;
     this._ignoreCase = itemAttribute._ignoreCase;
   }
 
   public void setItemAttribute(HiContext ctx)
   {
     ctx.setProperty("_ITFEngineModel", this);
   }
 
   public void clearItemAttribute(HiContext ctx) {
     ctx.delProperty("_ITFEngineModel");
   }
 
   public static HiITFEngineModel getItemAttribute(HiContext ctx) {
     return ((HiITFEngineModel)ctx.getProperty("_ITFEngineModel"));
   }
 
   public String get_align_mode() {
     return this._align_mode;
   }
 
   public void setAlign_mode(String align_mode) {
     this._align_mode = align_mode;
   }
 
   public byte get_deli_asc() {
     return this._deli_asc;
   }
 
   public void setDeli_asc(String deli_asc) {
     this._deli_asc = Byte.parseByte(deli_asc);
     this._type = "deli";
   }
 
   public String get_deli_str() {
     return this._deli_str;
   }
 
   public void setDeli_str(String _deli_str) {
     this._deli_str = _deli_str;
     this._type = "deli_str";
   }
 
   public int get_head_len() {
     return this._head_len;
   }
 
   public void setHead_len(String head_len) {
     this._head_len = NumberUtils.toInt(head_len);
     this._type = "ahead_len";
   }
 
   public boolean is_include_len() {
     return this._include_len;
   }
 
   public void setInclude_len(String include_len) {
     if (StringUtils.equalsIgnoreCase(include_len, "yes"))
       this._include_len = true;
   }
 
   public String get_len_type()
   {
     return this._len_type;
   }
 
   public void setLen_type(String len_type) {
     this._len_type = len_type;
   }
 
   public boolean is_necessary() {
     return this._necessary;
   }
 
   public void setNecessary(String necessary) {
     if (StringUtils.equalsIgnoreCase(necessary, "no"))
       this._necessary = false;
   }
 
   public boolean is_ignoreCase() {
     return this._ignoreCase;
   }
 
   public void setIgnoreCase(String ignoreCase) {
     if (StringUtils.equalsIgnoreCase(ignoreCase, "yes"))
       this._ignoreCase = true;
   }
 
   public boolean is_pass() {
     return this._pass;
   }
 
   public void setPass(String pass) {
     if (StringUtils.equalsIgnoreCase(pass, "yes"))
       this._pass = true;
   }
 
   public int get_seq_no() {
     return this._seq_no;
   }
 
   public void setSeq_no(String seq_no) {
     this._seq_no = NumberUtils.toInt(seq_no);
   }
 
   public String get_type() {
     return this._type;
   }
 
   public void setType(String type) {
     this._type = type;
   }
 }