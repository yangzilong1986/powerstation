package com.hzjbbis.ws.logic;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.tracelog.TraceLog;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.hzjbbis.ws.logic.WsProfile")
public class WsProfileImpl implements WsProfile {
    private static final TraceLog tracer = TraceLog.getTracer(WsProfileImpl.class);

    public String allProfile() {
        return FasSystem.getFasSystem().gatherSystemsProfile();
    }

    public String modulesProfile() {
        return FasSystem.getFasSystem().getModuleProfile();
    }

    public ModuleSimpleProfile[] getAllModuleProfile() {
        List list = new ArrayList();
        for (IModule mod : FasSystem.getFasSystem().getModules()) {
            ModuleSimpleProfile mp = new ModuleSimpleProfile();
            mp.setLastReceiveTime(mod.getLastReceiveTime());
            mp.setModuleType(mod.getModuleType());
            mp.setName(mod.getName());
            mp.setPerMinuteReceive(mod.getMsgRecvPerMinute());
            mp.setPerMinuteSend(mod.getMsgSendPerMinute());
            mp.setRunning(mod.isActive());
            mp.setTotalReceive(mod.getTotalRecvMessages());
            mp.setTotalSend(mod.getTotalSendMessages());
            list.add(mp);
        }
        return ((ModuleSimpleProfile[]) list.toArray(new ModuleSimpleProfile[list.size()]));
    }

    public boolean updateRtuSimNum(String rtuSimList) {
        try {
            if ((rtuSimList != null) && (rtuSimList.length() > 0)) {
                tracer.trace("rtuSimList:" + rtuSimList);
                String[] rtuSims = rtuSimList.trim().split(";");
                for (int i = 0; i < rtuSims.length; ++i) {
                    String[] rtuSim = rtuSims[i].trim().split(",");
                    if (rtuSim.length == 2) {
                        String logicAddress = rtuSim[0].trim();
                        String simNum = rtuSim[1].trim();
                        ComRtu rtu = RtuManage.getInstance().getComRtuInCache((int) Long.parseLong(logicAddress, 16));
                        if (rtu != null) rtu.setSimNum(simNum);
                    }
                }
            }
        } catch (Exception ex) {
            tracer.trace("update rtu simnum error:" + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean updateRemoteUpdateRtuaList(String rtuaList) {
        try {
            if ((rtuaList != null) && (rtuaList.length() > 0)) {
                tracer.trace("remoteUpdateRtuaList:" + rtuaList);
                String[] rtuSims = rtuaList.trim().split(",");
                RtuManage.getInstance().clearRtuRemoteUpdateMap();
                for (int i = 0; i < rtuSims.length; ++i)
                    RtuManage.getInstance().putRemoteUpateRtuaToCache(rtuSims[i].trim());
            }
        } catch (Exception ex) {
            tracer.trace("update remote update rtua list error:" + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }
}