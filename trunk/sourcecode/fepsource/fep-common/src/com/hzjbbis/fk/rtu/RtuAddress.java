package com.hzjbbis.fk.rtu;

public class RtuAddress {
    private String peerAddr;

    public RtuAddress(String peerAddr) {
        this.peerAddr = peerAddr;
    }

    public String getIp() {
        int i = this.peerAddr.indexOf(58);
        if (i > 0) {
            return this.peerAddr.substring(0, i);
        }
        return "";
    }

    public int getPort() {
        int i = this.peerAddr.indexOf(58);
        if (i > 0) {
            return Integer.parseInt(this.peerAddr.substring(i + 1));
        }
        return 0;
    }

    public String toString() {
        return this.peerAddr;
    }

    public String getPeerAddr() {
        return this.peerAddr;
    }

    public void setPeerAddr(String peerAddr) {
        this.peerAddr = peerAddr;
    }
}