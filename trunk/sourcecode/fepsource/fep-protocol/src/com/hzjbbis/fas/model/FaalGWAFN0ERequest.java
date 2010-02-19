package com.hzjbbis.fas.model;

public class FaalGWAFN0ERequest extends FaalRequest {
    private static final long serialVersionUID = -562982183058685616L;
    private String tpSendTime;
    private int tpTimeout;
    private int pm;
    private int pn;

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

    public int getPm() {
        return this.pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getPn() {
        return this.pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }
}