package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import org.apache.log4j.Logger;

public class ModuleProfileEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(ModuleProfileEventAdapt.class);
    private IEvent event;

    public void handleEvent(IEvent event) {
        this.event = event;
        process();
    }

    protected void process() {
        if (log.isInfoEnabled()) log.info(this.event);
    }
}