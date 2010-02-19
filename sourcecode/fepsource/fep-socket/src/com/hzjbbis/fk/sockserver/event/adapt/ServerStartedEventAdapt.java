package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.ServerStartedEvent;
import org.apache.log4j.Logger;

public class ServerStartedEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(ServerStartedEventAdapt.class);
    private ServerStartedEvent event;

    public void handleEvent(IEvent event) {
        this.event = ((ServerStartedEvent) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled()) log.info(this.event);
    }
}