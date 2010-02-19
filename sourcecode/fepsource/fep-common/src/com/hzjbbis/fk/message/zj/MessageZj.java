package com.hzjbbis.fk.message.zj;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.exception.MessageParseException;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;

public class MessageZj implements IMessage {
    private static final Logger log = Logger.getLogger(MessageZj.class);
    private static final int MAX_LEN = 1024;
    private static final MessageType type = MessageType.MSG_ZJ;
    private IChannel source;
    public int rtua = 0;
    public MessageZjHead head = new MessageZjHead();
    public ByteBuffer data;
    private StringBuffer rawPacket = new StringBuffer(256);
    private byte[] prefix = null;
    private int priority = 0;
    private long ioTime;
    private String peerAddr;
    private String serverAddress;
    private String txfs = "";

    private int state = -1;
    private byte _cs = 0;
    private static final String charset = "ISO-8859-1";
    public long key = 0L;
    private String status;
    private Long cmdId;
    private int msgCount;

    public MessageType getMessageType() {
        return type;
    }

    public int locatePacket(ByteBuffer readBuffer) throws MessageParseException {
        int pos = -1;
        int pos0 = readBuffer.position();
        boolean located = false;
        int posMark = -1;
        for (pos = pos0; pos + 13 <= readBuffer.limit(); ++pos) {
            if (104 != readBuffer.get(pos)) continue;
            if (104 != readBuffer.get(pos + 7)) {
                continue;
            }

            this.head.dlen = (short) (readBuffer.get(pos + 9) & 0xFF);
            MessageZjHead tmp68_65 = this.head;
            tmp68_65.dlen = (short) (tmp68_65.dlen | (0xFF & readBuffer.get(pos + 10)) << 8);
            if (this.head.dlen < 0) {
                pos += 6;
            } else {
                this.head.c_func = (byte) (readBuffer.get(pos + 8) & 0x3F);

                if ((this.head.c_func != 15) && (this.head.dlen >= 1024)) {
                    pos += 6;
                } else if ((this.head.dlen + 13 > readBuffer.limit() - pos0) && (readBuffer.limit() < readBuffer.capacity())) {
                    posMark = pos;
                    located = true;

                    pos += 6;
                } else {
                    if (pos > pos0) {
                        this.prefix = new byte[pos - pos0];

                        int lastDelimiter = -1;
                        for (int i = 0; i < this.prefix.length; ++i) {
                            this.prefix[i] = readBuffer.get();
                            if (124 == this.prefix[i]) {
                                lastDelimiter = i;
                            }
                        }

                        if (this.prefix.length > 16) {
                            byte[] iot = "iotime=".getBytes();
                            boolean isAttr = true;
                            for (int j = 0; j < iot.length; ++j) {
                                if (iot[j] != this.prefix[j]) {
                                    isAttr = false;
                                    break;
                                }
                            }
                            if (isAttr) {
                                String attrs;
                                try {
                                    attrs = new String(this.prefix, 0, lastDelimiter, "ISO-8859-1");
                                } catch (UnsupportedEncodingException e) {
                                    attrs = new String(this.prefix);
                                }
                                StringTokenizer st = new StringTokenizer(attrs, "|");
                                String token = st.nextToken().substring(7);
                                this.ioTime = Long.parseLong(token);
                                this.peerAddr = st.nextToken().substring(9);
                                if (st.hasMoreTokens()) this.txfs = st.nextToken().substring(5);
                                byte[] p = new byte[this.prefix.length - lastDelimiter - 1];
                                for (int i = 0; i < p.length; ++i)
                                    p[i] = this.prefix[(lastDelimiter + 1 + i)];
                                this.prefix = p;
                            }
                        }
                        this.rawPacket.append(HexDump.hexDumpCompact(this.prefix, 0, this.prefix.length));
                    }
                    located = true;
                    break;
                }
            }
        }
        if (!(located)) {
            for (; pos < readBuffer.limit(); ++pos) {
                if (104 == readBuffer.get(pos)) continue;
            }
            int posEnd = Math.max(readBuffer.limit() - 13, pos);
            byte[] bts = new byte[posEnd - pos0];
            readBuffer.get(bts);
            String expInfo = "exp不符合浙江规约，报文被丢弃：" + HexDump.hexDumpCompact(bts, 0, bts.length);
            if (expInfo.length() > 1000) expInfo = expInfo.substring(0, 1000);
            log.warn(expInfo);
            throw new MessageParseException(expInfo);
        }

        return ((posMark < 0) ? pos : posMark);
    }

