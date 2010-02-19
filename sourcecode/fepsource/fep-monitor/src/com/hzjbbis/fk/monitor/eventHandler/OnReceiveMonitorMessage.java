package com.hzjbbis.fk.monitor.eventHandler;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.monitor.biz.HandleFile;
import com.hzjbbis.fk.monitor.biz.HandleListFile;
import com.hzjbbis.fk.monitor.biz.HandleRtuTrace;
import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.adapt.ReceiveMessageEventAdapt;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class OnReceiveMonitorMessage extends ReceiveMessageEventAdapt {
    private static final Logger log = Logger.getLogger(OnReceiveMonitorMessage.class);
    private static final byte[] Reply_Success = "成功".getBytes();
    private static final byte[] Reply_Failed = "失败".getBytes();
    private static final String configPath = ".";
    private static final String logPath = "log";

    protected void process(ReceiveMessageEvent event) {
        String name;
        byte[] result;
        MonitorMessage msg = (MonitorMessage) event.getMessage();
        msg.resetMessageState();

        switch (msg.getCommand()) {
            case 0:
                log.error("OnReceiveMonitorMessage: CMD_INVALID");
                break;
            case 2:
                result = HandleListFile.getListFile().list(".", "*.xml,*.properties").getBytes();
                _reply(event, result);
                break;
            case 1:
                result = HandleListFile.getListFile().list("log").getBytes();
                _reply(event, result);
                break;
            case 3:
                _reply(event, HandleFile.getHandleFile().getFile(msg.getBody()));
                break;
            case 4:
                _reply(event, HandleFile.getHandleFile().putFile(msg.getBody()));
                break;
            case 16:
                onProfileEvent(event, "system");
                break;
            case 17:
                onProfileEvent(event, "module");
                break;
            case 18:
                onProfileEvent(event, "eventhook");
                break;
            case 31:
                onProfileEvent(event, "gather");
                break;
            case 19:
                name = new String(msg.getBody().array());
                onBooleanReply(event, FasSystem.getFasSystem().startModule(name));
                break;
            case 20:
                name = new String(msg.getBody().array());
                onBooleanReply(event, FasSystem.getFasSystem().stopModule(name));
                break;
            case 21:
                break;
            case 22:
                FasSystem.getFasSystem().stopSystem();
                break;
            case 23:
                onBooleanReply(event, HandleRtuTrace.getHandleRtuTrace().startTraceRtu(event, msg.getBody()));
                break;
            case 24:
                onBooleanReply(event, HandleRtuTrace.getHandleRtuTrace().stopTrace(event));
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
        }
    }

    private void onProfileEvent(ReceiveMessageEvent event, String eType) {
        FasSystem fas = FasSystem.getFasSystem();
        String profile = fas.getProfile(eType);
        if (profile == null) return;
        byte[] ret = profile.getBytes();

        _reply(event, ret);
    }

    private void onBooleanReply(ReceiveMessageEvent event, boolean ret) {
        byte[] result;
        if (ret) result = Reply_Success;
        else result = Reply_Failed;
        _reply(event, result);
    }

    private void _reply(ReceiveMessageEvent event, byte[] result) {
        MonitorMessage msg = (MonitorMessage) event.getMessage();
        ByteBuffer body = ByteBuffer.wrap(result);
        msg.setBody(body);
        event.getClient().send(msg);
    }

    private void _reply(ReceiveMessageEvent event, ByteBuffer result) {
        MonitorMessage msg = (MonitorMessage) event.getMessage();
        msg.setBody(result);
        event.getClient().send(msg);
    }
}