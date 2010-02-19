package com.hzjbbis.fas.protocol.conf;

import java.util.List;

public abstract interface IItemParser {
    public abstract int parse(byte[] paramArrayOfByte, int paramInt, Object paramObject, Long paramLong, List paramList);

    public abstract int construct(byte[] paramArrayOfByte, int paramInt1, Object paramObject, int paramInt2);
}