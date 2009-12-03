package com.hisun.atc.rpt.data;

import com.hisun.atc.rpt.HiDataRecord;

public abstract interface RecordReader
{
  public abstract HiDataRecord readRecord();

  public abstract void close();
}