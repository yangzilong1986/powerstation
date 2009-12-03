package com.hisun.cnaps;

public abstract interface CnapsMessageArea
{
  public static final int BODY = 3;
  public static final int HEAD = 1;
  public static final int BUS = 2;
  public static final String BODY_AREA_MARK = "{3:";
  public static final String HEAD_AREA_MARK = "{1:";
  public static final String BUSHEAD_AREA_MARK = "{2:";
  public static final String BATCH_AREA_MARK = "{B:";
  public static final String AREA_END_MARK = "}";

  public abstract int getTagCount();

  public abstract CnapsTag getTagByIndex(int paramInt);

  public abstract int getLength();
}