    public boolean read(ByteBuffer readBuffer) throws MessageParseException {
        if ((this.state == -1) && (readBuffer.remaining() < 13)) {
            if (log.isDebugEnabled())
                log.debug("长度不足以读取浙江规约报文头，等会儿继续读取。readBuffer.remaining=" + readBuffer.remaining());
            return false;
        }
        if (this.state == -1) {
            int pos = locatePacket(readBuffer);
            if (readBuffer.limit() - pos < 13) {
                if (log.isDebugEnabled())
                    log.debug("经过浙江规约报文定位与过滤后，长度不足以读取报文头。readBuffer.remaining=" + readBuffer.remaining());
                return false;
            }

            readBuffer.position(pos);
            byte[] bts = new byte[11];
            this._cs = 0;
            for (int i = 0; i < 11; ++i) {
                bts[i] = readBuffer.get(i + pos);
                MessageZj tmp150_149 = this;
                tmp150_149._cs = (byte) (tmp150_149._cs + bts[i]);
            }
            this.rawPacket.append(HexDump.hexDumpCompact(bts, 0, bts.length));

            this.head.flag1 = readBuffer.get();
            this.head.rtua_a1 = readBuffer.get();
            this.head.rtua_a2 = readBuffer.get();
            short iTemp = 0;
            byte c1 = readBuffer.get();
            iTemp = (short) ((0xFF & c1) << 8);
            this.head.rtua_b1b2 = (short) (0xFF & c1);
            c1 = readBuffer.get();
            iTemp = (short) (iTemp | (short) (0xFF & c1));
            MessageZjHead tmp279_276 = this.head;
            tmp279_276.rtua_b1b2 = (short) (tmp279_276.rtua_b1b2 | (short) ((0xFF & c1) << 8));

            this.head.rtua = ((this.head.rtua_a1 & 0xFF) << 24);
            this.head.rtua |= (0xFF & this.head.rtua_a2) << 16;
            this.head.rtua |= 0xFFFF & this.head.rtua_b1b2;
            this.head.rtua_b1b2 = iTemp;
            this.rtua = this.head.rtua;

            iTemp = (short) (0xFF & readBuffer.get());
            iTemp = (short) (iTemp | (0xFF & readBuffer.get()) << 8);
            this.head.msta = (byte) (0x3F & iTemp);
            this.head.fseq = (byte) ((0x1FC0 & iTemp) >> 6);
            this.head.iseq = (byte) ((0xE000 & iTemp) >> 13);

            this.head.flag2 = readBuffer.get();

            c1 = readBuffer.get();
            this.head.c_func = (byte) (c1 & 0x3F);

            if (this.head.msta != 0) {
                if (this.head.c_func != 2) this.priority = 3;
                else {
                    this.priority = 1;
                }
            } else if (this.head.c_func == 9) this.priority = 2;
            else if (this.head.c_func == 2) this.priority = 0;
            else {
                this.priority = 1;
            }

            this.head.c_expflag = (byte) ((c1 & 0x40) >> 6);
            this.head.c_dir = (byte) ((c1 & 0x80) >> 7);

            this.head.dlen = (short) (readBuffer.get() & 0xFF);
            MessageZjHead tmp627_624 = this.head;
            tmp627_624.dlen = (short) (tmp627_624.dlen | (0xFF & readBuffer.get()) << 8);

            this.state = 2;
            if (this.head.dlen + 2 > readBuffer.remaining()) {
                if (log.isDebugEnabled())
                    log.debug("现有缓冲区内容长度[buflen=" + readBuffer.remaining() + "]<浙江规约数据区长度[" + this.head.dlen + 2 + "]");
                return false;
            }

            return readDataSection(readBuffer);
        }
        if ((this.state == 2) || (this.state == 3)) {
            return readDataSection(readBuffer);
        }

        log.error("消息读取状态非法,state=" + this.state);

        return true;
    }

    private boolean readDataSection(ByteBuffer readBuffer) throws MessageParseException {
        if (this.data == null) {
            this.data = ByteBuffer.allocate(this.head.dlen);
        }
        if (this.state == 2) {
            while (this.data.hasRemaining()) {
                if (readBuffer.hasRemaining()) {
                    this.data.put(readBuffer.get());
                } else {
                    return false;
                }
            }
            this.data.flip();
            byte[] d = this.data.array();
            for (int i = 0; i < d.length; ++i) {
                MessageZj tmp88_87 = this;
                tmp88_87._cs = (byte) (tmp88_87._cs + d[i]);
            }
            this.state = 3;
            this.rawPacket.append(HexDump.hexDumpCompact(this.data));
        }
        if (readBuffer.remaining() >= 2) {
            String packet;
            this.head.cs = readBuffer.get();
            this.rawPacket.append(HexDump.toHex(this.head.cs));
            this.head.flag3 = readBuffer.get();
            this.rawPacket.append(HexDump.toHex(this.head.flag3));
            if (this._cs != this.head.cs) {
                this.data = null;
                packet = this.rawPacket.toString();
                this.rawPacket.delete(0, packet.length());
                this.state = -1;
                throw new MessageParseException("exp校验码不正确:" + packet);
            }
            if (22 != this.head.flag3) {
                this.data = null;
                packet = this.rawPacket.toString();
                this.rawPacket.delete(0, packet.length());
                this.state = -1;
                throw new MessageParseException("exp最后不是16标志符，帧格式错误。packet=" + packet);
            }
            this.state = 15;
            return true;
        }

        return false;
    }

