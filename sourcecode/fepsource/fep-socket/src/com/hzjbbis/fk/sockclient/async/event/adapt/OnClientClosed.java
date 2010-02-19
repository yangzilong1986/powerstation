package com.hzjbbis.fk.sockclient.async.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockclient.async.JAsyncSocket;
import com.hzjbbis.fk.sockclient.async.simulator.SimulatorManager;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import org.apache.log4j.Logger;

public class OnClientClosed implements IEventHandler {
    private static final Logger log = Logger.getLogger(OnClientClosed.class);

    public void handleEvent(IEvent evt) {
        ClientCloseEvent event = (ClientCloseEvent) evt;
        JAsyncSocket client = (JAsyncSocket) event.getClient();

        SimulatorManager.onChannelClosed(client);
        log.info("async socket pool: client=" + client.getPeerAddr() + " closed.");
    }
}