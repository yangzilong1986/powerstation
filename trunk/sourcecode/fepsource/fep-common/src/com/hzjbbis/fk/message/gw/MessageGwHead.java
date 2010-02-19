package com.hzjbbis.fk.message.gw;

public class MessageGwHead {
    public byte flag1 = 104;
    public byte flag2 = 104;
    public short dlen = 0;
    public byte proto_flag = 2;
    public int rtua = 0;
    public byte a3_d0 = 0;
    public byte a3_msa = 1;
    public byte c_dir = 0;
    public byte c_prm = 1;
    public byte c_acd = 0;
    public byte c_fcv = 0;
    protected byte c_func = 0;

    protected byte app_func = 0;
    public byte seq_tpv = 0;
    public byte seq_fir = 1;
    public byte seq_fin = 1;
    protected byte seq_con = 0;
    protected byte seq_pseq = 0;

    public void decodeL(short L) {
        this.proto_flag = (byte) (L & 0x3);
        this.dlen = (short) (L >>> 2);
    }

    public short encodeL() {
        int len = (this.dlen & 0xFFFF) << 2 | this.proto_flag;
        len = len << 8 & 0xFF00 | len >>> 8 & 0xFF;
        return (short) len;
    }

    public void decodeC(byte C) {
        this.c_dir = (byte) ((0x80 & C) >>> 7);
        this.c_prm = (byte) ((0x40 & C) >>> 6);
        this.c_acd = (byte) ((0x20 & C) >>> 5);
        this.c_fcv = (byte) ((0x10 & C) >>> 4);
        this.c_func = (byte) (0xF & C);
    }

    public byte encodeC() {
        int c = this.c_dir << 7;
        c |= this.c_prm << 6;
        c |= this.c_acd << 5;
        c |= this.c_fcv << 4;
        c |= this.c_func;
        return (byte) c;
    }

    public void decodeA3(byte A3) {
        this.a3_d0 = (byte) (0x1 & A3);
        this.a3_msa = (byte) (A3 >>> 1);
    }

    public byte encodeA3() {
        return (byte) (this.a3_msa << 1 | this.a3_d0);
    }

    public void decodeSEQ(byte SEQ) {
        this.seq_tpv = (byte) ((0x80 & SEQ) >>> 7);
        this.seq_fir = (byte) ((0x40 & SEQ) >>> 6);
        this.seq_fin = (byte) ((0x20 & SEQ) >>> 5);
        this.seq_con = (byte) ((0x10 & SEQ) >>> 4);
        this.seq_pseq = (byte) (0xF & SEQ);
    }

    public byte encodeSEQ() {
        int c = this.seq_tpv << 7;
        c |= this.seq_fir << 6;
        c |= this.seq_fin << 5;
        c |= this.seq_con << 4;
        c |= this.seq_pseq;
        return (byte) c;
    }
}