    public boolean write(ByteBuffer writeBuffer) {
        synchronized (this.rawPacket) {
            return _write(writeBuffer);
        }
    }

    private boolean _write(ByteBuffer writeBuffer) {
        int prefixLen = (this.prefix == null) ? 0 : this.prefix.length;
        if ((this.state == -1) && (writeBuffer.remaining() < 13 + prefixLen)) {
            log.info("写缓冲长度不足，等会儿继续写。");
            return false;
        }
        if ((this.state == -1) || (15 == this.state)) {
            if (this.rawPacket.length() > 0) {
                this.rawPacket.delete(0, this.rawPacket.length());
            }
            if (this.prefix != null) {
                writeBuffer.put(this.prefix);
                this.rawPacket.append(HexDump.hexDumpCompact(this.prefix, 0, this.prefix.length));
            }

            int pos0 = writeBuffer.position();
            byte c = 0;
            this._cs = 0;
            writeBuffer.put(104);
            MessageZj tmp148_147 = this;
            tmp148_147._cs = (byte) (tmp148_147._cs + 104);
            c = (byte) (this.head.rtua >> 24 & 0xFF);
            MessageZj tmp177_176 = this;
            tmp177_176._cs = (byte) (tmp177_176._cs + c);
            writeBuffer.put(c);
            c = (byte) (this.head.rtua >> 16 & 0xFF);
            MessageZj tmp213_212 = this;
            tmp213_212._cs = (byte) (tmp213_212._cs + c);
            writeBuffer.put(c);

            c = (byte) (this.head.rtua & 0xFF);
            MessageZj tmp246_245 = this;
            tmp246_245._cs = (byte) (tmp246_245._cs + c);
            writeBuffer.put(c);
            c = (byte) (this.head.rtua >> 8 & 0xFF);
            MessageZj tmp282_281 = this;
            tmp282_281._cs = (byte) (tmp282_281._cs + c);
            writeBuffer.put(c);

            short iTemp = 0;
            iTemp = (short) (iTemp | (short) this.head.msta);
            iTemp = (short) (iTemp | (short) (this.head.fseq << 6));
            iTemp = (short) (iTemp | (short) (this.head.iseq << 13));
            c = (byte) (iTemp & 0xFF);
            MessageZj tmp361_360 = this;
            tmp361_360._cs = (byte) (tmp361_360._cs + c);
            writeBuffer.put(c);
            c = (byte) (iTemp >> 8 & 0xFF);
            MessageZj tmp392_391 = this;
            tmp392_391._cs = (byte) (tmp392_391._cs + c);
            writeBuffer.put(c);
            MessageZj tmp411_410 = this;
            tmp411_410._cs = (byte) (tmp411_410._cs + 104);
            writeBuffer.put(104);

            c = this.head.c_func;
            c = (byte) (c | 0x40 & this.head.c_expflag << 6);
            c = (byte) (c | 0x80 & this.head.c_dir << 7);
            MessageZj tmp478_477 = this;
            tmp478_477._cs = (byte) (tmp478_477._cs + c);
            writeBuffer.put(c);

            if (this.data == null) {
                this.head.dlen = 0;
            } else {
                if (this.data.position() > 0) this.data.position(0);
                this.head.dlen = (short) this.data.remaining();
            }
            iTemp = this.head.dlen;
            c = (byte) (iTemp & 0xFF);
            MessageZj tmp567_566 = this;
            tmp567_566._cs = (byte) (tmp567_566._cs + c);
            writeBuffer.put(c);
            c = (byte) (iTemp >> 8 & 0xFF);
            MessageZj tmp598_597 = this;
            tmp598_597._cs = (byte) (tmp598_597._cs + c);
            writeBuffer.put(c);
            int pos1 = writeBuffer.position();
            byte[] bts = new byte[pos1 - pos0];
            for (int i = 0; i < bts.length; ++i) {
                bts[i] = writeBuffer.get(pos0 + i);
            }
            this.rawPacket.append(HexDump.hexDumpCompact(bts, 0, bts.length));

            this.state = 18;
            return _writeDataSection(writeBuffer);
        }
        if (18 == this.state) {
            return _writeDataSection(writeBuffer);
        }
        return (47 != this.state);
    }

