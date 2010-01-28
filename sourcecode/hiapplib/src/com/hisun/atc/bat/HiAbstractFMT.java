 package com.hisun.atc.bat;
 
 import com.hisun.engine.HiITFEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiAbstractFMT extends HiITFEngineModel
 {
   private HiContext context = HiContext.getCurrentContext();
 
   private boolean line_wrap = true;
 
   private int max_length = -1;
   private String pro_dll;
   private String pro_func;
   private int record_length = -1;
 
   public HiAbstractFMT()
   {
     super.setItemAttribute(this.context);
   }
 
   public String getNodeName()
   {
     return "End";
   }
 
   public void Pro_func(String pro_func)
   {
     this.pro_func = pro_func;
   }
 
   public void setLine_wrap(String line_wrap)
   {
     if (StringUtils.equalsIgnoreCase(line_wrap, "N"))
       this.line_wrap = false;
   }
 
   public boolean isLineWrap() {
     return this.line_wrap;
   }
 
   public void setMax_length(int max_length) {
     this.max_length = max_length;
   }
 
   public void setPro_dll(String pro_dll)
   {
     this.pro_dll = pro_dll;
   }
 
   public void setRecord_length(int record_length)
     throws HiException
   {
     this.record_length = record_length;
   }
 
   public int getRecordLength()
   {
     return this.record_length;
   }
 
   public int getMaxLength()
   {
     return this.max_length;
   }
 
   public void loadAfter() throws HiException
   {
     if (this.record_length == -1)
       this.record_length = this.max_length;
   }
 
   public String toString()
   {
     StringBuffer result = new StringBuffer();
     result.append("line_wrap:" + this.line_wrap);
     result.append(";max_length:" + this.max_length);
     result.append(";pro_dll:" + this.pro_dll);
     result.append(";pro_func:" + this.pro_func);
     result.append(";record_length:" + this.record_length);
     return result.toString();
   }
 }