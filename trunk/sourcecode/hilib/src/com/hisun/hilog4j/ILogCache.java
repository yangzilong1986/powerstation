package com.hisun.hilog4j;

import java.io.IOException;

public abstract interface ILogCache {
    public abstract void put(HiLogInfo paramHiLogInfo) throws IOException;

    public abstract void clear();

    public abstract void flush() throws IOException;

    public abstract void close() throws IOException;
}