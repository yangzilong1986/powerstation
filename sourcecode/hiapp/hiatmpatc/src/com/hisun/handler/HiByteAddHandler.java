package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;

public class HiByteAddHandler implements IHandler {
    private String key;

    public void process(HiMessageContext ctx) throws HiException {
        HiByteBuffer buffer = (HiByteBuffer) ctx.getCurrentMsg().getBody();

        byte[] head = null;
        Object obj = ctx.getCurrentMsg().getObjectHeadItem(this.key);
        if (obj instanceof String) head = ((String) obj).getBytes();
        else {
            head = (byte[]) (byte[]) obj;
        }
        byte[] data = buffer.getBytes();

        HiByteBuffer buf = new HiByteBuffer(head);
        buf.append(data);

        ctx.getCurrentMsg().setBody(buf);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}