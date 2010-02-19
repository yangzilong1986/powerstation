package com.hzjbbis.fk.monitor.client.biz;

import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.sockclient.JSocket;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class TraceRTUCommand {
    private static final Logger log = Logger.getLogger(TraceRTUCommand.class);

    public void startTrace(JSocket client, int[] rtus) {
        short cmd = 23;
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(cmd);
        ByteBuffer body = ByteBuffer.allocate(rtus.length * 4);
        for (int i = 0; i < rtus.length; ++i) {
            log.debug("TRACE RTUA=" + HexDump.toHex(rtus[i]));
            body.putInt(rtus[i]);
        }
        body.flip();
        msg.setBody(body);
        client.sendMessage(msg);
    }

    public void stopTrace(JSocket client) {
        short cmd = 24;
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(cmd);
        ByteBuffer body = ByteBuffer.allocate(0);
        msg.setBody(body);
        client.sendMessage(msg);
    }
}