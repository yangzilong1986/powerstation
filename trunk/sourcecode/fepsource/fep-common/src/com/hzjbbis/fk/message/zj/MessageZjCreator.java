package com.hzjbbis.fk.message.zj;

import com.hzjbbis.fk.message.IMessageCreator;

import java.nio.ByteBuffer;

public class MessageZjCreator implements IMessageCreator {
    public MessageZj createHeartBeat(int reqNum) {
        MessageZj msg = new MessageZj();
        msg.head.rtua = reqNum;
        msg.head.c_dir = 0;
        msg.head.c_func = 36;
        msg.head.fseq = 1;
        return msg;
    }

    public MessageZj createUserDefine(int rtua, byte manuCode, byte[] data) {
        MessageZj msg = new MessageZj();
        msg.head.rtua = rtua;
        msg.head.c_dir = 0;
        msg.head.c_func = 15;
        msg.head.msta = manuCode;
        msg.head.fseq = 2;
        byte[] d = new byte[data.length + 1];
        d[0] = manuCode;
        for (int i = 0; i < data.length; ++i) {
            d[(i + 1)] = data[i];
        }
        msg.data = ByteBuffer.wrap(d);
        return msg;
    }

    public MessageZj create() {
        return new MessageZj();
    }
}