package com.hzjbbis.fk.sockclient.async.simulator;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockclient.async.JAsyncSocket;

public abstract interface IRtuSimulator {
    public abstract int getRtua();

    public abstract void setRtua(int paramInt);

    public abstract void onConnect(JAsyncSocket paramJAsyncSocket);

    public abstract void onClose(JAsyncSocket paramJAsyncSocket);

    public abstract void onReceive(JAsyncSocket paramJAsyncSocket, IMessage paramIMessage);

    public abstract void onSend(JAsyncSocket paramJAsyncSocket, IMessage paramIMessage);

    public abstract void sendLogin();

    public abstract void sendHeart();

    public abstract void sendTask();
}