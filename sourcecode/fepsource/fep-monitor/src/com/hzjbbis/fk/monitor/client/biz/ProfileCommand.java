package com.hzjbbis.fk.monitor.client.biz;

import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.sockclient.JSocket;

import java.nio.ByteBuffer;

public class ProfileCommand {
    public void getSystemProfile(JSocket client) {
        getProfile(client, "system");
    }

    public void getModuleProfile(JSocket client) {
        getProfile(client, "module");
    }

    public void getEventHookProfile(JSocket client) {
        getProfile(client, "eventhook");
    }

    public void gatherProfile(JSocket client) {
        getProfile(client, "gather");
    }

    public void getProfile(JSocket client, String type) {
        short cmd;
        if ("module".equalsIgnoreCase(type)) cmd = 17;
        else if ("eventhook".equalsIgnoreCase(type)) cmd = 18;
        else if ("gather".equalsIgnoreCase(type)) cmd = 31;
        else cmd = 16;
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(cmd);
        ByteBuffer body = ByteBuffer.allocate(0);
        msg.setBody(body);
        client.sendMessage(msg);
    }
}