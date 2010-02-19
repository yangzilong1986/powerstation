package com.hzjbbis.fk.model;

public class RtuSynchronizeItem {
    private String rtuId;
    private int SycType;
    private String SycTime;

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public int getSycType() {
        return this.SycType;
    }

    public void setSycType(int sycType) {
        this.SycType = sycType;
    }

    public String getSycTime() {
        return this.SycTime;
    }

    public void setSycTime(String sycTime) {
        this.SycTime = sycTime;
    }
}