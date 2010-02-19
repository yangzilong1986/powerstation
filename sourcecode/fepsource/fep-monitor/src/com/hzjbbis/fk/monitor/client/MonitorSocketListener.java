package com.hzjbbis.fk.monitor.client;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.monitor.client.biz.ClientHandleFile;
import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.sockclient.JSocket;
import com.hzjbbis.fk.sockclient.JSocketListener;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class MonitorSocketListener implements JSocketListener {
    private static final Logger log = Logger.getLogger(MonitorSocketListener.class);

    public IMonitorReplyListener replyListener = null;

    public void onClose(JSocket client) {
        if (this.replyListener != null) this.replyListener.onClose();
        log.info("监控服务连接断开:" + client);
    }

    public void onConnected(JSocket client) {
        if (this.replyListener != null) this.replyListener.onConnect();
        log.info("监控服务连接成功:" + client);
    }

    public void onReceive(JSocket client, IMessage message) {
        String result;
        MonitorMessage msg = (MonitorMessage) message;
        msg.resetMessageState();
        ByteBuffer body = null;
        switch (msg.getCommand()) {
            case 2:
                body = msg.getBody();

                result = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onListConfig(result);

                break;
            case 1:
                body = msg.getBody();

                result = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onListLog(result);

                break;
            case 3:
                body = ClientHandleFile.getHandleFile().getFile(msg.getBody());
                if (body == null) {
                    if (this.replyListener != null) this.replyListener.onGetFile();
                    return;
                }
                msg.setBody(body);
                client.sendMessage(msg);
                break;
            case 4:
                body = ClientHandleFile.getHandleFile().putFile(msg.getBody());
                if (body == null) {
                    if (this.replyListener != null) this.replyListener.onPutFile();
                    return;
                }
                msg.setBody(body);
                client.sendMessage(msg);
                break;
            case 16:
                body = msg.getBody();

                result = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onSystemProfile(result);

                break;
            case 17:
                body = msg.getBody();

                result = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onModuleProfile(result);

                break;
            case 18:
                body = msg.getBody();

                result = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onEventHookProfile(result);

                break;
            case 31:
                body = msg.getBody();

                String profile = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onMultiSysProfile(profile);

                break;
            case 25:
                body = msg.getBody();

                result = new String(body.array());
                if (this.replyListener == null) return;
                this.replyListener.onRtuMessageInd(result);
        }
    }

    public void onSend(JSocket client, IMessage msg) {
    }
}