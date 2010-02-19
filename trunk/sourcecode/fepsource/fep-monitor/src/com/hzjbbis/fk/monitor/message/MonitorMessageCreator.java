package com.hzjbbis.fk.monitor.message;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;

public class MonitorMessageCreator implements IMessageCreator {
    public IMessage createHeartBeat(int reqNum) {
        return null;
    }

    public IMessage create() {
        return new MonitorMessage();
    }
}