package com.hisun.protocol.tcp;

import com.hisun.util.HiByteBuffer;

public abstract interface ICheckConn
{
  public abstract boolean isCheckData(HiByteBuffer paramHiByteBuffer);

  public abstract boolean isCheckData(byte[] paramArrayOfByte);

  public abstract String getCheckData();

  public abstract HiByteBuffer getRspCheckData(HiByteBuffer paramHiByteBuffer);
}