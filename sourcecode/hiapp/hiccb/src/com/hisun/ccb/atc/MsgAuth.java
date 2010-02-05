package com.hisun.ccb.atc;

public class MsgAuth extends MsgBase {
    private int auth_level1;
    private int auth_level2;

    public MsgAuth(String appMmo, String msgCode, String msgInfo) {
        super(appMmo, msgCode, msgInfo);
    }

    public int getAuth_level1() {
        return this.auth_level1;
    }

    public void setAuth_level1(int auth_level1) {
        this.auth_level1 = auth_level1;
    }

    public int getAuth_level2() {
        return this.auth_level2;
    }

    public void setAuth_level2(int auth_level2) {
        this.auth_level2 = auth_level2;
    }

    public String getMsg() {
        return null;
    }
}