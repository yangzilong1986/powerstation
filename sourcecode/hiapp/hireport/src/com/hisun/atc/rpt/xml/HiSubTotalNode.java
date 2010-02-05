 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.xml.Located;
 
 public abstract class HiSubTotalNode extends Located
 {
   protected String[] groupName;
   private int groupNum;
 
   public abstract HiDataFile process(HiRptContext paramHiRptContext, HiDataFile paramHiDataFile1, HiDataFile paramHiDataFile2);
 
   public void setGroupby(String groups)
   {
     this.groupName = groups.split("\\|");
     this.groupNum = this.groupName.length; }
 
   protected int getGroupNum() {
     return this.groupNum;
   }
 }