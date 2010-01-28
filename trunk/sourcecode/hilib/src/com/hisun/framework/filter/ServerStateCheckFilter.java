package com.hisun.framework.filter;


import com.hisun.exception.HiException;
import com.hisun.framework.HiDefaultServer;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.pubinterface.IHandlerFilter;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;
import com.hisun.util.HiStringManager;
import org.apache.log4j.Level;

public class ServerStateCheckFilter implements IHandlerFilter {
    public static final HiStringManager sm = HiStringManager.getManager();
    private final HiDefaultServer _server;

    public ServerStateCheckFilter(HiDefaultServer server) {

        this._server = server;
    }

    public void process(HiMessageContext ctx, IHandler handler) throws HiException {

        HiMessage msg = ctx.getCurrentMsg();

        HiServiceObject serviceObject = HiRegisterService.getService(this._server.getName());


        if (!(serviceObject.isRunning())) {

            String errmsg = sm.getString("HiDefaultServer.process01", msg.getRequestId(), this._server.getName(), this._server.getType());


            this._server.getLog().error(errmsg);

            ctx.popParent();

            throw new HiException("211003", errmsg);
        }


        if (serviceObject.getLogLevel() != null) {

            this._server.getLog().setLevel(Level.toLevel(serviceObject.getLogLevel()));
        }


        handler.process(ctx);
    }
}