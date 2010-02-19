package com.hzjbbis.fk.message.gw;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;

public class MessageGwCreator implements IMessageCreator {
    public IMessage create() {
        return new MessageGw();
    }

    public IMessage createHeartBeat(int reqNum) {
        MessageGw heart = new MessageGw();
        heart.head.app_func = 2;
        heart.head.c_func = 9;
        heart.head.rtua = reqNum;
        return heart;
    }
}