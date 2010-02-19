package com.hzjbbis.fk.sockclient;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class SimpleSocket {
    private static final Logger log = Logger.getLogger(SimpleSocket.class);
    private String hostIp;
    private int hostPort;
    private int ioTimeout = 20000;
    private int readBufferSize = 2048;
    private Socket socket;
    private byte[] readBuffer = new byte[2048];
    private long lastConnectTime = 0L;
    private long lastIoTime = 0L;

    public SimpleSocket(String ip, int port) {
        this.hostIp = ip;
        this.hostPort = port;
    }

    public SimpleSocket() {
    }

    public boolean connect() {
        this.socket = new Socket();
        this.lastConnectTime = System.currentTimeMillis();
        try {
            this.socket.connect(new InetSocketAddress(this.hostIp, this.hostPort));
            this.socket.setTcpNoDelay(true);
            this.socket.setSoTimeout(this.ioTimeout);
            this.socket.setReceiveBufferSize(this.readBufferSize);
            this.socket.setSendBufferSize(this.readBufferSize);
        } catch (IOException exp) {
            log.warn("连接到UMS服务器失败：hostIp=" + this.hostIp + ",port=" + this.hostPort + ";原因：" + exp.getLocalizedMessage());
            this.socket = null;
            return false;
        } finally {
            this.lastConnectTime = System.currentTimeMillis();
        }
        return true;
    }

    public void close() {
        if (this.socket == null) return;
        try {
            this.socket.shutdownInput();
            this.socket.shutdownOutput();
            this.socket.close();
        } catch (IOException exp) {
            log.warn("关闭socket异常，原因：" + exp.getLocalizedMessage());
        } finally {
            this.socket = null;
        }
    }

    public boolean reConnect() {
        close();
        return connect();
    }

    public boolean isAlive() {
        return ((this.socket == null) || (!(this.socket.isConnected())));
    }

    public int read(byte[] buffer, int offset, int len) {
        try {
            if (!(isAlive())) return -1;
            this.lastIoTime = System.currentTimeMillis();
            return this.socket.getInputStream().read(buffer, offset, len);
        } catch (SocketTimeoutException timeoutExp) {
            return 0;
        } catch (IOException ioExp) {
            close();
            log.warn("socket读数据异常:" + ioExp.getLocalizedMessage());
            return -1;
        } catch (Exception exp) {
            log.warn("socket读数据异常:" + exp.getLocalizedMessage(), exp);
        }
        return 0;
    }

    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public int read(ByteBuffer byteBuffer) {
        byte[] buffer = new byte[byteBuffer.remaining()];
        int n = read(buffer);
        if (n <= 0) return n;
        byteBuffer.put(buffer, 0, n).flip();
        return n;
    }

    public int write(byte[] buffer, int offset, int len) {
        try {
            if (!(isAlive())) return -1;
            this.socket.getOutputStream().write(buffer, offset, len);
            this.socket.getOutputStream().flush();
        } catch (IOException ioExp) {
            close();
            log.warn("socket发送数据异常:" + ioExp.getLocalizedMessage());
            return -1;
        } catch (Exception exp) {
            close();
            log.warn("socket发送数据异常:" + exp.getLocalizedMessage(), exp);
            return 0;
        }
        return len;
    }

    public int write(byte[] buffer) {
        return write(buffer, 0, buffer.length);
    }

    public int write(ByteBuffer byteBuffer) {
        byte[] buffer = new byte[byteBuffer.remaining()];
        byteBuffer.get(buffer);
        return write(buffer);
    }

    public int write(String message) {
        try {
            return write(message.getBytes("GBK"));
        } catch (Exception e) {
            log.warn("write(message.getBytes(\"GBK\")) exception", e);
        }
        return -1;
    }

    public String read() {
        int n = read(this.readBuffer);
        if (n <= 0) return null;
        try {
            return new String(this.readBuffer, 0, n);
        } catch (Exception e) {
            log.warn("read from socket exception.", e);
        }
        return null;
    }

    public long getLastConnectTime() {
        return this.lastConnectTime;
    }

    public String getHostIp() {
        return this.hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getHostPort() {
        return this.hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public int getIoTimeout() {
        return this.ioTimeout;
    }

    public void setIoTimeout(int ioTimeout) {
        this.ioTimeout = ioTimeout;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public final long getLastIoTime() {
        return this.lastIoTime;
    }
}