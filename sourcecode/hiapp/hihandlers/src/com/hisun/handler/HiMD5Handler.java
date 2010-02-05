package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import org.apache.commons.codec.digest.DigestUtils;

public class HiMD5Handler implements IHandler {
    public void process(HiMessageContext ctx) throws HiException {
        HiByteBuffer buffer = (HiByteBuffer) ctx.getCurrentMsg().getBody();

        byte[] data = DigestUtils.md5(buffer.getBytes());
        buffer.append(data);
        ctx.getCurrentMsg().setBody(buffer);
    }
}