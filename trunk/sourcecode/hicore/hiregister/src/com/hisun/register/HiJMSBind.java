package com.hisun.register;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;

import java.io.Serializable;

public class HiJMSBind implements HiBind, Serializable {
    private static final long serialVersionUID = -8942301155780349161L;

    public HiMessage process(HiMessage message) throws HiException {
        return null;
    }
}