package com.hzjbbis.fk.monitor.eventHandler;

import com.hzjbbis.fk.monitor.biz.HandleRtuTrace;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import com.hzjbbis.fk.sockserver.event.adapt.ClientCloseEventAdapt;

public class OnMonitorClientCloseEvent extends ClientCloseEventAdapt {
    protected void process(ClientCloseEvent event) {
        super.process(event);
        HandleRtuTrace.getHandleRtuTrace().onClientClose(event);
    }
}