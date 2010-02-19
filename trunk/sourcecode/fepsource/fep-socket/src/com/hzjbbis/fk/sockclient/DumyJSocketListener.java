package com.hzjbbis.fk.sockclient;

import com.hzjbbis.fk.message.IMessage;
import org.apache.log4j.Logger;

public class DumyJSocketListener implements JSocketListener {
    private static final Logger log = Logger.getLogger(DumyJSocketListener.class);

    public void onClose(JSocket client) {
        log.debug("socket client连接关闭:" + client.getHostIp() + "@" + client.getHostPort());
    }

    public void onConnected(JSocket client) {
        log.debug("socket client连接成功:" + client.getHostIp() + "@" + client.getHostPort());
    }

    public void onReceive(JSocket client, IMessage msg) {
        log.debug("收到消息:" + msg);
    }

    public void onSend(JSocket client, IMessage msg) {
        log.debug("发送成功:" + msg);
    }
}