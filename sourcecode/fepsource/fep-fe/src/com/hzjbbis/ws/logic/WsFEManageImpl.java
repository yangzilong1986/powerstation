package com.hzjbbis.ws.logic;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.fe.config.ApplicationPropertiesConfig;
import com.hzjbbis.fk.fe.filecache.MisparamRtuManage;
import com.hzjbbis.fk.fe.filecache.RtuCommFlowCache;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.hzjbbis.ws.logic.WsFEManage")
public class WsFEManageImpl implements WsFEManage {
    private static final Logger log = Logger.getLogger(WsFEManageImpl.class);

    public boolean addGprsGateChannel(String ip, int port, String gateName) {
        return ApplicationPropertiesConfig.getInstance().addGprsGate(ip, port, gateName);
    }

    public boolean addUmsChannel(String appid, String password) {
        return ApplicationPropertiesConfig.getInstance().addUmsClient(appid, password);
    }

    public void startModule(String name) {
        FasSystem.getFasSystem().startModule(name);
    }

    public void stopModule(String name) {
        FasSystem.getFasSystem().stopModule(name);
    }

    public void updateFlow() {
        MisparamRtuManage instance = MisparamRtuManage.getInstance();
        RtuManage rm = RtuManage.getInstance();
        List allRtu = null;
        synchronized (rm) {
            allRtu = new ArrayList(rm.getAllComRtu());
        }
        try {
            for (ComRtu rtu : allRtu) {
                RtuCommFlowCache.getInstance().addRtu(rtu);
            }
            instance.saveRtuStatus2Db(allRtu);
        } catch (Exception e) {
            log.warn("更新流量异常:" + e.getLocalizedMessage(), e);
        }
    }
}