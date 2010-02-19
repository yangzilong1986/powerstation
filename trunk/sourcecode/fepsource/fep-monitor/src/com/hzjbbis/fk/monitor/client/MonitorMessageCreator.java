package com.hzjbbis.fk.monitor.client;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.monitor.message.MonitorMessage;

public class MonitorMessageCreator implements IMessageCreator {
    public IMessage createHeartBeat(int reqNum) {
        return null;
    }

    public IMessage create() {
        return new MonitorMessage();
    }
}