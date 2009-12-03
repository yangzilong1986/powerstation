/*    */ package com.hisun.atc.bat;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiData extends HiAbstractFMT
/*    */ {
/* 14 */   private boolean _extend_flag = false;
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 23 */     return "数据明细记录:Data";
/*    */   }
/*    */ 
/*    */   public void setExtend_flag(String extend_flag) throws HiException
/*    */   {
/* 28 */     if (StringUtils.equalsIgnoreCase(extend_flag, "Y"))
/* 29 */       this._extend_flag = true;
/*    */   }
/*    */ 
/*    */   public boolean isExtendFlag()
/*    */   {
/* 34 */     return this._extend_flag;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 38 */     StringBuffer result = new StringBuffer();
/* 39 */     result.append(super.toString());
/* 40 */     result.append(";_extend_flag:" + this._extend_flag);
/* 41 */     return result.toString();
/*    */   }
/*    */ }