package com.hzjbbis.fk.message.zj;

import com.hzjbbis.fk.utils.HexDump;

public class MessageZjHead {
    public byte flag1;
    public byte rtua_a1;
    public byte rtua_a2;
    public short rtua_b1b2;
    public int rtua;
    public byte msta;
    public byte fseq;
    public byte iseq;
    public byte flag2;
    public byte c_dir;
    public byte c_expflag;
    public byte c_func;
    public short dlen;
    public byte cs;
    public byte flag3;

    public MessageZjHead() {
        this.flag1 = 104;
        this.flag2 = 104;
        this.flag3 = 22;
        this.iseq = 0;
        this.c_dir = 0;
        this.c_expflag = 0;
        this.c_func = 0;
        this.dlen = 0;
        this.cs = 0;
        this.fseq = 0;
        this.msta = 1;
        this.rtua = 0;
        this.rtua_a1 = (this.rtua_a2 = 0);
        this.rtua_b1b2 = 0;
    }

    public void parseRtua() {
        if (this.rtua != 0) {
            return;
        }
        this.rtua |= (0xFF & this.rtua_a1) << 24;
        this.rtua |= (0xFF & this.rtua_a2) << 16;
        this.rtua |= (0xFF & this.rtua_b1b2) << 8;
        this.rtua |= 0xFF & this.rtua_b1b2 >> 8;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("[rtua=").append(HexDump.toHex(this.rtua));

        sb.append(",msta=").append(HexDump.toHex(this.msta));
        sb.append(",fseq=").append(HexDump.toHex(this.fseq));
        sb.append(",iseq=").append(HexDump.toHex(this.iseq));

        sb.append(",c_dir=").append(HexDump.toHex(this.c_dir));
        sb.append(",c_expflag=").append(HexDump.toHex(this.c_expflag));
        sb.append(",c_func=").append(HexDump.toHex(this.c_func));
        sb.append(",datalen=").append(HexDump.toHex(this.dlen));
        sb.append("]");
        return sb.toString();
    }
}