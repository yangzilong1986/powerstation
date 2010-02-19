package com.hzjbbis.fk.sockclient;

import com.hzjbbis.fk.message.IMessage;

public abstract interface JSocketListener {
    public abstract void onReceive(JSocket paramJSocket, IMessage paramIMessage);

    public abstract void onSend(JSocket paramJSocket, IMessage paramIMessage);

    public abstract void onConnected(JSocket paramJSocket);

    public abstract void onClose(JSocket paramJSocket);
}