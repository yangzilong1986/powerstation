package com.hzjbbis.fas.model;

public class FaalGWAFN0DRequest extends FaalRequest {
    private static final long serialVersionUID = -262982183058685616L;
    private String tpSendTime;
    private int tpTimeout;
    private String startTime;
    private int interval;
    private int count;

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

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}