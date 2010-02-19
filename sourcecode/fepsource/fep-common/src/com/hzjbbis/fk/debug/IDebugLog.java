package com.hzjbbis.fk.debug;

import org.apache.log4j.Logger;

public abstract interface IDebugLog {
    public abstract void setName(String paramString);

    public abstract Logger getLogger();
}