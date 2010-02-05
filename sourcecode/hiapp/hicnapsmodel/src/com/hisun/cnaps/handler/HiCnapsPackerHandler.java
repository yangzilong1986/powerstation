package com.hisun.cnaps.handler;

import com.hisun.cnaps.common.HiCnapsDataTypeHelper;
import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;

public class HiCnapsPackerHandler implements IHandler {
    final Logger log;

    public HiCnapsPackerHandler() {
        this.log = ((Logger) HiContext.getCurrentContext().getProperty("SVR.log"));
    }

    public void process(HiMessageContext context) throws HiException {
        HiByteBuffer buffer = (HiByteBuffer) context.getCurrentMsg().getBody();
        StringBuffer sb = new StringBuffer();
        sb.append(buffer.toString());
        String len = String.valueOf(buffer.getBytes().length);
        String realLength = HiCnapsDataTypeHelper.lFullByte(len, len.length(), 6, '0');
        sb.replace(3, 9, realLength);
        buffer = new HiByteBuffer(sb.toString().getBytes());
        context.getCurrentMsg().setBody(buffer);
    }
}