package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import org.apache.log4j.Logger;

public class ClientCloseEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(ClientCloseEventAdapt.class);
    protected ClientCloseEvent event;

    public void handleEvent(IEvent ev) {
        this.event = ((ClientCloseEvent) ev);
        process(this.event);
    }

    protected void process(ClientCloseEvent event) {
        if (log.isInfoEnabled())
            log.info("server[" + event.getServer().getPort() + "] close client[" + event.getClient().getPeerIp() + "]");
    }
}