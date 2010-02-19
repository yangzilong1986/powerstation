package com.hzjbbis.fk.message.gate;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.exception.MessageParseException;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.MultiProtoRecognizer;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class MessageGate implements IMessage {
    private static final Logger log = Logger.getLogger(MessageGate.class);
    private static final byte[] zeroPacket = new byte[0];
    private static final ByteBuffer emptyData = ByteBuffer.wrap(zeroPacket);
    protected MessageType type = MessageType.MSG_GATE;
    private long ioTime = System.currentTimeMillis();
    private String peerAddr;
    private String txfs;
    private String status;
    private Long cmdId;
    private IChannel source;
    protected GateHead head = new GateHead();
    protected ByteBuffer data = emptyData;
    private ByteBuffer rawPacket = null;

    private int state = -1;
    private int priority = 0;

    private IMessage innerMessage = null;
    private String serverAddress;
    public static final short CMD_WRAP = 0;
    public static final short CMD_GATE_HREQ = 17;
    public static final short CMD_GATE_HREPLY = 18;
    public static final short CMD_GATE_PARAMS = 32;
    public static final short CMD_GATE_REQUEST = 33;
    public static final short CMD_GATE_REPLY = 34;
    public static final short CMD_GATE_CONFIRM = 35;
    public static final short CMD_GATE_SENDFAIL = 36;
    public static final short REQ_MONITOR_RELAY_PROFILE = 49;
    public static final short REP_MONITOR_RELAY_PROFILE = 50;

    public long getIoTime() {
        return this.ioTime;
    }

    public MessageType getMessageType() {
        return MessageType.MSG_GATE;
    }

    public String getPeerAddr() {
        return this.peerAddr;
    }

    public int getPriority() {
        return this.priority;
    }

    public byte[] getRawPacket() {
        if (this.rawPacket != null) {
            return this.rawPacket.array();
        }
        return zeroPacket;
    }

    public String getRawPacketString() {
        if (this.rawPacket != null) {
            return HexDump.hexDumpCompact(this.rawPacket);
        }
        return "";
    }

    public IChannel getSource() {
        return this.source;
    }

    public boolean read(ByteBuffer readBuffer) throws MessageParseException {
        synchronized (this) {
            return _read(readBuffer);
        }
    }

    public boolean _read(ByteBuffer readBuffer) throws MessageParseException {
        boolean ret;
        if ((this.state == -1) && (readBuffer.remaining() < 13)) {
            if (log.isDebugEnabled()) log.debug("长度不足读取网关报文头，等会儿继续读取。readBuffer.remaining=" + readBuffer.remaining());
            return false;
        }
        if ((this.type != MessageType.MSG_GATE) && (this.type != MessageType.MSG_WEB) && (this.type != MessageType.MSG_INVAL)) {
            ret = this.innerMessage.read(readBuffer);
            if (ret) onReadFinished();
            return ret;
        }

        if ((-1 == this.state) || (15 == this.state)) {
            this.innerMessage = MultiProtoRecognizer.recognize(readBuffer);
            if (this.innerMessage == null) return false;
            if (!(this.innerMessage instanceof MessageGate)) {
                this.type = this.innerMessage.getMessageType();
                this.head.setCommand(0);
                ret = this.innerMessage.read(readBuffer);
                if (ret) {
                    onReadFinished();
                    this.rawPacket = HexDump.toByteBuffer(this.innerMessage.getRawPacketString());
                }
                return ret;
            }

            this.innerMessage = null;
            if (readBuffer.remaining() < 13) {
                if (log.isDebugEnabled())
                    log.debug("网关对报文进行分析后，长度不足以读取网关报文头。readBuffer.remaining=" + readBuffer.remaining());
                return false;
            }
            this.state = 1;
            ret = this.head.read(readBuffer);
            if (!(ret)) return false;
            this.state = 2;
            return readDataSection(readBuffer);
        }
        if (1 == this.state) {
            ret = this.head.read(readBuffer);
            if (!(ret)) return false;
            this.state = 2;
            return readDataSection(readBuffer);
        }
        if (2 == this.state) {
            return readDataSection(readBuffer);
        }
        return true;
    }

    private boolean readDataSection(ByteBuffer buffer) throws MessageParseException {
        if (this.state == 2) {
            if ((emptyData == this.data) && (this.head.getIntBodylen() > 0)) {
                this.data = ByteBuffer.wrap(new byte[this.head.getIntBodylen()]);
            }
            if (this.data.remaining() >= buffer.remaining()) {
                this.data.put(buffer);
            } else {
                buffer.get(this.data.array(), this.data.position(), this.data.remaining());
                this.data.position(this.data.limit());
            }
            if (this.data.remaining() == 0) {
                this.data.flip();
                this.rawPacket = ByteBuffer.allocate(this.data.remaining() + this.head.getHeadLen());
                this.rawPacket.put(this.head.getRawHead()).put(this.data);
                this.rawPacket.rewind();
                this.data.rewind();
                this.state = 15;
                this.ioTime = System.currentTimeMillis();
                onReadFinished();
                return true;
            }
            if (log.isDebugEnabled()) log.debug("readDataSection，长度不足。网关数据区还缺的数据长度=" + this.data.remaining());
            return false;
        }
        buffer.position(buffer.limit());
        if (log.isInfoEnabled()) log.info("readDataSection,非法状态，把数据全部清空。");
        return false;
    }

    private void onReadFinished() throws MessageParseException {
        String peer;
        if ((this.type == MessageType.MSG_ZJ) || (this.type == MessageType.MSG_GW)) {
            if (this.innerMessage.getIoTime() == 0L) this.innerMessage.setIoTime(System.currentTimeMillis());
            peer = this.innerMessage.getPeerAddr();
            if (peer == null) this.innerMessage.setPeerAddr("");
            this.innerMessage.setSource(getSource());
        }
        if (this.type == MessageType.MSG_GATE) {
            if (this.head.getCommand() == 34) {
                this.innerMessage = MultiProtoRecognizer.recognize(this.data);
                if (this.innerMessage == null) {
                    log.warn("上行网关报文数据区不能识别:" + HexDump.hexDumpCompact(this.data));
                    return;
                }
                this.innerMessage.read(this.data);
                this.data.rewind();
                if (this.innerMessage.getIoTime() == 0L) {
                    this.innerMessage.setIoTime(System.currentTimeMillis());
                    peer = this.head.getAttributeAsString(9);
                    if (peer.length() == 0) this.innerMessage.setPeerAddr(this.source.toString());
                    else this.innerMessage.setPeerAddr(peer);
                }
                String _txfs = this.head.getAttributeAsString(10);
                if (_txfs.length() != 0) this.innerMessage.setTxfs(_txfs);
                String serverAddress = this.head.getAttributeAsString(17);
                if (serverAddress.length() > 0) {
                    setServerAddress(serverAddress);
                    this.innerMessage.setServerAddress(serverAddress);
                }
            } else if (this.head.getCommand() == 33) {
                this.innerMessage = MultiProtoRecognizer.recognize(this.data);
                if (this.innerMessage == null) {
                    log.warn("下行网关报文数据区不能识别:" + HexDump.hexDumpCompact(this.data));
                    return;
                }
                this.innerMessage.read(this.data);
                this.data.rewind();
            }
            if (this.innerMessage != null) this.innerMessage.setSource(getSource());
        }
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
        if (this.innerMessage != null) this.innerMessage.setSource(src);
    }

    public boolean write(ByteBuffer writeBuffer) {
        synchronized (this) {
            return _write(writeBuffer);
        }
    }

    private boolean _write(ByteBuffer writeBuffer) {
        if (47 == this.state) return true;
        if (15 == this.state) this.state = -1;
        if (-1 == this.state) {
            if (this.data == null) this.head.setIntBodylen(0);
            else this.head.setIntBodylen(this.data.remaining());
            this.state = 17;
            if (!(this.head.write(writeBuffer))) return false;
            this.state = 18;
            return _writeDataSection(writeBuffer);
        }
        if (17 == this.state) {
            if (!(this.head.write(writeBuffer))) return false;
            this.state = 18;
            return _writeDataSection(writeBuffer);
        }
        if (18 == this.state) {
            return _writeDataSection(writeBuffer);
        }
        return true;
    }

    private boolean _writeDataSection(ByteBuffer buffer) {
        if (buffer.remaining() >= this.data.remaining()) {
            buffer.put(this.data);
            this.data.rewind();
            this.ioTime = System.currentTimeMillis();
            this.rawPacket = ByteBuffer.allocate(this.head.getHeadLen() + this.data.remaining());
            this.rawPacket.put(this.head.getRawHead()).put(this.data);
            this.data.rewind();
            this.rawPacket.flip();
            this.state = -1;
            return true;
        }

        int limit = this.data.limit();
        this.data.limit(this.data.position() + buffer.remaining());
        buffer.put(this.data);
        this.data.limit(limit);
        return false;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public IMessage getInnerMessage() {
        return this.innerMessage;
    }

    public void setDownInnerMessage(IMessage innerMessage) {
        this.innerMessage = innerMessage;
        this.innerMessage.setSource(getSource());
        this.head.setCommand(33);
        this.data = ByteBuffer.wrap(innerMessage.getRawPacket());
        this.head.setIntBodylen(this.data.remaining());
        String innerMsg = innerMessage.getRawPacketString();
        this.rawPacket = ByteBuffer.allocate(this.head.getHeadLen() + innerMsg.length() / 2);
        this.rawPacket.put(this.head.getRawHead()).put(HexDump.toByteBuffer(innerMsg));
        this.rawPacket.flip();
    }

    public void setUpInnerMessage(IMessage innerMessage) {
        this.innerMessage = innerMessage;
        this.head.setCommand(34);

        if (innerMessage.getServerAddress() != null) {
            this.head.setAttribute(17, innerMessage.getServerAddress());
        }

        this.data = ByteBuffer.wrap(innerMessage.getRawPacket());
        this.head.setIntBodylen(this.data.remaining());
        String innerMsg = innerMessage.getRawPacketString();
        this.rawPacket = ByteBuffer.allocate(this.head.getHeadLen() + innerMsg.length() / 2);
        this.rawPacket.put(this.head.getRawHead()).put(HexDump.toByteBuffer(innerMsg));
        this.rawPacket.flip();
    }

    public GateHead getHead() {
        return this.head;
    }

    public ByteBuffer getData() {
        return this.data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public String toString() {
        return getRawPacketString();
    }

    public static MessageGate createHRequest(int numPackets) {
        MessageGate msg = new MessageGate();
        msg.head.setCommand(17);
        msg.data = ByteBuffer.allocate(8);
        msg.data.putInt(numPackets).flip();
        return msg;
    }

    public static MessageGate createHReply() {
        MessageGate msg = new MessageGate();
        msg.head.setCommand(18);
        return msg;
    }

    public static final MessageGate createMoniteProfileRequest() {
        MessageGate msg = new MessageGate();
        msg.head.setCommand(49);
        return msg;
    }

    public static final MessageGate createMoniteProfileReply(String profile) {
        MessageGate msg = new MessageGate();
        msg.head.setCommand(50);
        msg.setPriority(3);
        if ((profile != null) && (profile.length() > 0)) {
            byte[] bts = profile.getBytes();
            msg.data = ByteBuffer.wrap(bts);
        }
        return msg;
    }

    public static MessageGate createHReply(ByteBuffer carriedMsgs) {
        MessageGate msg = new MessageGate();
        msg.head.setCommand(18);
        msg.data = carriedMsgs;
        return msg;
    }

    public Long getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(Long id) {
        this.cmdId = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean isHeartbeat() {
        return ((this.head.getCommand() != 18) && (this.head.getCommand() != 17));
    }

    public int getRtua() {
        if (this.innerMessage != null) return this.innerMessage.getRtua();
        return 0;
    }

    public int length() {
        int len = 0;
        if (getRawPacket().length == 0) {
            if (this.innerMessage != null) len = this.innerMessage.length();
        } else len = getRawPacket().length;
        return len;
    }

    public boolean isTask() {
        return false;
    }

    public void setTask(boolean isTask) {
    }
}