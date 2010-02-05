package com.hisun.mon;

import java.util.HashMap;

public class HiRunTimeInfo {
    String msgId;
    String id;
    long elapseTm;
    long sysTm;
    String msgTyp;
    String msgCod;
    String msg;
    HashMap extMap;

    public HiRunTimeInfo(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg) {
        this.msgId = msgId;
        this.id = id;
        this.elapseTm = elapseTm;
        this.sysTm = sysTm;
        this.msgTyp = msgTyp;
        this.msgCod = msgCod;
        this.msg = msg;
    }

    public HiRunTimeInfo(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg, HashMap extMap) {
        this.msgId = msgId;
        this.id = id;
        this.elapseTm = elapseTm;
        this.sysTm = sysTm;
        this.msgTyp = msgTyp;
        this.msgCod = msgCod;
        this.msg = msg;
        this.extMap = extMap;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getElapseTm() {
        return this.elapseTm;
    }

    public void setElapseTm(long elapseTm) {
        this.elapseTm = elapseTm;
    }

    public long getSysTm() {
        return this.sysTm;
    }

    public void setSysTm(long sysTm) {
        this.sysTm = sysTm;
    }

    public String getMsgTyp() {
        return this.msgTyp;
    }

    public void setMsgTyp(String msgTyp) {
        this.msgTyp = msgTyp;
    }

    public String getMsgCod() {
        return this.msgCod;
    }

    public void setMsgCod(String msgCod) {
        this.msgCod = msgCod;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap getExtMap() {
        return this.extMap;
    }

    public void setExtMap(HashMap extMap) {
        this.extMap = extMap;
    }
}