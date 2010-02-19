package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.MessageSendFailEvent;
import org.apache.log4j.Logger;

public class MessageSendFailEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(MessageSendFailEventAdapt.class);
    private MessageSendFailEvent event;

    public void handleEvent(IEvent event) {
        this.event = ((MessageSendFailEvent) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled())
            log.info("event send failed。client ip=" + this.event.getClient() + ";message=" + this.event.getMessage().toString());
    }
}