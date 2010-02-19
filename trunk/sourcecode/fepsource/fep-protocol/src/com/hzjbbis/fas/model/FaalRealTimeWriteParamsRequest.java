package com.hzjbbis.fas.model;

import java.util.Calendar;

public class FaalRealTimeWriteParamsRequest extends FaalWriteParamsRequest {
    private static final long serialVersionUID = 2234597531372736773L;
    private Calendar cmdTime;
    private int timeout;

    public FaalRealTimeWriteParamsRequest() {
        this.type = 7;
    }

    public Calendar getCmdTime() {
        return this.cmdTime;
    }

    public void setCmdTime(Calendar cmdTime) {
        this.cmdTime = cmdTime;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}