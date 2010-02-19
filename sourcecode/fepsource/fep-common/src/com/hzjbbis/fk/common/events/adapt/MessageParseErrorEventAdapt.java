package com.hzjbbis.fk.common.events.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import org.apache.log4j.Logger;

public class MessageParseErrorEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(MessageParseErrorEventAdapt.class);

    public void handleEvent(IEvent event) {
        log.warn(event);
    }
}