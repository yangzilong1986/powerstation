package com.hisun.framework;


import com.hisun.exception.HiException;
import com.hisun.framework.event.*;
import com.hisun.framework.filter.SetContextFilter;
import com.hisun.framework.imp.HiDefaultProcess;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.pubinterface.IHandlerFilter;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;
import com.hisun.util.HiStringManager;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;


public class HiDefaultServer implements IServer {
    Logger log;
    public static final HiStringManager sm = HiStringManager.getManager();
    private Map declares;
    private Map processes;
    private Map processesbyname;
    private boolean isRunning;
    private boolean isPause;
    private String name;
    private String type;
    private String trace;
    private HiContext serverContext;
    private List filters;
    private IHandler procHandler;


    public HiDefaultServer() {

        this.declares = new LinkedHashMap();


        this.processes = new HashMap();


        this.processesbyname = new HashMap();

    }


    public String getName() {

        return this.name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getType() {

        return this.type;

    }


    public void setType(String type) {

        this.type = type;

    }


    public String getTrace() {

        return this.trace;

    }


    public void setTrace(String trace) {

        this.trace = trace;

    }


    public HiContext getServerContext() {

        return this.serverContext;

    }


    public void addDeclare(String name, Object declare) throws Exception {

        if (this.log.isDebugEnabled()) {

            this.log.debug("addDeclare() - start:" + name);

        }


        try {

            PropertyUtils.setProperty(declare, "server", this);

        } catch (Exception e) {

        }

        try {

            PropertyUtils.setProperty(declare, "log", this.log);

        } catch (Exception e) {

        }

        try {

            if (declare instanceof IServerInitListener) {

                IServerInitListener init = (IServerInitListener) declare;

                init.serverInit(new ServerEvent(this, this.log, this.serverContext));

            }

            if (this.log.isInfoEnabled()) this.log.info(sm.getString("HiDefaultServer.init00", name));

        } catch (HiException e) {

            HiLog.logServerError(this.name, null, e);

            destroy();

            throw e;

        }


        this.declares.put(name, declare);


        if (declare instanceof IHandlerFilter) {

            addFilter((IHandlerFilter) declare);

        }


        if (this.log.isDebugEnabled()) this.log.debug("addDeclare() - end:" + name);

    }


    public Object getDeclare(String name) {

        return this.declares.get(name);

    }


    public void addProcess(HiDefaultProcess process) {

        if (this.log.isDebugEnabled()) {

            this.log.debug("addProcess() - start:" + process.getName());

        }


        if (StringUtils.equalsIgnoreCase("default", process.getMsgtype())) this.processes.put("default", process);

        else {

            this.processes.put(process.getMsgtype(), process);

        }

        this.processesbyname.put(process.getName(), process);


        if (this.log.isDebugEnabled()) this.log.debug("addProcess() - end:" + process.getName());

    }


    public HiDefaultProcess getProcess(String msgtype) {

        if (this.log.isDebugEnabled()) {

            this.log.debug("getProcess() - start");

        }


        HiDefaultProcess returnHiDefaultProcess = (HiDefaultProcess) this.processes.get(msgtype);


        if (this.log.isDebugEnabled()) {

            this.log.debug("getProcess() - end");

        }

        return returnHiDefaultProcess;

    }


    public HiDefaultProcess getProcessByName(String name) {

        if (this.log.isDebugEnabled()) {

            this.log.debug("getProcessByName() - start");

        }


        HiDefaultProcess returnHiDefaultProcess = (HiDefaultProcess) this.processesbyname.get(name);


        if (this.log.isDebugEnabled()) {

            this.log.debug("getProcess() - end");

        }

        return returnHiDefaultProcess;

    }


    public void startBuild() {

        this.log = HiLog.getLogger(this.name + ".trc");


        if (this.trace != null) {

            this.log.setLevel(Logger.toLevel(this.trace));

        }

        this.serverContext = HiContext.createContext("SVR." + this.name, null);


        this.serverContext.setProperty("SVR.type", this.type, true);

        this.serverContext.setProperty("SVR.name", this.name, true);

        this.serverContext.setProperty("SVR.log", this.log, true);

        this.serverContext.setProperty("SVR.server", this, true);


        HiContext.pushCurrentContext(this.serverContext);


        if (this.log.isDebugEnabled())
            this.log.debug("server [" + this.name + "] start build! [name:" + this.name + ",trace:" + this.trace + "]");

    }


    public void endBuild() {

        if (this.log.isDebugEnabled()) {

            this.log.debug("server [" + this.name + "] end build!");

        }

        HiContext.popCurrentContext();

        HiContext.removeCurrentContext();

    }


    public Logger getLog() {

        return this.log;

    }


    public boolean isRunning() {

        return this.isRunning;

    }


    public boolean isPaused() {

        return this.isPause;

    }


    public void init() throws HiException {

    }


    public void destroy() {

        if (this.log.isDebugEnabled()) {

            this.log.debug("destroy() - start");

        }

        if (this.declares == null) {

            return;

        }

        ServerEvent event = new ServerEvent(this, this.log, this.serverContext);

        for (Iterator it = this.declares.keySet().iterator(); it.hasNext();) {

            String key = (String) it.next();

            Object declare = this.declares.get(key);

            if (declare instanceof IServerDestroyListener) {

                try {

                    ((IServerDestroyListener) declare).serverDestroy(event);

                    if (this.log.isInfoEnabled()) this.log.info("destroy Declare Object : " + key);

                } catch (HiException e) {

                    HiLog.logServerError(this.name, null, e);

                }


            }


        }


        this.declares.clear();

        this.declares = null;

        if (this.serverContext != null) {

            this.serverContext.clear();

            this.serverContext = null;

        }

        if (this.log.isInfoEnabled()) this.log.info(sm.getString("HiDefaultServer.destroy01", this.name));

    }


    public void start() throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("start() - start");

        }


