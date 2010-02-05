 package com.hisun.atc.bat;
 
 import com.hisun.exception.HiException;
 
 public class HiPackInfo
 {
   HiBatchFile batchFile;
   public String formatName;
   public String tableName;
   public String sqlCond;
   public String fileName;
   public String applyLogNoFlag;
   public boolean sumFlag;
   public boolean statFlag;
   public int totalCnt;
   public int ornCnt;
   public long ornAmt;
   public int dTotalCnt;
   public long totalAmt;
   public long dTotalAmt;
   public boolean recAmtFlg;
   public long logNo;
   public long seqNo;
   public boolean isUpdate;
   public String conSts;
 
   public HiPackInfo()
   {
     this.batchFile = null;
 
     this.sumFlag = false;
 
     this.statFlag = false;
 
     this.totalCnt = 0;
 
     this.ornCnt = 0;
 
     this.ornAmt = 0L;
 
     this.dTotalCnt = 0;
 
     this.totalAmt = 0L;
 
     this.dTotalAmt = 0L;
 
     this.recAmtFlg = false;
 
     this.logNo = -1L;
 
     this.seqNo = -1L;
 
     this.isUpdate = false;
   }
 
   public void check()
     throws HiException
   {
     if (!(this.statFlag)) {
       return;
     }
     if ((this.ornCnt != 0) && (this.ornCnt != this.dTotalCnt)) {
       throw new HiException("220200", this.batchFile.getFile(), String.valueOf(this.ornCnt), String.valueOf(this.dTotalCnt));
     }
 
     if ((this.recAmtFlg) && (this.ornAmt != 0L) && (this.dTotalAmt != this.ornAmt)) {
       throw new HiException("220201", this.batchFile.getFile(), String.valueOf(this.ornAmt), String.valueOf(this.dTotalAmt));
     }
 
     if (this.sumFlag) {
       if (this.totalCnt != this.dTotalCnt) {
         throw new HiException("220202", this.batchFile.getFile(), String.valueOf(this.totalCnt), String.valueOf(this.dTotalCnt));
       }
 
       if ((this.recAmtFlg) && (this.totalAmt != this.dTotalAmt))
         throw new HiException("220203", this.batchFile.getFile(), String.valueOf(this.totalAmt), String.valueOf(this.dTotalAmt));
     }
   }
 }