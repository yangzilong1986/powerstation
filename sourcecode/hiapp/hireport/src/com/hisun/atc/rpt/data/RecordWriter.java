package com.hisun.atc.rpt.data;

import com.hisun.atc.rpt.HiDataRecord;

public abstract interface RecordWriter
{
  public abstract void appendRecord(HiDataRecord paramHiDataRecord);

  public abstract void appendRecordValue(String paramString1, String paramString2);

  public abstract void appendSeq(int paramInt);

  public abstract int newRecord(int paramInt);

  public abstract void close();
}