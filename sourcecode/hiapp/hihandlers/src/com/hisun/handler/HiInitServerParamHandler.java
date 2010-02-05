package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.message.HiContext;
import com.hisun.util.HiSysParamParser;

public class HiInitServerParamHandler implements IServerInitListener {
    public void serverInit(ServerEvent arg0) throws HiException {
        HiContext ctx = arg0.getServerContext();
        HiSysParamParser.load(ctx, ctx.getStrProp("SVR.name"));
    }
}