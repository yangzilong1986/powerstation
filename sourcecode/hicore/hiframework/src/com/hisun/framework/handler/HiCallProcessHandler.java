package com.hisun.framework.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.imp.HiDefaultProcess;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiStringManager;

public class HiCallProcessHandler implements IHandler {
    private String param;
    private Logger log;
    private static HiStringManager sm = HiStringManager.getManager();
    private HiDefaultServer server;

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();

        String processname = this.param;
        if (processname == null) {
            processname = (String) ctx.getProperty("_SUBPROCESS");
        }
        HiDefaultProcess process = this.server.getProcessByName(processname);

        if (process == null) {
            String errmsg = sm.getString("HiCallProcessHandler.err", msg.getRequestId(), this.param);

            throw new HiException("211004", errmsg);
        }

        process.process(ctx);
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setLog(Logger log) {
        this.log = log;

        this.server = ((HiDefaultServer) HiContext.getCurrentContext().getProperty("SVR.server"));
    }
}