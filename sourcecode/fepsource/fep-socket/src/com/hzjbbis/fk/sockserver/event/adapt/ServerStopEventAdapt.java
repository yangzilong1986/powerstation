package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.ServerStoppedEvent;
import org.apache.log4j.Logger;

public class ServerStopEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(ServerStopEventAdapt.class);
    private ServerStoppedEvent event;

    public void handleEvent(IEvent event) {
        this.event = ((ServerStoppedEvent) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled()) log.info(this.event);
    }
}