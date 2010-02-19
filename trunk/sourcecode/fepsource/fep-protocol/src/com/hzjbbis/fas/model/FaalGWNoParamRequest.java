package com.hzjbbis.fas.model;

public class FaalGWNoParamRequest extends FaalRequest {
    private static final long serialVersionUID = -162982183058685616L;
    private String tpSendTime;
    private int tpTimeout;

    public String getTpSendTime() {
        return this.tpSendTime;
    }

    public void setTpSendTime(String tpSendTime) {
        this.tpSendTime = tpSendTime;
    }

    public int getTpTimeout() {
        return this.tpTimeout;
    }

    public void setTpTimeout(int tpTimeout) {
        this.tpTimeout = tpTimeout;
    }
}