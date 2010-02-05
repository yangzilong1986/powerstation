package com.hisun.register;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;

import java.io.Serializable;

public class HiTCPBind implements HiBind, Serializable {
    String ip;
    int port;
    private static final long serialVersionUID = 3378369309783844267L;

    public HiMessage process(HiMessage msg) throws HiException {
        return null;
    }

    public String toString() {
        return this.ip + this.port;
    }
}