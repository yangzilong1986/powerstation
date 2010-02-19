package com.hzjbbis.fk.fe;

import com.hzjbbis.db.DbMonitor;
import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.spi.IEventHook;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.fe.config.ApplicationPropertiesConfig;
import com.hzjbbis.fk.fe.fiber.FiberManage;
import com.hzjbbis.fk.fe.fiber.IFiber;
import com.hzjbbis.fk.utils.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;

public class FeCommunication {
    public static void main(String[] args) {
        IModule mod;
        String[] path = {"classpath*:applicationContext-common.xml", "classpath*:applicationContext-socket.xml", "classpath*:applicationContext-monitor.xml", "classpath*:applicationContext-db-batch.xml", "classpath*:applicationContext-ums.xml", "classpath*:applicationContext-fec.xml"};

        ApplicationContext context = new ClassPathXmlApplicationContext(path);
        ApplicationContextUtil.setContext(context);

        FasSystem fasSystem = (FasSystem) context.getBean("fasSystem");
        fasSystem.setApplicationContext(context);

        GateClientManage gateClients = GateClientManage.getInstance();

        ApplicationPropertiesConfig config = ApplicationPropertiesConfig.getInstance();
        config.parseConfig();
        gateClients.setGprsGateClients(config.getGprsClientModules());
        gateClients.setUmsClients(config.getUmsClientModules());

        fasSystem.setModules(new ArrayList());
        for (Iterator localIterator1 = config.getSocketServers().iterator(); localIterator1.hasNext();) {
            mod = (IModule) localIterator1.next();
            fasSystem.addModule(mod);
        }

        fasSystem.setEventHooks(new ArrayList());
        for (IEventHook hook : config.getEventHandlers()) {
            fasSystem.addEventHook(hook);
        }

        for (Iterator localIterator1 = gateClients.getGprsGateClients().iterator(); localIterator1.hasNext();) {
            mod = (IModule) localIterator1.next();
            fasSystem.addModule(mod);
        }
        for (Iterator localIterator1 = gateClients.getUmsClients().iterator(); localIterator1.hasNext();) {
            mod = (IModule) localIterator1.next();
            fasSystem.addModule(mod);
        }

        DbMonitor mastDbMonitor = (DbMonitor) context.getBean("master.dbMonitor");
        mastDbMonitor.testDbConnection();

        FiberManage fiberManage = FiberManage.getInstance();
        fiberManage.setFibers(new ArrayList());
        for (IFiber fiber : config.getUmsClientModules()) {
            fiberManage.schedule(fiber);
        }
        fasSystem.addUnMonitoredModules(fiberManage);

        fasSystem.startSystem();
    }
}