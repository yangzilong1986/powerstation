 package com.hisun.atc.bat;
 
 import com.hisun.exception.HiException;
 import org.apache.commons.lang.StringUtils;
 
 public class HiData extends HiAbstractFMT
 {
   private boolean _extend_flag = false;
 
   public String getNodeName()
   {
     return "数据明细记录:Data";
   }
 
   public void setExtend_flag(String extend_flag) throws HiException
   {
     if (StringUtils.equalsIgnoreCase(extend_flag, "Y"))
       this._extend_flag = true;
   }
 
   public boolean isExtendFlag()
   {
     return this._extend_flag;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
     result.append(super.toString());
     result.append(";_extend_flag:" + this._extend_flag);
     return result.toString();
   }
 }