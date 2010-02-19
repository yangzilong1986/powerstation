package com.hzjbbis.fas.framework.spi;

import java.io.Serializable;

public abstract interface IStringSerializable extends Serializable {
    public abstract String serialzeToString() throws Exception;

    public abstract IStringSerializable deserializeFromString(String paramString) throws Exception;
}