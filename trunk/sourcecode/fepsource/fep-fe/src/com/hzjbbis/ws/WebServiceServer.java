package com.hzjbbis.ws;

import com.hzjbbis.fk.common.spi.IModule;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServiceServer implements IModule {
    private static final Logger log = Logger.getLogger(WebServiceServer.class);
    private int wsPort = 9002;

    Server server = null;

    public String getModuleType() {
        return "webService";
    }

    public String getName() {
        return "webService";
    }

    public String getTxfs() {
        return "ws";
    }

    public boolean isActive() {
        return ((this.server == null) || (!(this.server.isRunning())));
    }

    public boolean start() {
        this.server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(this.wsPort);
        this.server.setConnectors(new Connector[]{connector});

        WebAppContext webappcontext = new WebAppContext();
        webappcontext.setContextPath("/");

        webappcontext.setWar("webapp");

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[]{webappcontext, new DefaultHandler()});

        this.server.setHandler(handlers);

        boolean ret = false;
        try {
            this.server.start();
            ret = true;
        } catch (Exception e) {
            log.warn("sever start exception:" + e.getLocalizedMessage(), e);
        }
        log.info("WebService Server started，port=" + this.wsPort);
        return ret;
    }

    public void stop() {
        if (!(isActive())) return;
        try {
            this.server.stop();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public long getLastReceiveTime() {
        return 0L;
    }

    public long getLastSendTime() {
        return 0L;
    }

    public int getMsgRecvPerMinute() {
        return 0;
    }

    public int getMsgSendPerMinute() {
        return 0;
    }

    public long getTotalRecvMessages() {
        return 0L;
    }

    public long getTotalSendMessages() {
        return 0L;
    }

    public String profile() {
        return "";
    }

    public final int getWsPort() {
        return this.wsPort;
    }

    public final void setWsPort(int wsPort) {
        this.wsPort = wsPort;
    }
}