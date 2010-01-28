package com.hisun.framework.imp;


import com.hisun.exception.HiException;
import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.HiFrameworkBuilder;
import com.hisun.framework.filter.LogFilter;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.stat.util.HiStats;
import com.hisun.stat.util.IStat;
import com.hisun.util.HiStringManager;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class HiDefaultProcess implements IHandler {
    private final Logger log;
    private final HiStringManager sm = HiStringManager.getManager();
    private String name;
    private String msgtype;
    private List handlers = new ArrayList();
    private HiDefaultServer server;
    private String errprocess;
    private HiDefaultProcess errprocessObject;


    public HiDefaultProcess() {

        HiContext ctx = HiContext.getCurrentContext();

        this.server = ((HiDefaultServer) ctx.getProperty("SVR.server"));

        this.log = ((Logger) ctx.getProperty("SVR.log"));

    }


    public HiDefaultServer getServer() {

        return this.server;

    }


    public void setServer(HiDefaultServer server) {

        this.server = server;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getName() {

        return this.name;

    }


    public String getMsgtype() {

        return this.msgtype;

    }


    public void setMsgtype(String msgType) {

        this.msgtype = msgType;

    }


    public String getErrorprocess() {

        return this.errprocess;

    }


    public void setError(String errprocess) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("HiDefaultProcess.setErrhandler() - start" + errprocess);

        }


        this.errprocess = errprocess;


        if (this.log.isInfoEnabled()) {

            this.log.info(this.sm.getString("HiDefaultProcess.ErrHandler00", errprocess));

        }


        if (this.log.isDebugEnabled()) this.log.debug("HiDefaultProcess.setErrhandler() - end");

    }


    public void addHandler(String handler, String method) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("HiDefaultProcess.addHandler() - start");

        }


        Object objHandler = this.server.getDeclare(handler);

        if (StringUtils.isBlank(method)) {

            if (objHandler instanceof IHandler) {

                IStat stat = HiStats.getState(this.server.getName() + ":" + handler);

                LogFilter filter = new LogFilter("handler " + handler, this.log, stat);

                IHandler proxyHandler = (IHandler) HiFrameworkBuilder.getPipelineBuilder().buildPipeline(filter, (IHandler) objHandler);

                this.handlers.add(proxyHandler);

            }
            throw new HiException("Declare Object: " + handler + " is not a handler");

        }


        HiHandlerWapper wp = new HiHandlerWapper(handler, objHandler, method, this.log);

        IStat stat = HiStats.getState(this.server.getName() + ":" + handler);

        LogFilter filter = new LogFilter("handler " + handler, this.log, stat);

        IHandler proxyHandler = (IHandler) HiFrameworkBuilder.getPipelineBuilder().buildPipeline(filter, wp);


        this.handlers.add(proxyHandler);


        if (this.log.isDebugEnabled()) this.log.debug("HiDefaultProcess.addHandler() - end");

    }


    public int getHandlerNum() {

        int returnint = this.handlers.size();

        return returnint;

    }


    public void addSystemHandler(String handler, String param) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("HiDefaultProcess.addSystemHandler() - start" + handler + ":" + param);

        }


        IHandler objHandler = createSystemHandler(handler, param);

        if (objHandler instanceof IHandler) {

            IStat stat = null;

            stat = HiStats.getState(this.server.getName() + ":" + handler);

            LogFilter filter = new LogFilter("system handler " + handler, this.log, stat);

            IHandler proxyHandler = (IHandler) HiFrameworkBuilder.getPipelineBuilder().buildPipeline(filter, objHandler);


            this.handlers.add(proxyHandler);

        } else {

            throw new HiException("System handler: " + handler + " is not a handler");

        }


        if (this.log.isDebugEnabled()) this.log.debug("HiDefaultProcess.addSystemHandler() - end");

    }


    protected IHandler createSystemHandler(String name, String param) throws HiException {

        if (!(HiFrameworkBuilder.getHandlerFactory().contains(name))) {

            throw new HiException("211002", "can't create systemhandler :" + name);

        }


        IHandler handler = (IHandler) HiFrameworkBuilder.getHandlerFactory().get(name);


        if (param != null) {

            try {

                PropertyUtils.setProperty(handler, "param", param);

            } catch (Exception e) {

                throw new HiException("211002", "handler '" + name + "':" + e, e);

            }

        }


        try {

            PropertyUtils.setProperty(handler, "log", this.server.getLog());

        } catch (Exception e) {

        }

        return handler;

    }


    public void process(HiMessageContext ctx) throws HiException {

        Iterator i;

        if (this.log.isDebugEnabled()) {

            this.log.debug("HiDefaultProcess.process() - start:" + this.name);

        }


        HiMessage msg = ctx.getCurrentMsg();

        try {

            for (i = this.handlers.iterator(); i.hasNext();) {

                msg = ctx.getCurrentMsg();

                if (StringUtils.equals(msg.getHeadItem("RSP"), "0")) {

                    break;

                }

                IHandler handler = (IHandler) i.next();

                handler.process(ctx);

            }

        } catch (Throwable e) {

            if (this.errprocess != null) {

                try {

                    this.log.warn(e, e);


                    if (!(msg.hasHeadItem("SSC"))) {

                        msg.setHeadItem("SSC", "211007");

                        if (e instanceof HiException) {

                            HiException te = (HiException) e;

                            msg.setHeadItem("SSC", te.getCode());

                        }

                    }

                    HiDefaultProcess process = this.server.getProcessByName(this.errprocess);

                    if (process != null) process.process(ctx);

                } catch (HiException ee) {

                    ee.addMsgStack("211007", this.name);

                    HiLog.logServerError(getServer().getName(), ctx.getCurrentMsg(), ee);


                    throw ee;

                }

            }

            throw HiException.makeException("211007", this.name, e);

        }


        if (this.log.isDebugEnabled()) this.log.debug("HiDefaultProcess.process() - end:" + this.name);

    }


    public void init() throws HiException {

    }

}