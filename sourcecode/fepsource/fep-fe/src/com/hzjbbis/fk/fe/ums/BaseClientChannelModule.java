package com.hzjbbis.fk.fe.ums;

import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.common.spi.socket.abstra.BaseClientChannel;

import java.net.SocketAddress;

public abstract class BaseClientChannelModule extends BaseClientChannel implements IModule {
    protected String peerIp;
    protected int peerPort = 0;
    protected String txfs = "01";
    protected String name = "UMS通道";

    protected long lastReceiveTime = 0L;
    protected long lastSendTime = 0L;
    protected long totalRecvMessages = 0L;
    protected long totalSendMessages = 0L;
    protected int msgRecvPerMinute = 0;
    protected int msgSendPerMinute = 0;
    protected String moduleType = "umsClient";

    public String getPeerAddr() {
        return this.peerIp + ":" + this.peerPort;
    }

    public String getPeerIp() {
        return this.peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public void setHostIp(String hostIp) {
        this.peerIp = hostIp;
    }

    public int getPeerPort() {
        return this.peerPort;
    }

    public void setPeerPort(int peerPort) {
        this.peerPort = peerPort;
    }

    public void setHostPort(int hostPort) {
        this.peerPort = hostPort;
    }

    public SocketAddress getSocketAddress() {
        return null;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(String type) {
        this.moduleType = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public long getLastReceiveTime() {
        return this.lastReceiveTime;
    }

    public long getLastSendTime() {
        return this.lastSendTime;
    }

    public int getMsgRecvPerMinute() {
        return this.msgRecvPerMinute;
    }

    public int getMsgSendPerMinute() {
        return this.msgSendPerMinute;
    }

    public long getTotalRecvMessages() {
        return this.totalRecvMessages;
    }

    public long getTotalSendMessages() {
        return this.totalSendMessages;
    }

    public String profile() {
        return "";
    }
}