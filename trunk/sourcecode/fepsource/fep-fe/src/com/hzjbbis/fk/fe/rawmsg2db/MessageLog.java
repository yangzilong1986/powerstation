package com.hzjbbis.fk.fe.rawmsg2db;

import java.util.Date;

public class MessageLog {
    private String logicAddress;
    private String qym;
    private String kzm;
    private String srcAddr;
    private String destAddr;
    private String txfs;
    private Date time;
    private int size;
    private String body;
    private String result;

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        if (body.length() >= 4000) body = body.substring(0, 3999);
        this.body = body;
    }

    public String getDestAddr() {
        return this.destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    public String getKzm() {
        return this.kzm;
    }

    public void setKzm(String kzm) {
        this.kzm = kzm;
    }

    public String getLogicAddress() {
        return this.logicAddress;
    }

    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    public String getQym() {
        return this.qym;
    }

    public void setQym(String qym) {
        this.qym = qym;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSrcAddr() {
        return this.srcAddr;
    }

    public void setSrcAddr(String srcAddr) {
        this.srcAddr = srcAddr;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public String toString() {
        return "rtua=" + this.logicAddress + ",message=" + this.body;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}