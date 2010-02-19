package com.hzjbbis.fk.sockclient.async.event.adapt;

import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockclient.async.JAsyncSocket;
import com.hzjbbis.fk.sockclient.async.simulator.SimulatorManager;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;

public class OnClientSendMsg implements IEventHandler {
    public void handleEvent(IEvent evt) {
        SendMessageEvent event = (SendMessageEvent) evt;
        JAsyncSocket client = (JAsyncSocket) event.getClient();
        IMessage msg = event.getMessage();
        SimulatorManager.onChannelSend(client, msg);
    }
}