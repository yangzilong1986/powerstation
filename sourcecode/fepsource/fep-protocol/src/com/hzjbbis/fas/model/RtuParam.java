package com.hzjbbis.fas.model;

import java.util.Date;

public class RtuParam {
    private String rtuid;
    private Date opttime;
    private int curve;

    public int getCurve() {
        return this.curve;
    }

    public Date getOpttime() {
        return this.opttime;
    }

    public String getRtuid() {
        return this.rtuid;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public void setRtuid(String rtuid) {
        this.rtuid = rtuid;
    }
}