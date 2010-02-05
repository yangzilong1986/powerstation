package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;

public class HiByteCutHandler implements IHandler {
    private int length;
    private String key;

    public void process(HiMessageContext ctx) throws HiException {
        HiByteBuffer buffer = (HiByteBuffer) ctx.getCurrentMsg().getBody();
        byte[] head = buffer.subbyte(0, this.length);
        byte[] data = buffer.subbyte(this.length, buffer.length() - this.length);

        ctx.getCurrentMsg().setHeadItem(this.key, head);
        ctx.getCurrentMsg().setBody(new HiByteBuffer(data));
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}