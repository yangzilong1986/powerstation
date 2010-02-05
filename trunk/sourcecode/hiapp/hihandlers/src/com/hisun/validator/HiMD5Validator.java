package com.hisun.validator;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import org.apache.commons.codec.digest.DigestUtils;

public class HiMD5Validator implements IHandler {
    public boolean validate(HiMessageContext ctx) {
        HiByteBuffer buffer = (HiByteBuffer) ctx.getCurrentMsg().getBody();
        int length = buffer.length();
        byte[] data1 = buffer.subbyte(length - 16, 16);
        byte[] data2 = DigestUtils.md5(buffer.subbyte(0, length - 16));
        int i = 0;
        if (data1.length != data2.length) {
            return false;
        }
        for (; i < data1.length; ++i) {
            if (data1[i] != data2[i]) {
                return false;
            }
        }
        return true;
    }

    public void process(HiMessageContext arg0) throws HiException {
    }
}