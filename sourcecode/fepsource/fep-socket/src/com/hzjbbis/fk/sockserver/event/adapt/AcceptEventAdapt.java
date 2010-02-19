package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.AcceptEvent;
import org.apache.log4j.Logger;

public class AcceptEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(AcceptEventAdapt.class);
    private AcceptEvent event;

    public void handleEvent(IEvent event) {
        this.event = ((AcceptEvent) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled())
            log.info("server[" + this.event.getServer().getPort() + "] accept client[" + this.event.getClient().getPeerIp() + "]");
    }
}