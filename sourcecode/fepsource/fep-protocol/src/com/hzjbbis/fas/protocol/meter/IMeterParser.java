package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.data.DataItem;

public abstract interface IMeterParser {
    public abstract String[] convertDataKey(String[] paramArrayOfString);

    public abstract byte[] constructor(String[] paramArrayOfString, DataItem paramDataItem);

    public abstract Object[] parser(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

    public abstract String[] getMeterCode(String[] paramArrayOfString);
}