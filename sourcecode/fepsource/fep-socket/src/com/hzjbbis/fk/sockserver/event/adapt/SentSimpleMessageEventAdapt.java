package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import org.apache.log4j.Logger;

public class SentSimpleMessageEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(SentSimpleMessageEventAdapt.class);
    private SendMessageEvent event;

    public void handleEvent(IEvent event) {
        this.event = ((SendMessageEvent) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled()) log.info(this.event);
    }
}