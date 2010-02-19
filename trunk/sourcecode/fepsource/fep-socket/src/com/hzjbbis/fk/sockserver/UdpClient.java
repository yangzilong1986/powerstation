package com.hzjbbis.fk.sockserver;

import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.message.IMessage;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class UdpClient implements IServerSideChannel {
    private SocketAddress socketAddress;
    private SyncUdpServer server;
    private String peerIp = "0.0.0.0";
    private int peerPort = 0;

    private ByteBuffer bufRead = null;
    private IMessage curReadingMsg;
    private long lastIoTime = System.currentTimeMillis();
    private long lastReadTime = System.currentTimeMillis();
    private boolean bufferHasRemaining = false;

    private int lastingWrite = 0;

    private int requestNum = -1;

    public int getRequestNum() {
        return this.requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public int getLastingWrite() {
        return this.lastingWrite;
    }

    public void setLastingWrite(int lastingWrite) {
        this.lastingWrite = lastingWrite;
    }

    public UdpClient(SocketAddress sa, SyncUdpServer udpServer) {
        this.socketAddress = sa;
        this.server = udpServer;
        this.bufRead = ByteBuffer.allocate(this.server.getBufLength());
        if (sa instanceof InetSocketAddress) {
            this.peerIp = ((InetSocketAddress) sa).getAddress().getHostAddress();
            this.peerPort = ((InetSocketAddress) sa).getPort();
        } else {
            String connstr = sa.toString();
            if (connstr != null) {
                if (connstr.charAt(0) == '/') {
                    connstr = connstr.substring(1);
                }
                String[] parts = connstr.split(":");
                if (parts.length >= 2) {
                    this.peerIp = parts[0];
                    this.peerPort = Integer.parseInt(parts[1]);
                }
            }
        }
    }

    public boolean send(IMessage msg) {
        this.server.incSendMessage();
        this.server.setLastSendTime(System.currentTimeMillis());
        return this.server.send(msg, this);
    }

    public int sendQueueSize() {
        return 0;
    }

    public void setMaxSendQueueSize(int maxSize) {
    }

    public IMessage getCurReadingMsg() {
        return this.curReadingMsg;
    }

    public void setCurReadingMsg(IMessage curReadingMsg) {
        this.curReadingMsg = curReadingMsg;
        if (curReadingMsg != null) this.server.incRecvMessage();
    }

    public SyncUdpServer getServer() {
        return this.server;
    }

    public String getPeerIp() {
        return this.peerIp;
    }

    public int getPeerPort() {
        return this.peerPort;
    }

    public String getPeerAddr() {
        return this.peerIp + ":" + this.peerPort + ":U";
    }

    public ByteBuffer getBufRead() {
        return this.bufRead;
    }

    public SocketChannel getChannel() {
        return null;
    }

    public void setIoThread(Object ioThread) {
    }

    public SocketAddress getSocketAddress() {
        return this.socketAddress;
    }

    public long getLastIoTime() {
        return this.lastIoTime;
    }

    public long getLastReadTime() {
        return this.lastReadTime;
    }

    public void setLastIoTime() {
        this.lastIoTime = System.currentTimeMillis();
    }

    public void setLastReadTime() {
        this.lastReadTime = System.currentTimeMillis();
        this.lastIoTime = this.lastReadTime;
    }

    public void close() {
    }

    public ByteBuffer getBufWrite() {
        return null;
    }

    public IMessage getCurWritingMsg() {
        return null;
    }

    public IMessage getNewSendMessage() {
        return null;
    }

    public void setCurWritingMsg(IMessage curWritingMsg) {
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UDP client,peer=").append(this.peerIp);
        sb.append(":").append(this.peerPort);
        return sb.toString();
    }

    public boolean bufferHasRemaining() {
        return this.bufferHasRemaining;
    }

    public void setBufferHasRemaining(boolean hasRemaining) {
        this.bufferHasRemaining = hasRemaining;
    }
}