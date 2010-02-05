package com.hisun.framework.handler;

import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;

public class HiPutMsgHandler implements IHandler {
    public void process(HiMessageContext ctx) {
        HiMessage msg = ctx.getCurrentMsg();

        Object header = msg.getHead();
        synchronized (header) {
            msg.setHeadItem("_MSG_RECVED", "1");
            header.notify();
        }
    }
}