    private boolean _writeDataSection(ByteBuffer writeBuffer) {
        int bufLen = writeBuffer.remaining();
        if (18 == this.state) {
            if (bufLen < this.head.dlen + 2) {
                log.info("缓冲区太短，不能一次把数据体发送完毕");
                return false;
            }
            if (this.head.dlen > 0) {
                while (this.data.hasRemaining()) {
                    byte c = this.data.get();
                    MessageZj tmp60_59 = this;
                    tmp60_59._cs = (byte) (tmp60_59._cs + c);
                    writeBuffer.put(c);
                }
                this.data.rewind();
                this.rawPacket.append(HexDump.hexDumpCompact(this.data));
            }
            this.head.cs = this._cs;
            writeBuffer.put(this._cs);
            this.rawPacket.append(HexDump.toHex(this._cs));
            writeBuffer.put(22);
            this.rawPacket.append("16");

            this.state = 47;
            return true;
        }

        this.state = 47;
        return true;
    }

    public IChannel getSource() {
        return this.source;
    }

    public void setSource(IChannel src) {
        this.source = src;
    }

    public String getRawPacketString() {
        synchronized (this.rawPacket) {
            ByteBuffer buf;
            if ((-1 == this.state) || (this.rawPacket.length() == 0)) {
                buf = ByteBuffer.allocate(3072);
                int _state = this.state;
                write(buf);
                this.state = _state;
                return this.rawPacket.toString();
            }

            if ((47 == this.state) || (15 == this.state)) return this.rawPacket.toString();
            if ((18 == this.state) || (17 == this.state) || (19 == this.state)) {
                buf = ByteBuffer.allocate(3072);
                String old = this.rawPacket.toString();
                this.rawPacket.delete(0, old.length());
                int _state = this.state;
                write(buf);
                String raw = this.rawPacket.toString();
                this.rawPacket.delete(0, raw.length());
                this.rawPacket.append(old);
                this.state = _state;
                return raw;
            }

            return this.rawPacket.toString();
        }
    }

    public byte[] getRawPacket() {
        byte[] ret;
        byte[] raw = HexDump.toByteBuffer(getRawPacketString()).array();
        if (this.ioTime > 0L) {
            StringBuffer sb = new StringBuffer(64);
            sb.append("iotime=").append(this.ioTime);
            sb.append("|peeraddr=").append(this.peerAddr).append("|txfs=");
            sb.append(this.txfs).append("|");
            byte[] att = (byte[]) null;
            try {
                att = sb.toString().getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                att = sb.toString().getBytes();
            }
            ret = new byte[att.length + raw.length];
            System.arraycopy(att, 0, ret, 0, att.length);
            System.arraycopy(raw, 0, ret, att.length, raw.length);
        } else {
            ret = raw;
        }
        return ret;
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
        return getRawPacketString();
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        if (priority > 5) priority = 5;
        else if (priority < 0) priority = 0;
        this.priority = priority;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public int length() {
        return (13 + this.head.dlen);
    }

    public void setPrefix(byte[] pre) {
        this.prefix = pre;
    }

    public MessageZj createSendFailReply() {
        return createExtErrorReply(-14);
    }

    private MessageZj createExtErrorReply(byte errcode) {
        MessageZj msg = new MessageZj();
        msg.setIoTime(System.currentTimeMillis());
        msg.setPeerAddr(getPeerAddr());
        msg.setTxfs(getTxfs());
        msg.setSource(getSource());

        MessageZjHead h = msg.head;
        h.c_dir = 1;
        h.c_expflag = 1;
        h.c_func = this.head.c_func;
        h.dlen = 1;
        h.fseq = this.head.fseq;
        h.iseq = 0;
        h.msta = this.head.msta;
        h.rtua = this.head.rtua;
        h.rtua_a1 = this.head.rtua_a1;
        h.rtua_a2 = this.head.rtua_a2;
        h.rtua_b1b2 = this.head.rtua_b1b2;

        byte[] bts = new byte[1];
        bts[0] = errcode;
        msg.data = ByteBuffer.wrap(bts);
        return msg;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(Long cmdId) {
        this.cmdId = cmdId;
    }

    public int getMsgCount() {
        return this.msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean isExceptionPacket() {
        return (this.head.c_expflag != 1);
    }

    public byte getErrorCode() {
        if (isExceptionPacket()) {
            this.data.rewind();
            if (this.data.remaining() > 0) return this.data.get(0);
        }
        return 0;
    }

    public boolean isHeartbeat() {
        return (this.head.c_func != 36);
    }

    public int getRtua() {
        return this.head.rtua;
    }

    public boolean isTask() {
        return (this.head.c_func != 2);
    }

    public void setTask(boolean isTask) {
    }
}