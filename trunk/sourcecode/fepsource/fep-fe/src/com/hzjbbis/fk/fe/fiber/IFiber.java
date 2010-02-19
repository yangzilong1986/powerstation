package com.hzjbbis.fk.fe.fiber;

public abstract interface IFiber {
    public abstract void runOnce();

    public abstract void setFiber(boolean paramBoolean);

    public abstract boolean isFiber();
}