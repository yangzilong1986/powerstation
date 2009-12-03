package com.hisun.cnaps.tags;

public abstract interface HiCnapsSplitableTag
{
  public static final int SPLIT_FIEXED_MODE = 1;
  public static final int SPLIT_GROUP_MODE = 2;

  public abstract int getSplitedMode();

  public abstract HiCnapsTag getSubTagbyIndex(int paramInt);

  public abstract String getSubTagRepeatName();

  public abstract int getSubTagCount();
}