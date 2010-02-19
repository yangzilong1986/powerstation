package com.hzjbbis.fk.sockclient.async.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockclient.async.JAsyncSocket;
import com.hzjbbis.fk.sockclient.async.simulator.SimulatorManager;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;

public class OnClientRecvMsg implements IEventHandler {
    public void handleEvent(IEvent evt) {
        ReceiveMessageEvent event = (ReceiveMessageEvent) evt;
        JAsyncSocket client = (JAsyncSocket) event.getClient();
        IMessage msg = event.getMessage();
        SimulatorManager.onChannelReceive(client, msg);
    }
}