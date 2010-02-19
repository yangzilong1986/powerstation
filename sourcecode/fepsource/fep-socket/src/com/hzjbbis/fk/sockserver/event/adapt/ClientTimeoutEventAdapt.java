package com.hzjbbis.fk.sockserver.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockserver.event.ClientTimeoutEvent;
import org.apache.log4j.Logger;

public class ClientTimeoutEventAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(ClientTimeoutEventAdapt.class);
    private ClientTimeoutEvent event;

    public void handleEvent(IEvent event) {
        this.event = ((ClientTimeoutEvent) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled()) log.info("client[" + this.event.getClient().getPeerIp() + "]长时间没有IO，被关闭。");
    }
}