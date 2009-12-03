package com.hisun.atc.rpt.xml;

import com.hisun.atc.rpt.HiDataFile;
import com.hisun.atc.rpt.HiRptContext;
import com.hisun.xml.Located;

public abstract class HiSummedNode extends Located
{
  public abstract HiDataFile process(HiRptContext paramHiRptContext, HiDataFile paramHiDataFile1, HiDataFile paramHiDataFile2);
}