        if (isRunning()) {

            return;

        }

        ServerEvent event = new ServerEvent(this, this.log, this.serverContext);

        for (Iterator it = this.declares.keySet().iterator(); it.hasNext();) {

            String key = (String) it.next();

            Object declare = this.declares.get(key);

            if (declare instanceof IServerStartListener) {

                try {

                    ((IServerStartListener) declare).serverStart(event);

                    if (this.log.isInfoEnabled()) this.log.info("start Declare Object : " + key);

                } catch (HiException e) {

                    HiLog.logServerError(this.name, null, e);

                    throw e;

                }

            }

        }


        this.isRunning = true;

        if (this.log.isInfoEnabled()) this.log.info(sm.getString("HiDefaultServer.start01", this.name, this.type));

    }


    public void stop() throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("stop() - start");

        }


        if (!(isRunning())) {

            return;

        }

        ServerEvent event = new ServerEvent(this, this.log, this.serverContext);

        for (Iterator it = this.declares.keySet().iterator(); it.hasNext();) {

            String key = (String) it.next();

            Object declare = this.declares.get(key);

            if (declare instanceof IServerStopListener) {

                try {

                    ((IServerStopListener) declare).serverStop(event);

                    if (this.log.isInfoEnabled()) this.log.info("stop Declare Object : " + key);

                } catch (HiException e) {

                    HiLog.logServerError(this.name, null, e);

                    throw e;

                }

            }

        }

        this.isRunning = false;

        if (this.log.isInfoEnabled()) this.log.info(sm.getString("HiDefaultServer.stop01", this.name, this.type));

    }


    public void pause() {

        if (this.log.isDebugEnabled()) {

            this.log.debug("pause() - start");

        }


        if (!(isRunning())) return;

        if (this.isPause) {

            return;

        }

        ServerEvent event = new ServerEvent(this, this.log, this.serverContext);

        for (Iterator it = this.declares.keySet().iterator(); it.hasNext();) {

            String key = (String) it.next();

            Object declare = this.declares.get(key);

            if (declare instanceof IServerPauseListener) {

                ((IServerPauseListener) declare).serverPause(event);

                if (this.log.isInfoEnabled()) {

                    this.log.info(sm.getString("HiDefaultServer.pause00", key));

                }

            }

        }

        this.isPause = true;

        if (this.log.isInfoEnabled()) this.log.info(sm.getString("HiDefaultServer.pause01", this.name, this.type));

    }


    public void resume() {

        if (this.log.isDebugEnabled()) {

            this.log.debug("resume() - start");

        }


        if (!(isRunning())) return;

        if (!(this.isPause)) {

            return;

        }

        ServerEvent event = new ServerEvent(this, this.log, this.serverContext);

        for (Iterator it = this.declares.keySet().iterator(); it.hasNext();) {

            String key = (String) it.next();

            Object declare = this.declares.get(key);

            if (declare instanceof IServerResumeListener) {

                ((IServerResumeListener) declare).serverResume(event);

                if (this.log.isInfoEnabled()) {

                    this.log.info(sm.getString("HiDefaultServer.resume00", key));

                }

            }

        }

        this.isPause = false;

        if (this.log.isInfoEnabled()) this.log.info(sm.getString("HiDefaultServer.resume01", this.name, this.type));

    }


    public void process(HiMessageContext ctx) throws HiException {

        this.procHandler.process(ctx);

    }


    public void buildProcHandler() {

        IHandler handler = new procHandler();


        IHandlerFilter setCtxFilter = new SetContextFilter(this.serverContext);

        if (this.filters == null) addFilter(setCtxFilter);

        else {

            ((ArrayList) this.filters).add(0, setCtxFilter);

        }

        this.procHandler = ((IHandler) HiFrameworkBuilder.getPipelineBuilder().buildPipeline(this.filters, handler));

    }


    public void addFilter(IHandlerFilter filter) {

        if (this.filters == null) this.filters = new ArrayList();

        this.filters.add(filter);

    }


    class procHandler implements IHandler {

        public void process(HiMessageContext ctx) throws HiException {

            String errmsg;

            HiMessage msg = ctx.getCurrentMsg();


            HiServiceObject obj = HiRegisterService.getService(HiDefaultServer.this.name);

            if (!(obj.isRunning())) {

                errmsg = HiDefaultServer.sm.getString("HiDefaultServer.process01", msg.getRequestId(), HiDefaultServer.this.name, HiDefaultServer.this.type);


                HiDefaultServer.this.log.error(errmsg);

                throw new HiException("211003", errmsg);

            }

            HiDefaultServer.this.log.setLevel(Logger.toLevel(obj.getLogLevel()));

            if (!(HiDefaultServer.this.isRunning())) {

                errmsg = HiDefaultServer.sm.getString("HiDefaultServer.process01", msg.getRequestId(), HiDefaultServer.this.name, HiDefaultServer.this.type);


                HiDefaultServer.this.log.error(errmsg);

                throw new HiException("211003", errmsg);

            }


            String msgtype = msg.getType();

            HiDefaultProcess process = (HiDefaultProcess) HiDefaultServer.this.processes.get(msgtype);


            if (process == null) {

                process = (HiDefaultProcess) HiDefaultServer.this.processes.get("default");

            }


            if (process == null) {

                String errmsg = HiDefaultServer.sm.getString("HiDefaultServer.process02", msg.getRequestId(), HiDefaultServer.this.name, HiDefaultServer.this.type, msgtype);


                HiDefaultServer.this.log.error(errmsg);

                throw new HiException("211004", errmsg);

            }


            try {

                if (HiDefaultServer.this.log.isInfoEnabled()) {

                    HiDefaultServer.this.log.info(HiDefaultServer.sm.getString("HiDefaultServer.process03", msg.getRequestId(), msgtype, process.getName()));

                }


                process.process(ctx);

            } catch (HiException e) {

                HiLog.logServerError(HiDefaultServer.this.name, ctx.getCurrentMsg(), e);

                throw e;

            }

        }

    }

}