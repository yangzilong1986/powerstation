package com.hzjbbis.fas.protocol.conf;

public abstract interface IDataSets {
    public abstract IItemParser getParser(String paramString);

    public abstract String getLocal(String paramString, Object paramObject);
}