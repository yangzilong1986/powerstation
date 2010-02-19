package com.hzjbbis.fk.model;

public class RtuCmdItem {
    private long cmdId;
    private int bwsl;
    private int zdzjbz;

    public int getBwsl() {
        return this.bwsl;
    }

    public void setBwsl(int bwsl) {
        this.bwsl = bwsl;
    }

    public long getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(long cmdId) {
        this.cmdId = cmdId;
    }

    public int getZdzjbz() {
        return this.zdzjbz;
    }

    public void setZdzjbz(int zdzjbz) {
        this.zdzjbz = zdzjbz;
    }
}