 package com.hisun.engine.invoke.load;
 
 public class HiDictItem
 {
   public String _name;
   public String _etf_name;
   public String _type;
   public String _deli_asc;
   public String _head_len;
   public String _len_type;
   public String _include_len;
   public String _deli_str;
   public String _necessary;
   public String _align_mode;
   public String _pass;
   public String _seq_no;
   public String _length;
   public String _data_type;
   public String _must_input;
 
   public HiDictItem()
   {
     this._name = null;
 
     this._etf_name = null;
 
     this._type = null;
 
     this._deli_asc = null;
 
     this._head_len = null;
 
     this._len_type = null;
 
     this._include_len = null;
 
     this._necessary = null;
 
     this._align_mode = null;
 
     this._pass = null;
 
     this._seq_no = null;
 
     this._length = null;
 
     this._data_type = null;
 
     this._must_input = null; }
 
   public String getName() {
     return this._name;
   }
 
   public void setName(String name) {
     this._name = name.toUpperCase();
   }
 
   public String getEtf_name() {
     return this._etf_name;
   }
 
   public void setEtf_name(String name) {
     this._etf_name = name.toUpperCase();
   }
 
   public String getType() {
     return this._type;
   }
 
   public void setType(String type) {
     this._type = type;
   }
 
   public String getDeli_asc() {
     return this._deli_asc;
   }
 
   public void setDeli_asc(String deli_asc) {
     this._deli_asc = deli_asc;
   }
 
   public String getHead_len() {
     return this._head_len;
   }
 
   public void setHead_len(String head_len) {
     this._head_len = head_len;
   }
 
   public String getLen_type() {
     return this._len_type;
   }
 
   public void setLen_type(String len_type) {
     this._len_type = len_type;
   }
 
   public String getInclude_len() {
     return this._include_len;
   }
 
   public void setInclude_len(String include_len) {
     this._include_len = include_len;
   }
 
   public String getDeli_str() {
     return this._deli_str;
   }
 
   public void setDeli_str(String deli_str) {
     this._deli_str = deli_str;
   }
 
   public String getNecessary() {
     return this._necessary;
   }
 
   public void setNecessary(String necessary) {
     this._necessary = necessary;
   }
 
   public String getAlign_mode() {
     return this._align_mode;
   }
 
   public void setAlign_mode(String align_mode) {
     this._align_mode = align_mode;
   }
 
   public String getPass() {
     return this._pass;
   }
 
   public void setPass(String pass) {
     this._pass = pass;
   }
 
   public String getSeq_no() {
     return this._seq_no;
   }
 
   public void setSeq_no(String seq_no) {
     this._seq_no = seq_no;
   }
 
   public String getLength() {
     return this._length;
   }
 
   public void setLength(String length) {
     this._length = length;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
     if (this._name != null)
       result.append("[name=" + this._name + "];");
     if (this._etf_name != null)
       result.append("[etf_name=" + this._etf_name + "];");
     if (this._type != null)
       result.append("[type=" + this._type + "];");
     if (this._deli_asc != null)
       result.append("[deli_asc=" + this._deli_asc + "];");
     if (this._head_len != null)
       result.append("[head_len=" + this._head_len + "];");
     if (this._len_type != null)
       result.append("[len_type=" + this._len_type + "];");
     if (this._include_len != null)
       result.append("[include_len=" + this._include_len + "];");
     if (this._deli_str != null)
       result.append("[deli_str=" + this._include_len + "];");
     if (this._necessary != null)
       result.append("[necessary=" + this._necessary + "];");
     if (this._align_mode != null)
       result.append("[align_mode=" + this._align_mode + "];");
     if (this._pass != null)
       result.append("[pass=" + this._pass + "];");
     if (this._seq_no != null)
       result.append("[seq_no=" + this._seq_no + "];");
     if (this._length != null)
       result.append("[length=" + this._length + "];");
     if (this._must_input != null)
       result.append("[must_input=" + this._must_input + "];");
     if (this._data_type != null)
       result.append("[data_type=" + this._data_type + "];");
     return result.toString();
   }
 
   public String getData_type() {
     return this._data_type;
   }
 
   public void setData_type(String type) {
     this._data_type = type;
   }
 
   public String getMust_input() {
     return this._must_input;
   }
 
   public void setMust_input(String input) {
     this._must_input = input;
   }
 }