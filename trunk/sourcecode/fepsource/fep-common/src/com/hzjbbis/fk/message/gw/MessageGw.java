package com.hzjbbis.fk.message.gw;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.exception.MessageParseException;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;

public class MessageGw implements IMessage {
    private static final Logger log = Logger.getLogger(MessageGw.class);
    private static final MessageType type = MessageType.MSG_GW_10;
    private IChannel source;
    public ByteBuffer data = null;
    private ByteBuffer aux = null;
    private StringBuffer rawPacket = new StringBuffer(256);
    private byte[] prefix;
    private int priority = 0;
    private long ioTime;
    private String peerAddr;
    private String serverAddress;
    private String txfs = "";
    public MessageGwHead head = new MessageGwHead();

    private int state = -1;
    private byte _cs = 0;
    private static final String charset = "ISO-8859-1";
    public long key = 0L;
    private String status;
    private Long cmdId;
    private int msgCount;
    boolean isTask = false;

    public int locatePacket(ByteBuffer readBuffer) throws MessageParseException {
        int pos = -1;
        int pos0 = readBuffer.position();
        boolean located = false;
        for (pos = pos0; pos + 16 <= readBuffer.limit(); ++pos) {
            if (104 != readBuffer.get(pos)) continue;
            if (104 != readBuffer.get(pos + 5)) {
                continue;
            }

            byte c1 = 0;
            short len1 = 0;
            short len2 = 0;
            c1 = readBuffer.get(pos + 1);
            len1 = (short) (c1 & 0xFF);
            c1 = readBuffer.get(pos + 2);
            len1 = (short) (len1 | (c1 & 0xFF) << 8);

            this.head.decodeL(len1);

            c1 = readBuffer.get(pos + 3);
            len2 = (short) (len2 | c1 & 0xFF);
            c1 = readBuffer.get(pos + 4);
            len2 = (short) (len2 | (c1 & 0xFF) << 8);
            if ((len1 != len2) || (this.head.proto_flag != 2) || (this.head.dlen - 8 < 0)) {
                pos += 4;
            } else {
                this.head.decodeC(readBuffer.get(pos + 6));

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
        if (!(located)) {
            for (; pos < readBuffer.limit(); ++pos) {
                if (104 == readBuffer.get(pos)) continue;
            }
            byte[] bts = new byte[pos - pos0];
            readBuffer.get(bts);
            String expInfo = "exp不符合浙江规约，报文被丢弃：" + HexDump.hexDumpCompact(bts, 0, bts.length);
            if (expInfo.length() > 1000) expInfo = expInfo.substring(0, 1000);
            log.warn(expInfo);
            throw new MessageParseException(expInfo);
        }

        return pos;
    }

    public boolean read(ByteBuffer readBuffer) throws MessageParseException {
        if ((this.state == -1) && (readBuffer.remaining() < 16)) {
            if (log.isDebugEnabled()) log.debug("长度不足以读取国网规约报文头，继续读取。readBuffer.remaining=" + readBuffer.remaining());
            return false;
        }
        if (this.state == -1) {
            int pos = locatePacket(readBuffer);
            if (readBuffer.limit() - pos < 16) {
                if (log.isDebugEnabled())
                    log.debug("经过国网规约定位与过滤后，长度不足以读取报文头。readBuffer.remaining=" + readBuffer.remaining());
                return false;
            }

            readBuffer.position(pos);
            byte[] bts = new byte[14];
            this._cs = 0;
            for (int i = 0; i < 14; ++i) {
                bts[i] = readBuffer.get(i + pos);

                if (i > 5) {
                    MessageGw tmp156_155 = this;
                    tmp156_155._cs = (byte) (tmp156_155._cs + bts[i]);
                }
            }
            this.rawPacket.append(HexDump.hexDumpCompact(bts, 0, bts.length));

            this.head.flag1 = readBuffer.get();
            readBuffer.getShort();
            readBuffer.getShort();
            this.head.flag2 = readBuffer.get();
            readBuffer.get();
            byte c1 = readBuffer.get();
            this.head.rtua |= (c1 & 0xFF) << 16;
            c1 = readBuffer.get();
            this.head.rtua |= (c1 & 0xFF) << 24;
            c1 = readBuffer.get();
            this.head.rtua |= c1 & 0xFF;
            c1 = readBuffer.get();
            this.head.rtua |= (c1 & 0xFF) << 8;
            this.head.decodeA3(readBuffer.get());

            this.head.app_func = readBuffer.get();
            this.head.decodeSEQ(readBuffer.get());

            this.state = 2;

            if (this.head.dlen - 8 > readBuffer.remaining()) {
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
        if (this.data == null) this.data = ByteBuffer.allocate(this.head.dlen - 8);
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
                MessageGw tmp91_90 = this;
                tmp91_90._cs = (byte) (tmp91_90._cs + d[i]);
            }
            this.state = 3;
            this.rawPacket.append(HexDump.hexDumpCompact(this.data));
        }
        if ((this.state == 3) && (readBuffer.remaining() >= 2)) {
            String packet;
            byte cs0 = readBuffer.get();
            this.rawPacket.append(HexDump.toHex(cs0));
            byte flag16 = readBuffer.get();
            this.rawPacket.append(HexDump.toHex(flag16));
            if (cs0 != this._cs) {
                this.data = null;
                packet = this.rawPacket.toString();
                this.rawPacket.delete(0, packet.length());
                this.state = -1;
                throw new MessageParseException("exp校验码不正确:" + packet);
            }
            if (22 != flag16) {
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
        if (this.head.dlen == 0)
            this.head.dlen = (short) (8 + ((this.data != null) ? this.data.remaining() : 0) + ((this.aux != null) ? this.aux.remaining() : 0));
        if ((((this.state == -1) || (15 == this.state))) && (writeBuffer.remaining() < 16 + prefixLen)) {
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
            short stemp = this.head.encodeL();
            writeBuffer.putShort(stemp);
            writeBuffer.putShort(stemp);
            writeBuffer.put(104);
            c = this.head.encodeC();
            MessageGw tmp254_253 = this;
            tmp254_253._cs = (byte) (tmp254_253._cs + c);
            writeBuffer.put(c);

            c = (byte) (this.head.rtua >> 16 & 0xFF);
            MessageGw tmp290_289 = this;
            tmp290_289._cs = (byte) (tmp290_289._cs + c);
            writeBuffer.put(c);
            c = (byte) (this.head.rtua >> 24 & 0xFF);
            MessageGw tmp326_325 = this;
            tmp326_325._cs = (byte) (tmp326_325._cs + c);
            writeBuffer.put(c);
            c = (byte) (this.head.rtua & 0xFF);
            MessageGw tmp359_358 = this;
            tmp359_358._cs = (byte) (tmp359_358._cs + c);
            writeBuffer.put(c);
            c = (byte) (this.head.rtua >> 8 & 0xFF);
            MessageGw tmp395_394 = this;
            tmp395_394._cs = (byte) (tmp395_394._cs + c);
            writeBuffer.put(c);
            c = this.head.encodeA3();
            MessageGw tmp423_422 = this;
            tmp423_422._cs = (byte) (tmp423_422._cs + c);
            writeBuffer.put(c);

            c = this.head.app_func;
            MessageGw tmp451_450 = this;
            tmp451_450._cs = (byte) (tmp451_450._cs + c);
            writeBuffer.put(c);
            c = this.head.encodeSEQ();
            MessageGw tmp479_478 = this;
            tmp479_478._cs = (byte) (tmp479_478._cs + c);
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
        if ((18 == this.state) || (19 == this.state)) {
            return _writeDataSection(writeBuffer);
        }
        return (47 != this.state);
    }

    private boolean _writeDataSection(ByteBuffer writeBuffer) {
        if (!(writeBuffer.hasRemaining())) {
            log.info("发送缓冲区长度为0，发送调用失败");
            return false;
        }
        if (18 == this.state) {
            byte c;
            if ((this.data != null) && (this.data.hasRemaining())) {
                while ((this.data.hasRemaining()) && (writeBuffer.hasRemaining())) {
                    c = this.data.get();
                    MessageGw tmp56_55 = this;
                    tmp56_55._cs = (byte) (tmp56_55._cs + c);
                    writeBuffer.put(c);
                }
                if (!(this.data.hasRemaining())) {
                    this.data.rewind();
                    this.rawPacket.append(HexDump.hexDumpCompact(this.data));
                    this.data.position(this.data.limit());
                }
            }
            if ((this.aux != null) && (this.aux.hasRemaining())) {
                while ((this.aux.hasRemaining()) && (writeBuffer.hasRemaining())) {
                    c = this.aux.get();
                    MessageGw tmp166_165 = this;
                    tmp166_165._cs = (byte) (tmp166_165._cs + c);
                    writeBuffer.put(c);
                }
                if (!(this.aux.hasRemaining())) {
                    this.aux.rewind();
                    this.rawPacket.append(HexDump.hexDumpCompact(this.aux));
                    this.aux.position(this.aux.limit());
                }

            }

            boolean notFinished = ((this.data != null) && (this.data.hasRemaining())) || ((this.aux != null) && (this.aux.hasRemaining()));
            if (notFinished) {
                if (log.isDebugEnabled()) log.debug("缓冲区太短，不能一次把数据体发送完毕");
                return false;
            }

            if (this.data != null) this.data.rewind();
            if (this.aux != null) this.aux.rewind();
            this.state = 19;
        }
        if ((19 == this.state) && (writeBuffer.remaining() >= 2)) {
            writeBuffer.put(this._cs);
            this.rawPacket.append(HexDump.toHex(this._cs));
            writeBuffer.put(22);
            this.rawPacket.append("16");
            this.state = 47;
            return true;
        }

        return false;
    }

    public void setCmdId(Long cmdId) {
        this.cmdId = cmdId;
    }

    public Long getCmdId() {
        return this.cmdId;
    }

    public int getMsgCount() {
        return this.msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public long getIoTime() {
        return this.ioTime;
    }

    public MessageType getMessageType() {
        return type;
    }

    public String getPeerAddr() {
        return this.peerAddr;
    }

    public int getPriority() {
        return this.priority;
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

    public String getRawPacketString() {
        if (this.rawPacket.length() < 13) {
            ByteBuffer buf = ByteBuffer.allocate(5120);
            int _state = this.state;
            write(buf);
            this.state = _state;
        }
        return this.rawPacket.toString();
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public IChannel getSource() {
        return this.source;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public boolean isHeartbeat() {
        return (this.head.app_func != 2);
    }

    public int getFseq() {
        return this.head.seq_pseq;
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

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setSource(IChannel src) {
        this.source = src;
    }

    public void setTxfs(String fs) {
        this.txfs = fs;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRtua() {
        return this.head.rtua;
    }

    public ByteBuffer getAux() {
        return this.aux;
    }

    public void setAux(ByteBuffer aux, boolean hasTp) {
        this.aux = aux;
        this.head.seq_tpv = (byte) ((hasTp) ? 1 : 0);
    }

    public void setSEQ(byte seq) {
        this.head.seq_pseq = seq;
        if (this.aux != null) {
            this.aux.rewind();
            String sAux = HexDump.hexDumpCompact(this.aux);
            if ((sAux.length() == 12) && (seq <= 15)) {
                sAux = "0" + Integer.parseInt(new StringBuilder().append(seq).toString(), 16) + sAux.substring(2);
                setAux(HexDump.toByteBuffer(sAux), true);
            }
        }
    }

    public boolean isNeedConfirm() {
        return (this.head.seq_con != 1);
    }

    public void needConfirm(boolean needConfirm) {
        this.head.seq_con = (byte) ((needConfirm) ? 1 : 0);
    }

    public byte afn() {
        return getAFN();
    }

    public void afn(byte afn) {
        setAFN(afn);
    }

    public byte getAFN() {
        return this.head.app_func;
    }

    public void setAFN(byte afn) {
        this.head.app_func = afn;
        byte frameFn = 0;
        switch (afn) {
            case 0:
                break;
            case 1:
                frameFn = 1;
                this.head.seq_con = 1;
                break;
            case 2:
                frameFn = 9;
                break;
            case 4:
            case 5:
            case 6:
                frameFn = 10;
                this.head.seq_con = 1;
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
                frameFn = 11;
                break;
            case 15:
                frameFn = 10;
            case 7:
        }
        this.head.c_func = frameFn;
    }

    public MessageGw createConfirm() {
        MessageGw con = null;
        if (this.head.seq_con == 1) {
            con = new MessageGw();
            con.head.c_func = 0;
            con.head.c_prm = 0;
            con.head.dlen = 12;
            con.head.rtua = this.head.rtua;
            con.head.seq_con = 0;
            con.head.seq_pseq = this.head.seq_pseq;
            byte[] repData = {0, 0, 1};
            con.data = ByteBuffer.wrap(repData);
        }
        return con;
    }

    public int length() {
        return (this.head.dlen + 8);
    }

    public boolean isTask() {
        return this.isTask;
    }

    public void setTask(boolean isTask) {
        this.isTask = isTask;
    }

    public String toString() {
        return getRawPacketString();
    }
}