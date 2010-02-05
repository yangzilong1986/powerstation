package com.hisun.ccb.atc;

public abstract class MsgBase {
    protected String appMmo;
    protected String msgCode;
    protected String msgInfo;

    public MsgBase(String appMmo, String msgCode, String msgInfo) {
        this.appMmo = appMmo;
        this.msgCode = msgCode;
        this.msgInfo = msgInfo;
    }

    public String getAppMmo() {
        return this.appMmo;
    }

    public void setAppMmo(String appMmo) {
        this.appMmo = appMmo;
    }

    public String getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgInfo() {
        return this.msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public abstract String getMsg();
}