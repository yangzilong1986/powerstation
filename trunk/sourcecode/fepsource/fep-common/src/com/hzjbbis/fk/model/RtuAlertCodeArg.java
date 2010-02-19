package com.hzjbbis.fk.model;

public class RtuAlertCodeArg {
    private String code;
    private String sjx;
    private int xh = 0;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSjx() {
        return this.sjx;
    }

    public void setSjx(String sjx) {
        this.sjx = sjx;
    }

    public int getXh() {
        return this.xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }
}