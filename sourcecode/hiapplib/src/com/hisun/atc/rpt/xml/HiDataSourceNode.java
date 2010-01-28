 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.xml.Located;
 
 public abstract class HiDataSourceNode extends Located
   implements HiReportConstants
 {
   protected HiSubTotalNode subtotal;
   protected HiSummedNode summed;
 
   public void setSubTotalNode(HiSubTotalNode sub)
   {
     this.subtotal = sub;
   }
 
   public void setSummedNode(HiSummedNode sum) {
     this.summed = sum;
   }
 
   public abstract HiDataFile process(HiRptContext paramHiRptContext);
 }