package com.hisun.hilog4j;

public abstract interface IFileName {
    public abstract String get();

    public abstract int getLineLength();

    public abstract void setLineLength(int paramInt);

    public abstract String name();

    public abstract boolean isFixedSizeable();

    public abstract void setFixedSizeable(boolean paramBoolean);
}