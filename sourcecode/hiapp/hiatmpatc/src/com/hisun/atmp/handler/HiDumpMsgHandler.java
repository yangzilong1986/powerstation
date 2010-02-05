package com.hisun.atmp.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;

public class HiDumpMsgHandler implements IHandler, IServerInitListener {
    private Logger _serverLog;

    public HiDumpMsgHandler() {
        this._serverLog = null;
    }

    public void process(HiMessageContext arg0) throws HiException {
        this._serverLog.error(arg0.getCurrentMsg());
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        this._serverLog = arg0.getLog();
    }
}