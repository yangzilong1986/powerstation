package com.hzjbbis.fk.fe;

import com.hzjbbis.fk.clientmod.ClientModule;
import com.hzjbbis.fk.common.spi.abstra.BaseModule;
import com.hzjbbis.fk.fe.ums.UmsModule;

import java.util.ArrayList;
import java.util.List;

public class GateClientManage extends BaseModule {
    private List<ClientModule> gprsGateClients = new ArrayList();

    private List<UmsModule> umsClients = new ArrayList();

    private static final GateClientManage instance = new GateClientManage();

    public static final GateClientManage getInstance() {
        return instance;
    }

    public void setGprsGateClients(List<ClientModule> gprsGates) {
        this.gprsGateClients = gprsGates;
        for (ClientModule gate : this.gprsGateClients) {
            gate.init();
            ChannelManage.getInstance().addGprsClient(gate);
        }
    }

    public final void setUmsClients(List<UmsModule> clients) {
        this.umsClients = clients;
        for (UmsModule ums : this.umsClients)
            ChannelManage.getInstance().addUmsClient(ums);
    }

    public boolean start() {
        return false;
    }

    public void stop() {
    }

    public String getModuleType() {
        return "moduleContainer";
    }

    public final List<ClientModule> getGprsGateClients() {
        return this.gprsGateClients;
    }

    public final List<UmsModule> getUmsClients() {
        return this.umsClients;
    }
}