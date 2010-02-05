package com.hisun.register;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;

import java.io.Serializable;

public abstract interface HiBind extends Serializable {
    public abstract HiMessage process(HiMessage paramHiMessage) throws HiException;
}