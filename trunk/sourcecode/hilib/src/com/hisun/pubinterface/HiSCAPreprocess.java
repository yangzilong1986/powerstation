package com.hisun.pubinterface;


import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.message.HiMessageHelper;

public abstract class HiSCAPreprocess {
    public void request(HiMessageContext ctx) throws HiException {

        HiMessage msg = ctx.getCurrentMsg();


        if ((!(HiMessageHelper.isOutterMessage(msg))) || (!(HiMessageHelper.isRequestMessage(msg)))) return;

        doRequest(ctx);
    }

    public void response(HiMessageContext ctx) throws HiException {

        HiMessage msg = ctx.getCurrentMsg();


        if ((!(HiMessageHelper.isOutterMessage(msg))) || (!(HiMessageHelper.isResponseMessage(msg)))) return;

        doResponse(ctx);
    }

    protected abstract void doRequest(HiMessageContext paramHiMessageContext) throws HiException;

    protected abstract void doResponse(HiMessageContext paramHiMessageContext) throws HiException;
}