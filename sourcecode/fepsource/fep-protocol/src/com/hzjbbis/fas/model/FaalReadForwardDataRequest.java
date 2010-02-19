package com.hzjbbis.fas.model;

public class FaalReadForwardDataRequest extends FaalRequest {
    private static final long serialVersionUID = -6840264166731727088L;
    private String tn;
    private int timeout;
    private boolean broadcast = false;
    private String broadcastAddress;
    private String fixProto;
    private String fixAddre;
    private String fixPort;

    public FaalReadForwardDataRequest() {
        this.type = 0;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isBroadcast() {
        return this.broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public String getBroadcastAddress() {
        return this.broadcastAddress;
    }

    public void setBroadcastAddress(String broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }

    public String getFixAddre() {
        return this.fixAddre;
    }

    public String getFixProto() {
        return this.fixProto;
    }

    public void setFixAddre(String fixAddre) {
        this.fixAddre = fixAddre;
    }

    public void setFixProto(String fixProto) {
        this.fixProto = fixProto;
    }

    public String getFixPort() {
        return this.fixPort;
    }

    public void setFixPort(String fixPort) {
        this.fixPort = fixPort;
    }
}