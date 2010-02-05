package com.hisun.pubinterface;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;

public abstract interface IHandler {
    public abstract void process(HiMessageContext paramHiMessageContext) throws HiException;
}