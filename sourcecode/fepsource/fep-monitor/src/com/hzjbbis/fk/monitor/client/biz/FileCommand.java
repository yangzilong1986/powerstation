package com.hzjbbis.fk.monitor.client.biz;

import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.sockclient.JSocket;

import java.nio.ByteBuffer;

public class FileCommand {
    public void listLog(JSocket client) {
        fileList("log", client);
    }

    public void listConfig(JSocket client) {
        fileList("config", client);
    }

    public void fileList(String type, JSocket client) {
        short cmd;
        if (type.equalsIgnoreCase("config")) cmd = 2;
        else cmd = 1;
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(cmd);
        ByteBuffer body = ByteBuffer.allocate(0);
        msg.setBody(body);
        client.sendMessage(msg);
    }

    public void getFile(JSocket client, String path) {
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(3);
        byte[] btPath = path.getBytes();
        ByteBuffer body = ByteBuffer.allocate(btPath.length + 1 + 8);
        body.put(btPath).put(0);
        body.putLong(0L);
        body.flip();
        msg.setBody(body);
        client.sendMessage(msg);
    }

    public void putFile(JSocket client, String path) {
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(4);
        byte[] btPath = path.getBytes();
        ByteBuffer body = ByteBuffer.allocate(btPath.length + 1 + 8);
        body.put(btPath).put(0);
        body.putLong(0L);
        body.flip();
        body = ClientHandleFile.getHandleFile().putFile(body);
        if (body == null) return;
        msg.setBody(body);
        client.sendMessage(msg);
    }
}