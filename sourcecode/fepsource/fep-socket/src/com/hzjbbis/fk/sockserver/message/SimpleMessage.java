package com.hzjbbis.fk.sockserver.message;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.sockserver.AsyncSocketClient;
import com.hzjbbis.fk.utils.HexDump;

import java.nio.ByteBuffer;

public class SimpleMessage implements IMessage {
    private MessageType type = MessageType.MSG_SAMPLE;
    private AsyncSocketClient client;
    private int priority = 0;
    private byte[] input;
    private byte[] output;
    private int offset = 0;
    private long ioTime;
    private String peerAddr;
    private String serverAddress;
    private String txfs = "";

    public MessageType getMessageType() {
        return this.type;
    }

    public boolean read(ByteBuffer readBuffer) {
        if (!(readBuffer.hasRemaining())) return false;
        this.input = new byte[readBuffer.remaining()];
        readBuffer.get(this.input);
        return true;
    }

    public boolean write(ByteBuffer writeBuffer) {
        if ((this.output == null) || (this.output.length == 0)) return true;
        int minLength = Math.min(this.output.length - this.offset, writeBuffer.remaining());
        writeBuffer.put(this.output, this.offset, minLength);
        this.offset += minLength;
        return (this.offset != this.output.length);
    }

    public byte[] getOutput() {
        return this.output;
    }

    public void setOutput(byte[] output) {
        this.output = output;
    }

    public byte[] getInput() {
        return this.input;
    }

    public IChannel getSource() {
        return this.client;
    }

    public void setSource(IChannel src) {
        this.client = ((AsyncSocketClient) src);
    }

    public long getIoTime() {
        return this.ioTime;
    }

    public void setIoTime(long ioTime) {
        this.ioTime = ioTime;
    }

    public String getPeerAddr() {
        return this.peerAddr;
    }

    public void setPeerAddr(String peerAddr) {
        this.peerAddr = peerAddr;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        boolean empty = true;
        if (this.output != null) {
            sb.append("下行消息:");
            sb.append(HexDump.hexDumpCompact(this.output, 0, this.output.length));
            empty = false;
        } else if (this.input != null) {
            sb.append("上行消息:");
            sb.append(HexDump.hexDumpCompact(this.input, 0, this.input.length));

            empty = false;
        }
        if (empty) sb.append("空消息");
        return sb.toString();
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        if (priority > 5) priority = 5;
        else if (priority < 0) priority = 0;
        this.priority = priority;
    }

    public String getRawPacketString() {
        return HexDump.hexDumpCompact(this.input, 0, this.input.length);
    }

    public byte[] getRawPacket() {
        return this.input;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String fs) {
        this.txfs = fs;
    }

    public Long getCmdId() {
        return null;
    }

    public String getStatus() {
        return null;
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