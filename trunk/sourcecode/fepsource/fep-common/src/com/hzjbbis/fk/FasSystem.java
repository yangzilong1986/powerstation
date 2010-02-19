package com.hzjbbis.fk;

import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.events.event.MemoryProfileEvent;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IClientModule;
import com.hzjbbis.fk.common.spi.IEventHook;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.common.spi.IProfile;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;

import java.nio.charset.Charset;
import java.util.*;

public class FasSystem {
    private static FasSystem fasSystem = null;
    public static final String name = "fasSystem";
    private static final Logger log = Logger.getLogger(FasSystem.class);
    private List<IModule> modules;
    private List<IModule> unMonitoredModules;
    private List<IEventHook> eventHooks;
    private boolean testMode = false;
    private List<Object> globalObjects;
    private MemoryProfileEvent memProfile = new MemoryProfileEvent();
    private List<IProfile> profileObjects;
    private boolean dbAvailable = false;

    private Map<String, String> systemsProfile = new HashMap();
    private int waitCount = -1;
    private String osProfile = null;

    private final List<Runnable> shutdownHooks = new ArrayList();
    private ApplicationContext applicationContext;
    private boolean running = false;

    public boolean isTestMode() {
        return this.testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public static FasSystem getFasSystem() {
        if (fasSystem == null) fasSystem = new FasSystem();
        return fasSystem;
    }

    public FasSystem() {
        fasSystem = this;
    }

    public void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                FasSystem.log.info("Run shutdownHooks");
                for (Runnable runIt : FasSystem.this.shutdownHooks)
                    try {
                        runIt.run();
                    } catch (Exception e) {
                        FasSystem.log.warn("shutdownHook exception:" + e.getLocalizedMessage(), e);
                    }
            }
        });
    }

    public void showProfile() {
        if (!(this.running)) return;
        TraceLog.getTracer().trace(this.memProfile.profile());
        for (IModule module : this.modules)
            try {
                TraceLog.getTracer().trace(module.profile());
            } catch (Exception localException) {
            }
        for (IEventHook hook : this.eventHooks)
            try {
                TraceLog.getTracer().trace(hook.profile());
            } catch (Exception localException1) {
            }
    }

    public List<IModule> getModules() {
        return this.modules;
    }

    public void setModules(List<IModule> modules) {
        this.modules = modules;
    }

    public List<IEventHook> getEventHooks() {
        return this.eventHooks;
    }

    public void setEventHooks(List<IEventHook> eventHooks) {
        this.eventHooks = eventHooks;
    }

    public void addModule(IModule module) {
        if (this.modules == null) this.modules = new ArrayList();
        this.modules.remove(module);
        this.modules.add(module);
    }

    public void addEventHook(IEventHook hook) {
        if (this.eventHooks == null) this.eventHooks = new ArrayList();
        this.eventHooks.remove(hook);
        this.eventHooks.add(hook);
    }

    public boolean startModule(String name) {
        if (this.modules == null) return false;
        for (IModule module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) return module.start();
        }
        return false;
    }

    public boolean stopModule(String name) {
        if (this.modules == null) return false;
        for (IModule module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                module.stop();
                return true;
            }
        }
        return false;
    }

    public void startSystem() {
        IModule module;
        Iterator localIterator;
        if ((this.modules == null) || (this.eventHooks == null)) {
            log.fatal("系统没有配置任何模块或者事件处理模块。死翘翘！");
            stopSystem();
            return;
        }
        if (this.unMonitoredModules != null) {
            for (localIterator = this.unMonitoredModules.iterator(); localIterator.hasNext();) {
                module = (IModule) localIterator.next();
                try {
                    module.start();
                } catch (Exception e) {
                    log.warn("unMonitoredModules start exception:" + e.getLocalizedMessage(), e);
                }
            }
        }
        if (this.eventHooks != null) {
            for (IEventHook hook : this.eventHooks) {
                try {
                    hook.start();
                } catch (Exception e) {
                    log.warn("eventHooks start exception:" + e.getLocalizedMessage(), e);
                }
            }
        }

        if (this.modules != null) {
            for (localIterator = this.modules.iterator(); localIterator.hasNext();) {
                module = (IModule) localIterator.next();
                try {
                    module.start();
                } catch (Exception e) {
                    log.warn("modules start exception:" + e.getLocalizedMessage(), e);
                }
            }
        }
        this.running = true;
        log.info("startSystem successfully!");
    }

    public void stopSystem() {
        IModule module;
        Iterator localIterator;
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.shutdown();
        } catch (Exception e) {
            log.warn("Quartz default schedule shutdown error:" + e.getLocalizedMessage(), e);
        }

        log.info("stopSystem() is called.");
        if (this.modules != null) {
            for (localIterator = this.modules.iterator(); localIterator.hasNext();) {
                module = (IModule) localIterator.next();
                try {
                    module.stop();
                } catch (Exception e) {
                    log.warn("modules stop exception:" + e.getLocalizedMessage(), e);
                }
            }
        }

        if (this.eventHooks != null) {
            for (IEventHook hook : this.eventHooks) {
                try {
                    hook.stop();
                } catch (Exception e) {
                    log.warn("event hook stop exception: " + e.getLocalizedMessage(), e);
                }
            }
        }

        if (this.unMonitoredModules != null) {
            for (localIterator = this.unMonitoredModules.iterator(); localIterator.hasNext();) {
                module = (IModule) localIterator.next();
                try {
                    module.stop();
                } catch (Exception e) {
                    log.warn("unMonitoredModules stop exception: " + e.getLocalizedMessage(), e);
                }
            }

        }

        try {
            Thread.sleep(1000L);
        } catch (Exception localException1) {
        }
        try {
            TimerScheduler.getScheduler().destroy();
            GlobalEventHandler.getInstance().destroy();
        } catch (Exception e) {
            log.warn("shutdown timerScheduler or global event handler exception:" + e.getLocalizedMessage(), e);
        }

        this.running = false;
        log.info("System stopped successfully !");
    }

    public String getProfile(String type) {
        if ("module".equalsIgnoreCase(type)) return getModuleProfile();
        if ("eventhook".equalsIgnoreCase(type)) return getEventHookProfile();
        if ("gather".equalsIgnoreCase(type)) {
            return gatherSystemsProfile();
        }
        return getProfile();
    }

    public String getProfile() {
        StringBuffer sb = new StringBuffer(2048);
        sb.append("<?xml version=\"1.0\" encoding=\"");
        sb.append(Charset.defaultCharset().name()).append("\"?>\r\n");
        sb.append("<root>");
        if (this.osProfile != null) {
            sb.append("\r\n    ").append(this.osProfile);
        } else {
            sb.append("\r\n    ").append(this.memProfile.profile());
        }
        if (this.profileObjects != null) {
            for (IProfile profile : this.profileObjects) {
                sb.append("\r\n    ").append(profile.profile());
            }
        }
        for (IModule mod : this.modules) {
            sb.append("\r\n    ").append(mod.profile());
        }
        for (IEventHook hook : this.eventHooks)
            sb.append("\r\n    ").append(hook.profile());
        sb.append("\r\n    <dbAvailable>").append(this.dbAvailable).append("</dbAvailable>");
        sb.append("\r\n").append("</root>");
        return sb.toString();
    }

    public String getModuleProfile() {
        StringBuffer sb = new StringBuffer(2048);
        sb.append("<root>");
        for (IModule mod : this.modules) {
            sb.append("\r\n    ").append(mod.profile());
        }
        sb.append("\r\n    ").append(this.memProfile.profile());
        sb.append("\r\n").append("</root>");
        return sb.toString();
    }

    public String getEventHookProfile() {
        StringBuffer sb = new StringBuffer(2048);
        sb.append("<root>");
        for (IEventHook hook : this.eventHooks)
            sb.append("\r\n    ").append(hook.profile());
        sb.append("\r\n").append("</root>");
        return sb.toString();
    }

    public List<Object> getGlobalObjects() {
        return this.globalObjects;
    }

    public void setGlobalObjects(List<Object> globalObjects) {
        this.globalObjects = globalObjects;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    public List<IModule> getUnMonitoredModules() {
        return this.unMonitoredModules;
    }

    public void setUnMonitoredModules(List<IModule> unMonitoredModules) {
        if (this.unMonitoredModules != null) {
            this.unMonitoredModules.addAll(unMonitoredModules);
        } else this.unMonitoredModules = unMonitoredModules;
    }

    public void addUnMonitoredModules(IModule module) {
        if (this.unMonitoredModules == null) {
            this.unMonitoredModules = new ArrayList();
        }
        this.unMonitoredModules.add(module);
    }

    public synchronized String gatherSystemsProfile() {
        if (this.waitCount != -1) return null;
        synchronized (this.systemsProfile) {
            this.systemsProfile.clear();
            this.waitCount = 0;
        }
        for (IModule mod : this.modules) {
            if ((mod.getModuleType().equals("gprsClient")) && (mod instanceof IClientModule)) {
                try {
                    IClientModule gprsClient = (IClientModule) mod;
                    if (gprsClient.sendMessage(MessageGate.createMoniteProfileRequest())) this.waitCount += 1;
                } catch (Exception e) {
                    log.warn(e.getLocalizedMessage(), e);
                }
            } else {
                if ((!(mod.getModuleType().equals("businessProcessor"))) || (!(mod instanceof ISocketServer))) continue;
                for (IServerSideChannel client : ((ISocketServer) mod).getClients()) {
                    if (client.send(MessageGate.createMoniteProfileRequest())) {
                        this.waitCount += 1;
                    }
                }
            }
        }
        int sleepCount = 20;
        while ((this.systemsProfile.size() < this.waitCount) && (sleepCount > 0)) try {
            Thread.sleep(100L);
            --sleepCount;
        } catch (Exception e) {
        }
        addFrontEndProfile(getProfile());
        StringBuffer sb = new StringBuffer(30720);
        sb.append("<?xml version=\"1.0\" encoding=\"");
        sb.append(Charset.defaultCharset().name());
        sb.append("\"?>\r\n");
        sb.append("<multi-system>\r\n");
        synchronized (this.systemsProfile) {
            Iterator iter = this.systemsProfile.values().iterator();
            while (iter.hasNext()) {
                String prof = (String) iter.next();
                sb.append(prof);
            }
        }
        sb.append("</multi-system>");
        this.waitCount = -1;
        return sb.toString();
    }

    private void addSystemProfile(String sysType, String sysId, String profile) {
        if (this.waitCount == -1) return;
        int pos0 = profile.indexOf("<root>\r\n");
        if (pos0 < 0) return;
        pos0 += 8;
        int pos1 = profile.indexOf("</root>");
        if (pos1 < 0) return;
        profile = profile.substring(pos0, pos1);
        StringBuffer sb = new StringBuffer(10240);
        sb.append("    <system type=\"").append(sysType).append("\" id=\"").append(sysId).append("\">\r\n");
        String[] lines = profile.split("\r\n");
        for (String line : lines) {
            if (line.length() > 7) sb.append("      ").append(line).append("\r\n");
        }
        sb.append("    </system>\r\n");
        synchronized (this.systemsProfile) {
            this.systemsProfile.put(sysId, sb.toString());
        }
    }

    public void addGprsGateProfile(String systemId, String profile) {
        addSystemProfile("gprsGate", systemId, profile);
    }

    public void addBizProcessorProfile(String systemId, String profile) {
        addSystemProfile("bp", systemId, profile);
    }

    private void addFrontEndProfile(String profile) {
        addSystemProfile("frontEnd", "fe", profile);
    }

    public final synchronized void setOsProfile(String osProfile) {
        this.osProfile = osProfile;
    }

    public final void setProfileObjects(List<IProfile> profileObjects) {
        this.profileObjects = profileObjects;
    }

    public final void addProfileObject(IProfile profileObject) {
        if (this.profileObjects == null) {
            this.profileObjects = new ArrayList();
        }
        this.profileObjects.add(profileObject);
    }

    public final boolean isDbAvailable() {
        return this.dbAvailable;
    }

    public final void setDbAvailable(boolean dbAvailable) {
        this.dbAvailable = dbAvailable;
    }

    public final void addShutdownHook(Runnable runIt) {
        this.shutdownHooks.remove(runIt);
        this.shutdownHooks.add(runIt);
    }
}