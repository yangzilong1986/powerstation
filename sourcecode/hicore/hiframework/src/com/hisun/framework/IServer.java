package com.hisun.framework;

import com.hisun.exception.HiException;
import com.hisun.pubinterface.IHandler;

public abstract interface IServer extends IHandler {
    public abstract void init() throws HiException;

    public abstract void destroy();

    public abstract void start() throws HiException;

    public abstract void stop() throws HiException;

    public abstract void pause();

    public abstract void resume();
}