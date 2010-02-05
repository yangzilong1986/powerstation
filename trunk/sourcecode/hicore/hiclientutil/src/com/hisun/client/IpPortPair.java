package com.hisun.client;

class IpPortPair {
    int port;
    String ip;
    int tmOut = 30;
    int sslMode = 0;
    String identityFile = "";
    String trustFile = "";
    String keyPsw = "";
    String trustPsw = "";
    boolean logSwitch = false;

    boolean isSRNConn = false;

    public IpPortPair() {
    }

    public IpPortPair(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public IpPortPair(String ip, int port, boolean isSRNConn) {
        this.ip = ip;
        this.port = port;
        this.isSRNConn = isSRNConn;
    }

    public IpPortPair(String ip, int port, int sslMode, String identityFile, String trustFile, String keyPsw, String trustPsw) {
        this.ip = ip;
        this.port = port;
        this.sslMode = sslMode;
        this.identityFile = identityFile;
        this.trustFile = trustFile;
        this.keyPsw = keyPsw;
        this.trustPsw = trustPsw;
    }

    public IpPortPair(String ip, int port, int sslMode, String identityFile, String trustFile, String keyPsw, String trustPsw, boolean isSRNConn) {
        this.ip = ip;
        this.port = port;
        this.sslMode = sslMode;
        this.identityFile = identityFile;
        this.trustFile = trustFile;
        this.keyPsw = keyPsw;
        this.trustPsw = trustPsw;
        this.isSRNConn = isSRNConn;
    }

    public boolean isSRNConn() {
        return this.isSRNConn;
    }

    public void setSRNConn(boolean isSRNConn) {
        this.isSRNConn = isSRNConn;
    }

    public int getTmOut() {
        return this.tmOut;
    }

    public void setTmOut(int tmOut) {
        this.tmOut = tmOut;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSslMode() {
        return this.sslMode;
    }

    public void setSslMode(int sslMode) {
        this.sslMode = sslMode;
    }

    public String getIdentityFile() {
        return this.identityFile;
    }

    public void setIdentityFile(String identityFile) {
        this.identityFile = identityFile;
    }

    public String getTrustFile() {
        return this.trustFile;
    }

    public void setTrustFile(String trustFile) {
        this.trustFile = trustFile;
    }

    public String getKeyPsw() {
        return this.keyPsw;
    }

    public void setKeyPsw(String keyPsw) {
        this.keyPsw = keyPsw;
    }

    public String getTrustPsw() {
        return this.trustPsw;
    }

    public void setTrustPsw(String trustPsw) {
        this.trustPsw = trustPsw;
    }

    public boolean isLogSwitch() {
        return this.logSwitch;
    }

    public void setLogSwitch(boolean logSwitch) {
        this.logSwitch = logSwitch;
    }
}