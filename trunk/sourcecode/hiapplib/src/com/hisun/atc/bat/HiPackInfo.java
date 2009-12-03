/*    */ package com.hisun.atc.bat;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ 
/*    */ public class HiPackInfo
/*    */ {
/*    */   HiBatchFile batchFile;
/*    */   public String formatName;
/*    */   public String tableName;
/*    */   public String sqlCond;
/*    */   public String fileName;
/*    */   public String applyLogNoFlag;
/*    */   public boolean sumFlag;
/*    */   public boolean statFlag;
/*    */   public int totalCnt;
/*    */   public int ornCnt;
/*    */   public long ornAmt;
/*    */   public int dTotalCnt;
/*    */   public long totalAmt;
/*    */   public long dTotalAmt;
/*    */   public boolean recAmtFlg;
/*    */   public long logNo;
/*    */   public long seqNo;
/*    */   public boolean isUpdate;
/*    */   public String conSts;
/*    */ 
/*    */   public HiPackInfo()
/*    */   {
/*  7 */     this.batchFile = null;
/*    */ 
/* 20 */     this.sumFlag = false;
/*    */ 
/* 25 */     this.statFlag = false;
/*    */ 
/* 30 */     this.totalCnt = 0;
/*    */ 
/* 35 */     this.ornCnt = 0;
/*    */ 
/* 40 */     this.ornAmt = 0L;
/*    */ 
/* 45 */     this.dTotalCnt = 0;
/*    */ 
/* 50 */     this.totalAmt = 0L;
/*    */ 
/* 55 */     this.dTotalAmt = 0L;
/*    */ 
/* 57 */     this.recAmtFlg = false;
/*    */ 
/* 59 */     this.logNo = -1L;
/*    */ 
/* 61 */     this.seqNo = -1L;
/*    */ 
/* 66 */     this.isUpdate = false;
/*    */   }
/*    */ 
/*    */   public void check()
/*    */     throws HiException
/*    */   {
/* 73 */     if (!(this.statFlag)) {
/* 74 */       return;
/*    */     }
/* 76 */     if ((this.ornCnt != 0) && (this.ornCnt != this.dTotalCnt)) {
/* 77 */       throw new HiException("220200", this.batchFile.getFile(), String.valueOf(this.ornCnt), String.valueOf(this.dTotalCnt));
/*    */     }
/*    */ 
/* 82 */     if ((this.recAmtFlg) && (this.ornAmt != 0L) && (this.dTotalAmt != this.ornAmt)) {
/* 83 */       throw new HiException("220201", this.batchFile.getFile(), String.valueOf(this.ornAmt), String.valueOf(this.dTotalAmt));
/*    */     }
/*    */ 
/* 88 */     if (this.sumFlag) {
/* 89 */       if (this.totalCnt != this.dTotalCnt) {
/* 90 */         throw new HiException("220202", this.batchFile.getFile(), String.valueOf(this.totalCnt), String.valueOf(this.dTotalCnt));
/*    */       }
/*    */ 
/* 95 */       if ((this.recAmtFlg) && (this.totalAmt != this.dTotalAmt))
/* 96 */         throw new HiException("220203", this.batchFile.getFile(), String.valueOf(this.totalAmt), String.valueOf(this.dTotalAmt));
/*    */     }
/*    */   }
/*    */ }