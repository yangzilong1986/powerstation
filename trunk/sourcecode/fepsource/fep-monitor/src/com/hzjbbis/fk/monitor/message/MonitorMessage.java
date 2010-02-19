package com.hzjbbis.fk.monitor.message;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.exception.SocketClientCloseException;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class MonitorMessage implements IMessage {
    private static final Logger log = Logger.getLogger(MonitorMessage.class);
    private static final MessageType msgType = MessageType.MSG_MONITOR;
    private static final byte[] FLAG = "JBMONITOR".getBytes();
    private static final ByteBuffer voidBody = ByteBuffer.allocate(0);

    private long ioTime = System.currentTimeMillis();
    private String peerAddr;
    private String serverAddress;
    private String txfs = "tcp";
    private IChannel source;
    private int priority = 0;
    private short command = 0;
    private int bodyLen = 0;
    private ByteBuffer body = voidBody;

    private int state = -1;

    public long getIoTime() {
        return this.ioTime;
    }

    public MessageType getMessageType() {
        return msgType;
    }

    public String getPeerAddr() {
        return this.peerAddr;
    }

    public int getPriority() {
        return this.priority;
    }

    public byte[] getRawPacket() {
        return this.body.array();
    }

    public String getRawPacketString() {
        return "un implemented";
    }

    public IChannel getSource() {
        return this.source;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public boolean read(ByteBuffer readBuffer) {
        if ((this.state == -1) && (readBuffer.remaining() < 15)) {
            if (log.isDebugEnabled()) log.debug("长度不足读取监控报文头，等会儿继续读取。readBuffer.remaining=" + readBuffer.remaining());
            return false;
        }
        if (this.state == -1) {
            for (int i = 0; i < FLAG.length; ++i) {
                if (readBuffer.get() != FLAG[i]) throw new SocketClientCloseException("报文标志不匹配。必须关闭通讯连接");
            }
            this.command = readBuffer.getShort();
            this.bodyLen = readBuffer.getInt();
            this.body = ByteBuffer.allocate(this.bodyLen);
            this.state = 2;
            if (readBuffer.remaining() >= this.body.remaining()) {
                readBuffer.get(this.body.array());
                this.state = 15;
                return true;
            }
            this.body.put(readBuffer);
            return false;
        }
        if (this.state == 2) {
            if (readBuffer.remaining() >= this.body.remaining()) {
                readBuffer.get(this.body.array(), this.body.position(), this.body.remaining());
                this.body.position(0);
                this.state = 15;
                return true;
            }
            this.body.put(readBuffer);
            return false;
        }

        log.error("状态非法。不是读取信息状态。关闭通信连接。请检查程序BUG。");
        throw new SocketClientCloseException("状态非法。不是读取信息状态。关闭通信连接。请检查程序BUG。");
    }

    public void setIoTime(long time) {
        this.ioTime = time;
    }

    public void setPeerAddr(String peer) {
        this.peerAddr = peer;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setSource(IChannel src) {
        this.source = src;
    }

    public void setTxfs(String fs) {
        this.txfs = fs;
    }

    public boolean write(ByteBuffer writeBuffer) {
        if (47 == this.state) return true;
        if (-1 == this.state) {
            if (writeBuffer.remaining() < 15) {
                log.debug("缓冲区长度不足，不能发送监控报文头。writeBuffer.len=" + writeBuffer.remaining());
                return false;
            }
            writeBuffer.put(FLAG);
            writeBuffer.putShort(this.command);
            writeBuffer.putInt(this.body.remaining());
            this.state = 18;
            return sendDataSection(writeBuffer);
        }
        if (18 == this.state) {
            return sendDataSection(writeBuffer);
        }
        log.error("状态非法。不是发送监控消息状态。关闭通信连接。请检查程序BUG。");
        throw new SocketClientCloseException("状态非法。不是发送监控消息状态。关闭通信连接。请检查程序BUG。");
    }

    private boolean sendDataSection(ByteBuffer writeBuffer) {
        if (writeBuffer.remaining() > this.body.remaining()) {
            writeBuffer.put(this.body);
            this.body.rewind();
            this.state = 47;
            this.ioTime = System.currentTimeMillis();
            return true;
        }

        int limit = this.body.limit();
        this.body.limit(this.body.position() + writeBuffer.remaining());
        writeBuffer.put(this.body);
        this.body.limit(limit);
        return false;
    }

    public short getCommand() {
        return this.command;
    }

    public void setCommand(short command) {
        this.command = command;
    }

    public ByteBuffer getBody() {
        return this.body;
    }

    public void setBody(ByteBuffer body) {
        this.body = body;
    }

    public void resetMessageState() {
        this.state = -1;
    }

    public Long getCmdId() {
        return new Long(0L);
    }

    public String getStatus() {
        return "";
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean isHeartbeat() {
        return false;
    }

    public int getRtua() {
        return 0;
    }

    public int length() {
        return 0;
    }

    public void setStatus(String status) {
    }

    public boolean isTask() {
        return false;
    }

    public void setTask(boolean isTask) {
    }
}