package com.hisun.atmp.tcp;

import com.hisun.exception.HiException;
import com.hisun.framework.event.*;
import com.hisun.message.HiMessageContext;
import com.hisun.protocol.tcp.HiMessageInOut;
import com.hisun.util.HiThreadPool;

public class HiTcpSynListener3 implements IServerInitListener, IServerStartListener, IServerDestroyListener, IServerStopListener, IServerPauseListener {
    private HiThreadPool _threadpool;
    private HiMessageInOut msginout;
    private HiSocketPool _socketpool;

    public HiTcpSynListener3() {
        this.msginout = new HiMessageInOut();
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        this._threadpool = HiThreadPool.createThreadPool();
    }

    public void process(HiMessageContext ctx) {
    }

    public void serverDestroy(ServerEvent arg0) throws HiException {
    }

    public void serverStop(ServerEvent arg0) throws HiException {
    }

    public void serverPause(ServerEvent arg0) {
    }

    public void serverStart(ServerEvent arg0) throws HiException {
    }
}