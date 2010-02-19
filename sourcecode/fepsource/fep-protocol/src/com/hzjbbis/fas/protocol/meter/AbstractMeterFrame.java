package com.hzjbbis.fas.protocol.meter;

public abstract class AbstractMeterFrame {
    protected int start;
    protected int len;
    protected byte[] data;

    public AbstractMeterFrame() {
        this(0, -1, new byte[0]);
    }

    public AbstractMeterFrame(int start, int len, byte[] data) {
        this.data = data;
        this.len = len;
        this.start = start;
    }

    public abstract void parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

    public byte[] getData() {
        return this.data;
    }

    public int getLen() {
        return this.len;
    }

    public int getStart() {
        return this.start;
    }

    public void clear() {
        this.data = null;
        this.start = 0;
        this.len = 0;
    }
}