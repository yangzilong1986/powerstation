package com.hisun.parse8583;

import java.io.ByteArrayInputStream;

public class HiByteArrayInputStream extends ByteArrayInputStream {
    public HiByteArrayInputStream(byte[] buf) {
        super(buf);
    }

    public int getPos() {
        return this.pos;
    }
}