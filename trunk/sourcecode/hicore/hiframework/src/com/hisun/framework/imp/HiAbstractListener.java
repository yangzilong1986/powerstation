package com.hisun.framework.imp;

import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.event.*;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.util.HiStringManager;

public abstract class HiAbstractListener implements IServerInitListener, IServerStartListener, IServerStopListener, IServerDestroyListener, IServerPauseListener, IServerResumeListener {
    public static final HiStringManager sm = HiStringManager.getManager();
    private String name;
    private String msgType;
    private HiDefaultServer server;
    public Logger log;

    public HiDefaultServer getServer() {
        return this.server;
    }

    public void setServer(HiDefaultServer service) {
        this.server = service;
        this.log = this.server.getLog();
    }

    public String getName() {
        return this.name;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsgType(String process) {
        this.msgType = process;
    }

    public HiMessage getHiMessage() {
        HiMessage msg = new HiMessage(getServer().getName(), getMsgType());

        msg.setHeadItem("ECT", "text/plain");
        msg.setHeadItem("SCH", "rq");
        return msg;
    }

    public HiMessageContext getMessageContext(HiMessage msg) {
        HiMessageContext ctx = new HiMessageContext();
        ctx.setCurrentMsg(msg);
        return ctx;
    }
}