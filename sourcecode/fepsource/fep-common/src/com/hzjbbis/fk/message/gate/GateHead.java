package com.hzjbbis.fk.message.gate;

import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class GateHead {
    private static final Logger log;
    public static final byte ATT_VERSION = 1;
    public static final byte ATT_ENCRIPT = 2;
    public static final byte ATT_DESTID = 3;
    public static final byte ATT_PRIVATE = 4;
    public static final byte ATT_CPYATT = 5;
    public static final byte ATT_LOGTYPE = 6;
    public static final byte ATT_FILEPATH = 7;
    public static final byte ATT_SRCADDR = 8;
    public static final byte ATT_DESTADDR = 9;
    public static final byte ATT_TXFS = 10;
    public static final byte ATT_TXSJ = 11;
    public static final byte ATT_MSGTYPE = 12;
    public static final byte ATT_MSGSEQ = 16;
    public static final byte ATT_SERVERADDR = 17;
    private final String flag = "JBBS";
    private byte src = 2;
    private short cmd;
    private int intBodyLen;
    private short headAttrLen;
    private HashMap<Byte, HeadAttribute> hmAttr = new HashMap();
    private byte[] rawHead = null;

    static {
        log = Logger.getLogger(GateHead.class);
    }

    public GateHead() {
        this.cmd = 0;
        this.intBodyLen = 0;
        this.headAttrLen = 0;
    }

    public boolean isValid() {
        return (this.cmd == 0);
    }

    public boolean read(ByteBuffer buffer) {
        if (buffer.remaining() < 13) {
            return false;
        }
        buffer.mark();
        int posOld = buffer.position();
        int index = 0;

        for (index = 0; index < 4; ++index)
            buffer.get();
        this.src = buffer.get();
        this.cmd = buffer.getShort();
        this.intBodyLen = buffer.getInt();
        this.headAttrLen = buffer.getShort();
        if ((this.headAttrLen < 0) || (this.headAttrLen > 4096) || (this.intBodyLen < 0)) {
            log.warn("报文格式错误。headAttrLen=" + this.headAttrLen + ",intBodyLen=" + this.intBodyLen);
            buffer.position(buffer.limit());
            return false;
        }

        if (this.headAttrLen > buffer.remaining()) {
            log.info("缓冲区长度不足以读取网关报文头。");
            buffer.reset();
            return false;
        }
        index = 0;
        while (index < this.headAttrLen) {
            byte id = buffer.get();
            short attlen = buffer.getShort();
            setAttribute(id, buffer, attlen);
            index += attlen + 3;
        }
        int headLen = 13 + index;
        this.rawHead = new byte[headLen];
        buffer.position(posOld);
        buffer.get(this.rawHead);
        return true;
    }

    public int getHeadLen() {
        if (this.headAttrLen == 0) this.headAttrLen = getHeadAttrLen();
        return (13 + this.headAttrLen);
    }

    public int getTotalLen() {
        return (getHeadLen() + this.intBodyLen);
    }

    public boolean write(ByteBuffer buffer) {
        short attrLen = getHeadAttrLen();
        if (buffer.remaining() < 13 + attrLen) return false;
        int posOld = buffer.position();
        buffer.put("JBBS".getBytes());
        buffer.put(this.src);
        buffer.putShort(this.cmd);
        buffer.putInt(this.intBodyLen);
        buffer.putShort(attrLen);
        for (HeadAttribute attr : this.hmAttr.values()) {
            buffer.put(attr.id).putShort(attr.len).put(attr.attr);
        }
        this.rawHead = new byte[getHeadLen()];
        buffer.position(posOld);
        int limitOld = buffer.limit();
        buffer.limit(posOld + this.rawHead.length);
        buffer.get(this.rawHead, 0, this.rawHead.length);
        buffer.limit(limitOld);
        return true;
    }

    public void setCommand(short command) {
        this.cmd = command;
    }

    public void setCommand(int command) {
        this.cmd = (short) command;
    }

    public short getCommand() {
        return this.cmd;
    }

    public int getIntBodylen() {
        return this.intBodyLen;
    }

    public void setIntBodylen(int bodylen) {
        this.intBodyLen = bodylen;
    }

    public short getHeadAttrLen() {
        short hlen = 0;
        for (HeadAttribute attr : this.hmAttr.values()) {
            hlen = (short) (hlen + attr.len + 3);
        }
        return hlen;
    }

    public void setAttribute(byte id, ByteBuffer buf, short len) {
        if ((!($assertionsDisabled)) && (buf.remaining() < len)) throw new AssertionError();

        HeadAttribute attr = new HeadAttribute();
        attr.id = id;
        attr.len = len;
        attr.attr = new byte[len];
        for (int i = 0; i < len; ++i)
            attr.attr[i] = buf.get();
        this.hmAttr.put(Byte.valueOf(id), attr);
    }

    public void setAttribute(byte id, byte[] attr, int pos, int len) {
        if ((!($assertionsDisabled)) && (attr == null)) throw new AssertionError();
        if ((!($assertionsDisabled)) && (((len >= 1024) || (len <= 0) || (pos + len >= attr.length))))
            throw new AssertionError();

        HeadAttribute attrObj = new HeadAttribute();
        attrObj.id = id;
        attrObj.len = (short) len;
        attrObj.attr = new byte[len];
        System.arraycopy(attr, pos, attrObj.attr, 0, len);
        this.hmAttr.put(new Byte(id), attrObj);
    }

    public void setAttribute(byte id, byte[] attr) {
        if ((!($assertionsDisabled)) && (attr == null)) throw new AssertionError();
        setAttribute(id, attr, 0, attr.length);
    }

    public void setAttribute(byte id, int data) {
        byte[] val = new byte[4];
        ByteBuffer bf = ByteBuffer.wrap(val);
        bf.putInt(data);
        bf.flip();
        setAttribute(id, bf.array());
    }

    public void setAttribute(byte id, long data) {
        byte[] val = new byte[8];
        ByteBuffer bf = ByteBuffer.wrap(val);
        bf.putLong(data);
        bf.flip();
        setAttribute(id, bf.array());
    }

    public void setAttribute(byte id, short data) {
        byte[] val = new byte[2];
        ByteBuffer bf = ByteBuffer.wrap(val);
        bf.putShort(data);
        bf.flip();
        setAttribute(id, bf.array());
    }

    public void setAttribute(byte id, byte data) {
        byte[] val = new byte[1];
        ByteBuffer bf = ByteBuffer.wrap(val);
        bf.put(data);
        bf.flip();
        setAttribute(id, bf.array());
    }

    public void setAttribute(byte id, String data) {
        byte[] val = data.getBytes();
        setAttribute(id, val);
    }

    public byte[] getAttribute(byte id) {
        HeadAttribute attr = (HeadAttribute) this.hmAttr.get(new Byte(id));
        if (attr == null) return null;
        return attr.attr;
    }

    public int getAttributeAsInt(byte id) {
        byte[] val = getAttribute(id);
        if ((val == null) || (val.length != 4)) return 0;
        ByteBuffer bf = ByteBuffer.wrap(val);
        return bf.getInt();
    }

    public long getAttributeAsLong(byte id) {
        byte[] val = getAttribute(id);
        if ((val == null) || (val.length != 8)) return 0L;
        ByteBuffer bf = ByteBuffer.wrap(val);
        return bf.getLong();
    }

    public short getAttributeAsShort(byte id) {
        byte[] val = getAttribute(id);
        if ((val == null) || (val.length != 2)) return 0;
        ByteBuffer bf = ByteBuffer.wrap(val);
        return bf.getShort();
    }

    public String getAttributeAsString(byte id) {
        byte[] val = getAttribute(id);
        if (val == null) return "";
        try {
            return new String(val);
        } catch (Exception exp) {
        }
        return HexDump.hexDumpCompact(val, 0, val.length);
    }

    public byte getAttributeAsByte(byte id) {
        byte[] val = getAttribute(id);
        if (val == null) return 0;
        return val[0];
    }

    public byte[] getRawHead() {
        if (this.rawHead == null) {
            this.rawHead = new byte[13];
            ByteBuffer headBuffer = ByteBuffer.wrap(this.rawHead);
            write(headBuffer);
        }
        return this.rawHead;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("|source=").append(HexDump.toHex(this.src));
        sb.append(",cmd=").append(HexDump.toHex(this.cmd));
        sb.append(",bodylen=").append(getIntBodylen());
        sb.append(",attrlen=").append(getHeadAttrLen());
        if (this.hmAttr.size() > 0) {
            sb.append(",attributes:");
            for (HeadAttribute attr : this.hmAttr.values()) {
                sb.append(attr);
            }
        }
        return sb.toString();
    }

    class HeadAttribute {
        public byte id;
        public short len;
        public byte[] attr;

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(";att.id=").append(HexDump.toHex(this.id));
            sb.append(",att.len=").append(this.len);
            String str = "";
            try {
                str = new String(this.attr);
            } catch (Exception exp) {
                str = HexDump.hexDumpCompact(this.attr, 0, this.attr.length);
            }
            sb.append(",att.str=").append(str);
            return sb.toString();
        }
    }
}