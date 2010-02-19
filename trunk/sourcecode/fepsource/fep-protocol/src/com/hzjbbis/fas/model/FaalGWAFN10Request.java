package com.hzjbbis.fas.model;

public class FaalGWAFN10Request extends FaalRequest {
    private static final long serialVersionUID = -462982183058685616L;
    private String tpSendTime;
    private int tpTimeout;
    private int port;
    private String kzz;
    private String msgTimeout;
    private int byteTimeout;
    private String fixProto;
    private String fixAddre;
    private boolean broadcast = false;
    private String broadcastAddress;

    public String getTpSendTime() {
        return this.tpSendTime;
    }

    public void setTpSendTime(String tpSendTime) {
        this.tpSendTime = tpSendTime;
    }

    public int getTpTimeout() {
        return this.tpTimeout;
    }

    public void setTpTimeout(int tpTimeout) {
        this.tpTimeout = tpTimeout;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getKzz() {
        return this.kzz;
    }

    public void setKzz(String kzz) {
        this.kzz = kzz;
    }

    public String getMsgTimeout() {
        return this.msgTimeout;
    }

    public void setMsgTimeout(String msgTimeout) {
        this.msgTimeout = msgTimeout;
    }

    public int getByteTimeout() {
        return this.byteTimeout;
    }

    public void setByteTimeout(int byteTimeout) {
        this.byteTimeout = byteTimeout;
    }

    public String getFixProto() {
        return this.fixProto;
    }

    public void setFixProto(String fixProto) {
        this.fixProto = fixProto;
    }

    public String getFixAddre() {
        return this.fixAddre;
    }

    public void setFixAddre(String fixAddre) {
        this.fixAddre = fixAddre;
    }

    public String getBroadcastAddress() {
        return this.broadcastAddress;
    }

    public void setBroadcastAddress(String broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }

    public boolean getBroadcast() {
        return this.broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }
}