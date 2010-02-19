package com.hzjbbis.fk.sockclient.async;

import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.sockserver.AsyncSocketClient;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class JAsyncSocket extends AsyncSocketClient {
    private static final Logger log = Logger.getLogger(JAsyncSocket.class);
    private long lastConnectTime = System.currentTimeMillis() - 600000L;
    private Object attachment;

    public JAsyncSocket(ISocketServer s) {
        AsyncSocketPool sp = (AsyncSocketPool) s;
        this.server = s;
        this.peerIp = sp.getPeerIp();
        this.peerPort = sp.getPeerPort();
        this.peerAddr = this.peerIp + ":" + HexDump.toHex((short) this.peerPort) + ":A";
        this.bufRead = ByteBuffer.allocateDirect(s.getBufLength());
        this.bufWrite = ByteBuffer.allocateDirect(s.getBufLength());
    }

    public Object attachment() {
        return this.attachment;
    }

    public void attach(Object attach) {
        this.attachment = attach;
    }

    public void createChannel() {
        try {
            this.channel = SocketChannel.open();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public boolean isConnected() {
        synchronized (this) {
            return ((this.channel != null) ? this.channel.isConnected() : false);
        }
    }

    public long getLastConnectTime() {
        return this.lastConnectTime;
    }

    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public String toString() {
        return "async socket:peer=" + this.peerIp + ",localport=" + this.localPort;
    }
}