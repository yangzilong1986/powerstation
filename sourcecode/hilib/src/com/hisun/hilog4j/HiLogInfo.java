package com.hisun.hilog4j;

public class HiLogInfo {
    private StringBuilder buf;
    private IFileName name;

    HiLogInfo(IFileName name, StringBuilder buf) {

        this.buf = buf;

        this.name = name;
    }

    public StringBuilder getBuf() {

        return this.buf;
    }

    public void setBuf(StringBuilder buf) {

        this.buf = buf;
    }

    public IFileName getName() {

        return this.name;
    }

    public void setName(IFileName name) {

        this.name = name;
    }
}