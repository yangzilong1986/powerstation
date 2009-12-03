/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataFile;
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.xml.Located;
/*    */ 
/*    */ public abstract class HiSubTotalNode extends Located
/*    */ {
/*    */   protected String[] groupName;
/*    */   private int groupNum;
/*    */ 
/*    */   public abstract HiDataFile process(HiRptContext paramHiRptContext, HiDataFile paramHiDataFile1, HiDataFile paramHiDataFile2);
/*    */ 
/*    */   public void setGroupby(String groups)
/*    */   {
/* 20 */     this.groupName = groups.split("\\|");
/* 21 */     this.groupNum = this.groupName.length; }
/*    */ 
/*    */   protected int getGroupNum() {
/* 24 */     return this.groupNum;
/*    */   }
/*    */ }