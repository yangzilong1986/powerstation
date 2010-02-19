package com.hzjbbis.fas.model;

public class FaalGWAFN0ARequest extends FaalRequest {
    private static final long serialVersionUID = -362982183058685616L;
    private String tpSendTime;
    private int tpTimeout;
    private int[] param;

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

    public int[] getParam() {
        return this.param;
    }

    public void setParam(int[] param) {
        this.param = param;
    }
}