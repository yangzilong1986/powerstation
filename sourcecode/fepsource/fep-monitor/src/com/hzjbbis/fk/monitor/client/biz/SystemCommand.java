package com.hzjbbis.fk.monitor.client.biz;

import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.sockclient.JSocket;

import java.nio.ByteBuffer;

public class SystemCommand {
    public void shutdown(JSocket client) {
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(22);
        ByteBuffer body = ByteBuffer.allocate(0);
        msg.setBody(body);
        client.sendMessage(msg);
    }
}