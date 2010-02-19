package com.hzjbbis.fk.sockserver;

import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockserver.event.MessageSendFailEvent;
import com.hzjbbis.fk.sockserver.io.SocketIoThread;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class AsyncSocketClient implements IServerSideChannel {
    protected SocketChannel channel;
    protected String peerIp;
    protected int peerPort;
    protected String peerAddr;
    protected String localIp;
    protected int localPort;
    protected String localAddr;
    protected ByteBuffer bufRead;
    protected ByteBuffer bufWrite;
    protected IMessage curReadingMsg;
    protected IMessage curWritingMsg;
    protected List<IMessage> sendList = new LinkedList();

    private int lastingWrite = 0;

    private static final Logger log = Logger.getLogger(AsyncSocketClient.class);
    protected ISocketServer server;
    protected SocketIoThread ioThread;
    private int intKey = 0;
    private int maxSendQueueSize = 20;

    private long lastIoTime = System.currentTimeMillis();
    private long lastReadTime = System.currentTimeMillis();
    private boolean bufferHasRemaining = false;

    private int requestNum = -1;

    public AsyncSocketClient() {
    }

    public AsyncSocketClient(SocketChannel c, ISocketServer s) {
        this.channel = c;
        this.server = s;
        try {
            this.peerIp = this.channel.socket().getInetAddress().getHostAddress();
            this.peerPort = this.channel.socket().getPort();
            this.peerAddr = this.peerIp + ":" + this.peerPort + ":T";
            this.localIp = this.channel.socket().getLocalAddress().getHostAddress();
            this.localPort = this.channel.socket().getLocalPort();
            this.localAddr = this.localIp + ":" + HexDump.toHex((short) this.localPort);
        } catch (Exception localException) {
        }
        this.bufRead = ByteBuffer.allocateDirect(s.getBufLength());
        this.bufWrite = ByteBuffer.allocateDirect(s.getBufLength());
    }

    public boolean send(IMessage msg) {
        if (this.sendList.size() >= this.maxSendQueueSize) {
            log.warn(toString() + "-发送队列长度>maxSendQueueSize，本消息被丢弃");

            GlobalEventHandler.postEvent(new MessageSendFailEvent(msg, this));
            return false;
        }
        synchronized (this.sendList) {
            if (this.requestNum > 0) {
                synchronized (this) {
                    this.requestNum -= 1;
                }
            }
            this.sendList.add(msg);
        }
        this.ioThread.clientWriteRequest(this);
        return true;
    }

    public int sendQueueSize() {
        synchronized (this.sendList) {
            return this.sendList.size();
        }
    }

    public void setMaxSendQueueSize(int maxSendQueueSize) {
        this.maxSendQueueSize = maxSendQueueSize;
    }

    public IMessage getNewSendMessage() {
        synchronized (this.sendList) {
            if (this.sendList.size() == 0) return null;
            return ((IMessage) this.sendList.remove(0));
        }
    }

    public void close() {
        try {
            this.channel.socket().shutdownInput();
            this.channel.socket().shutdownOutput();
        } catch (Exception localException) {
        }
        try {
            this.channel.close();
            this.channel = null;
        } catch (Exception localException1) {
        }
        if (log.isInfoEnabled()) {
            log.info("客户端关闭[" + this.peerIp + ":" + this.peerPort + ",localport:" + this.localPort + "]");
        }

        synchronized (this.sendList) {
            for (IMessage msg : this.sendList) {
                GlobalEventHandler.postEvent(new MessageSendFailEvent(msg, this));
            }
            this.sendList.clear();
        }
    }

    public SocketChannel getChannel() {
        return this.channel;
    }

    public SocketAddress getSocketAddress() {
        return this.channel.socket().getRemoteSocketAddress();
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public String getPeerIp() {
        return this.peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public int getPeerPort() {
        return this.peerPort;
    }

    public void setPeerPort(int peerPort) {
        this.peerPort = peerPort;
    }

    public String getLocalIp() {
        return this.localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getLocalAddr() {
        return this.localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public final SocketIoThread getIoThread() {
        return this.ioThread;
    }

    public void setIoThread(Object ioThread) {
        this.ioThread = ((SocketIoThread) ioThread);
    }

    public ISocketServer getServer() {
        return this.server;
    }

    public void setServer(TcpSocketServer server) {
        this.server = server;
    }

    public void closeRequest() {
        this.ioThread.closeClientRequest(this);
    }

    public IMessage getCurReadingMsg() {
        return this.curReadingMsg;
    }

    public void setCurReadingMsg(IMessage curReadingMsg) {
        this.curReadingMsg = curReadingMsg;
        if (curReadingMsg != null) this.server.incRecvMessage();
    }

    public IMessage getCurWritingMsg() {
        return this.curWritingMsg;
    }

    public void setCurWritingMsg(IMessage curWritingMsg) {
        this.curWritingMsg = curWritingMsg;
        if (curWritingMsg != null) this.server.incSendMessage();
    }

    public ByteBuffer getBufRead() {
        return this.bufRead;
    }

    public ByteBuffer getBufWrite() {
        return this.bufWrite;
    }

    public String toString() {
        return this.peerAddr;
    }

    public int getIntKey() {
        return this.intKey;
    }

    public void setIntKey(int intKey) {
        this.intKey = intKey;
    }

    public final String getPeerAddr() {
        return this.peerAddr;
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

    public int getLastingWrite() {
        return this.lastingWrite;
    }

    public void setLastingWrite(int lastingWrite) {
        this.lastingWrite = lastingWrite;
    }

    public int getLocalPort() {
        return this.localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public int getRequestNum() {
        return this.requestNum;
    }

    public void setRequestNum(int requestNum) {
        synchronized (this) {
            this.requestNum = requestNum;
        }
    }

    public boolean bufferHasRemaining() {
        return this.bufferHasRemaining;
    }

    public void setBufferHasRemaining(boolean hasRemaining) {
        this.bufferHasRemaining = hasRemaining;
    }
}