package com.hzjbbis.fk.common.spi.socket.abstra;

import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.message.MultiProtoRecognizer;
import com.hzjbbis.fk.message.gate.MessageGateCreator;
import com.hzjbbis.fk.utils.CalendarUtil;

import java.nio.ByteBuffer;

public abstract class BaseSocketServer implements ISocketServer {
    protected String name = "async tcp server";
    protected String ip = null;
    protected int port = -1;
    protected int bufLength = 256;
    protected int ioThreadSize = 2;
    protected IMessageCreator messageCreator = new MessageGateCreator();

    protected IClientIO ioHandler = null;

    protected String txfs = "02";
    protected int timeout = 1800;

    private int writeFirstCount = 100;
    private int maxContinueRead = 100;
    private String serverAddress = null;

    protected long lastReceiveTime = 0L;
    protected long lastSendTime = 0L;
    protected long totalRecvMessages = 0L;
    protected long totalSendMessages = 0L;
    protected int msgRecvPerMinute = 0;
    protected int msgSendPerMinute = 0;
    protected Object statisticsRecv = new Object();
    protected Object statisticsSend = new Object();

    public IMessage createMessage(ByteBuffer buf) {
        IMessage msg = this.messageCreator.create();
        if (msg == null) msg = MultiProtoRecognizer.recognize(buf);
        return msg;
    }

    public int getBufLength() {
        return this.bufLength;
    }

    public void setBufLength(int bufLen) {
        this.bufLength = bufLen;
    }

    public abstract int getClientSize();

    public abstract IServerSideChannel[] getClients();

    public IClientIO getIoHandler() {
        return this.ioHandler;
    }

    public void setIoHandler(IClientIO ioh) {
        this.ioHandler = ioh;
    }

    public int getIoThreadSize() {
        return this.ioThreadSize;
    }

    public void setIoThreadSize(int iotSize) {
        this.ioThreadSize = iotSize;
    }

    public int getMaxContinueRead() {
        return this.maxContinueRead;
    }

    public void setMaxContinueRead(int mcRead) {
        this.maxContinueRead = mcRead;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerAddress() {
        if (this.serverAddress != null) {
            return this.serverAddress;
        }
        return "127.0.0.1:" + this.port;
    }

    public int getWriteFirstCount() {
        return this.writeFirstCount;
    }

    public void setWriteFirstCount(int fcount) {
        this.writeFirstCount = fcount;
    }

    public void incRecvMessage() {
        synchronized (this.statisticsRecv) {
            this.msgRecvPerMinute += 1;
            this.totalRecvMessages += 1L;
        }
    }

    public void incSendMessage() {
        synchronized (this.statisticsSend) {
            this.msgSendPerMinute += 1;
            this.totalSendMessages += 1L;
        }
    }

    public void removeClient(IServerSideChannel client) {
        if (getClientSize() == 0) {
            synchronized (this.statisticsRecv) {
                this.totalRecvMessages = 0L;
                this.msgRecvPerMinute = 0;
            }
            synchronized (this.statisticsSend) {
                this.totalSendMessages = 0L;
                this.msgSendPerMinute = 0;
            }
        }
    }

    public void setLastReceiveTime(long lastRecv) {
        this.lastReceiveTime = lastRecv;
    }

    public void setLastSendTime(long lastSend) {
        this.lastSendTime = lastSend;
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

    public String getModuleType() {
        return null;
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

    public abstract boolean isActive();

    public abstract boolean start();

    public abstract void stop();

    public String profile() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("\r\n<sockserver-profile type=\"").append(getModuleType()).append("\">");
        sb.append("\r\n    ").append("<name>").append(this.name).append("</name>");
        String stateDesc = (isActive()) ? "running" : "stopped";
        sb.append("\r\n    ").append("<state>").append(stateDesc).append("</state>");
        sb.append("\r\n    ").append("<port>").append(this.port).append("</port>");

        sb.append("\r\n    ").append("<ioThreadSize>").append(this.ioThreadSize).append("</ioThreadSize>");
        sb.append("\r\n    ").append("<clientSize>").append(getClientSize()).append("</clientSize>");
        sb.append("\r\n    ").append("<timeout>").append(this.timeout).append("</timeout>");

        sb.append("\r\n    ").append("<txfs>").append(this.txfs).append("</txfs>");
        sb.append("\r\n    ").append("<totalRecv>").append(this.totalRecvMessages).append("</totalRecv>");
        sb.append("\r\n    ").append("<totalSend>").append(this.totalSendMessages).append("</totalSend>");
        sb.append("\r\n    ").append("<perMinuteRecv>").append(this.msgRecvPerMinute).append("</perMinuteRecv>");
        sb.append("\r\n    ").append("<perMinuteSend>").append(this.msgSendPerMinute).append("</perMinuteSend>");

        String stime = CalendarUtil.getTimeString(this.lastReceiveTime);
        sb.append("\r\n    ").append("<lastRecv>").append(stime).append("</lastRecv>");
        stime = CalendarUtil.getTimeString(this.lastSendTime);
        sb.append("\r\n    ").append("<lastSend>").append(stime).append("</lastSend>");
        sb.append("\r\n</sockserver-profile>");
        return sb.toString();
    }

    public void setMessageCreator(IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}