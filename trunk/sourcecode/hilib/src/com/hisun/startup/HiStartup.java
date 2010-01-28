package com.hisun.startup;


import com.hisun.exception.HiException;
import com.hisun.framework.HiConfigParser;
import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.HiFrameworkBuilder;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.loader.HiClassLoaderFactory;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiResource;
import com.hisun.util.HiStringManager;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.HashMap;


public class HiStartup {
    private static HashMap startupMap = new HashMap();

    private static Logger log = HiLog.getLogger("SYS.trc");

    private static HiStringManager sm = HiStringManager.getManager();

    private HiDefaultServer server = null;

    private int count = 0;

    private boolean failured = false;
    private String serverName;
    private String appName;
    private boolean autoStart = false;
    private String configFile;
    private ClassLoader appLoader;


    public String getServerName() {

        return this.serverName;

    }


    public static HiStartup initialize(String serverName) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.initialize() - start");

        }


        HiStartup startup = null;


        synchronized (sm) {

            try {

                startup = getInstance(serverName);

                startup.init();

                startup.autoStart();

            } catch (HiException e) {

                if (startup != null) {

                    startup.setFailured(true);

                    try {

                        startup.destory();

                    } catch (HiException e1) {

                    }

                    startup = null;

                }

                e.addMsgStack("212009", serverName);

                throw e;

            }

        }


        if (log.isDebugEnabled()) {

            log.debug("HiStartup.initialize(String) - end");

        }

        return startup;

    }


    public static HiStartup initialize(String serverName, boolean start) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.initialize() - start");

        }

        HiStartup startup = null;


        synchronized (sm) {

            try {

                startup = getInstance(serverName);

                startup.init();

                startup.autoStart = start;

                startup.autoStart();

            } catch (HiException e) {

                if (startup != null) {

                    startup.setFailured(true);

                    try {

                        startup.destory();

                    } catch (HiException e1) {

                    }

                    startup = null;

                }

                e.addMsgStack("212009", serverName);

                throw e;

            }

        }


        if (log.isDebugEnabled()) {

            log.debug("HiStartup.initialize(String) - end");

        }

        return startup;

    }


    public static HiStartup getInstance(String serverName) {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.getInstance(String) - start");

        }


        HiStartup startup = null;

        if ((startup = (HiStartup) startupMap.get(serverName)) == null) {

            startup = new HiStartup(serverName);

            startupMap.put(serverName, startup);

        }

        startup.count += 1;


        if (log.isDebugEnabled()) {

            log.debug("HiStartup.getInstance(String) - end");

        }

        return startup;

    }


    public HiStartup(String serverName) {

        this.serverName = serverName;

        this.configFile = "etc/" + serverName + "_ATR.XML";

    }


    public void setFailured(boolean failured) {

        this.failured = failured;

    }


    public boolean isFailured() {

        return this.failured;

    }


    public void init() throws HiException {

        ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();


        if (HiICSProperty.getWorkDir() == null) throw new HiException("212012", "HWORKDIR");

    }


    public void autoStart() throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.autoStart() - start");

        }


        if (this.failured) {

            if (log.isDebugEnabled()) {

                log.debug("HiStartup.autoStart() - end");

            }

            return;

        }

        if (!(this.autoStart)) {

            if (log.isDebugEnabled()) {

                log.debug("HiStartup.autoStart() - end");

            }

            return;

        }


        start();


        if (log.isDebugEnabled()) log.debug("HiStartup.autoStart() - end");

    }


    public void destory() throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("destory() - start");

        }


        synchronized (sm) {

            if (log.isDebugEnabled()) {

                log.debug(sm.getString("HiStartup.destory", this.serverName, String.valueOf(this.count)));

            }

            if (--this.count != 0) {

                return;

            }

            stop();

        }


        if (log.isDebugEnabled()) log.debug("HiStartup.destory() - end");

    }


    public void start() throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.start() - start");

        }


        if (log.isInfoEnabled()) {

            log.info(sm.getString("HiStartup.start", this.serverName));

        }


        this.server = loadServer(this.configFile);

        this.server.init();

        this.server.start();


        HiServiceObject obj = new HiServiceObject(this.serverName);

        obj.setServerName(this.serverName);

        obj.setServerType(this.server.getType());

        obj.setLogLevel(this.server.getTrace());

        HiRegisterService.register(obj);


        if (log.isDebugEnabled()) log.debug("HiStartup.start() - end");

    }


    public void stop() throws HiException {

        if (log.isInfoEnabled()) {

            log.info("HiStartup.stop");

        }


        if (this.server == null) return;

        try {

            HiRegisterService.unregister(this.serverName);

        } catch (HiException e) {

        }

        this.server.stop();

        this.server.destroy();

        this.server = null;


        if (log.isDebugEnabled()) log.debug("HiStartup.stop() - end");

    }


    public void resume() throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.resume() - start");

        }


        if (this.server == null) {

            throw new HiException("212010");

        }


        this.server.start();


        if (log.isDebugEnabled()) log.debug("HiStartup.resume() - end");

    }


    public void pause() throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.pause() - start");

        }


        if (this.server == null) {

            return;

        }


        this.server.stop();


        if (log.isDebugEnabled()) log.debug("HiStartup.pause() - end");

    }


    public void reload() throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.reload() - start");

        }


        stop();

        start();


        if (log.isDebugEnabled()) log.debug("HiStartup.reload() - end");

    }


    public HiMessage process(HiMessage message) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.process(HiMessage) - start");

        }

        if (this.server == null) {

            throw new HiException("212010", this.serverName);

        }

        ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();


        Thread.currentThread().setContextClassLoader(this.appLoader);


        Logger log1 = HiLog.getLogger(message);

        if (log1.isDebugEnabled()) {

            log1.debug(sm.getString("HiStartup.process", this.server.getType(), this.server.getName()));

        }

        HiMessageContext ctx = new HiMessageContext();

        ctx.setCurrentMsg(message);


        HiMessage msg = null;

        try {

            this.server.process(ctx);

            msg = ctx.getCurrentMsg();

        } finally {

            Thread.currentThread().setContextClassLoader(oldLoader);


            ctx.clear();

            ctx = null;

        }

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.process(HiMessage) - end");

        }

        return msg;

    }


    public void load(String serverName, String configFile) throws HiException {

        this.serverName = serverName;

        this.configFile = configFile;

        init();

        start();

    }


    public synchronized HiMessage manage(HiMessage message) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.manange(HiMessage) - start");

        }


        String cmd = message.getHeadItem("CMD");

        HiETF etf = (HiETF) message.getBody();

        this.appName = etf.getChildValue("APP_NM");

        this.serverName = etf.getChildValue("SERVER");

        this.configFile = etf.getChildValue("CONFIG_FILE");

        if (log.isDebugEnabled()) log.debug(sm.getString("HiStartup.manange", message));

        if (StringUtils.equalsIgnoreCase(cmd, "START")) {

            if (this.server == null) load(message);

            else reload();

        } else if (StringUtils.equalsIgnoreCase(cmd, "RESTART")) {

            if (this.server == null) load(message);

            else reload();

        } else if (StringUtils.equalsIgnoreCase(cmd, "STOP")) {

            if (this.server == null) {

                throw new HiException("212010", this.serverName);

            }

            stop();

        } else if (StringUtils.equalsIgnoreCase(cmd, "PAUSE")) {

            if (this.server == null) {

                throw new HiException("212010", this.serverName);

            }

            pause();

        } else if (StringUtils.equalsIgnoreCase(cmd, "RESUME")) {

            if (this.server == null) {

                throw new HiException("212010", this.serverName);

            }

            resume();

        }

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.manange(HiMessage) - end");

        }

        return message;

    }


    private void load(HiMessage message) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.load(HiMessage) - start");

        }


        if (this.server != null) {

            throw new HiException("212011", this.server.getName());

        }


        HiETF etf = (HiETF) message.getBody();

        this.serverName = etf.getChildValue("SERVER");

        this.configFile = etf.getChildValue("CONFIG_FILE");


        init();

        start();


        if (log.isDebugEnabled()) log.debug("HiStartup.load(HiMessage) - end");

    }


    private HiDefaultServer loadServer(String file) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.loadFile() - start");

        }

        HiICSProperty.reload();

        ClassLoader oldLoader = null;

        InputStream is = null;

        HiDefaultServer server = null;

        try {

            oldLoader = Thread.currentThread().getContextClassLoader();

            if (this.appLoader != null) {

                Thread.currentThread().setContextClassLoader(this.appLoader);

                PropertyUtils.clearDescriptors();

            }

            this.appLoader = HiClassLoaderFactory.createClassLoader("application", this.appName, oldLoader);


            if (log.isDebugEnabled()) {

                log.debug("appLoader:" + this.appLoader);

            }

            Thread.currentThread().setContextClassLoader(this.appLoader);

            is = HiResource.getResourceAsStream(file);

            if (is == null) {

                throw new HiException("212004", file);

            }


            HiConfigParser parser = HiFrameworkBuilder.getParser();

            server = parser.parseServerXML(is);

            parser = null;

        } catch (HiException e) {

            server = null;


            throw e;

        } finally {

            try {

                if (is != null) {

                    is.close();

                    is = null;

                }

                Thread.currentThread().setContextClassLoader(oldLoader);

            } catch (Exception e) {

            }

        }

        if (log.isDebugEnabled()) {

            log.debug("HiStartup.loadFile(String) - end");

        }

        return server;

    }

}