package com.hzjbbis.fk.fe.filecache;

public class MisparamRtu {
    private int rtua;
    private String gprsActiveCommAddr;
    private String smsActiveCommAddr;
    private long lastUpdate;
    private char state = '0';

    public int getRtua() {
        return this.rtua;
    }

    public void setRtua(int rtua) {
        this.rtua = rtua;
    }

    public String getGprsActiveCommAddr() {
        return this.gprsActiveCommAddr;
    }

    public void setGprsActiveCommAddr(String gprsActiveCommAddr) {
        this.gprsActiveCommAddr = gprsActiveCommAddr;
        if (this.gprsActiveCommAddr == null) this.lastUpdate = 0L;
    }

    public String getSmsActiveCommAddr() {
        return this.smsActiveCommAddr;
    }

    public void setSmsActiveCommAddr(String smsActiveCommAddr) {
        this.smsActiveCommAddr = smsActiveCommAddr;
        if (this.smsActiveCommAddr == null) this.lastUpdate = 0L;
    }

    public void setLastUpdate() {
        this.lastUpdate = System.currentTimeMillis();
    }

    public void setLastUpdate(long tm) {
        this.lastUpdate = tm;
    }

    public long getLastUpdate() {
        return this.lastUpdate;
    }

    public char getState() {
        return this.state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public boolean isDirty() {
        return ((this.gprsActiveCommAddr == null) && (this.smsActiveCommAddr == null));
    }
}