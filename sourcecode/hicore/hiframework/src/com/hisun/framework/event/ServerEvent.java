package com.hisun.framework.event;

import com.hisun.framework.IServer;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;

public class ServerEvent {
    private static final long serialVersionUID = -2768663865535425218L;
    private IServer server;
    private Logger log;
    private HiContext serverContext;

    public ServerEvent(IServer server, Logger log, HiContext ctx) {
        this.server = server;
        this.log = log;
        this.serverContext = ctx;
    }

    public IServer getServer() {
        return this.server;
    }

    public Logger getLog() {
        return this.log;
    }

    public HiContext getServerContext() {
        return this.serverContext;
    }
}