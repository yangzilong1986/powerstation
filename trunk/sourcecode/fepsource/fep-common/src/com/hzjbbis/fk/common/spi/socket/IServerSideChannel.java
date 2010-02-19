package com.hzjbbis.fk.common.spi.socket;

import com.hzjbbis.fk.message.IMessage;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract interface IServerSideChannel extends IChannel {
    public abstract SocketChannel getChannel();

    public abstract int getLastingWrite();

    public abstract void setLastingWrite(int paramInt);

    public abstract SocketAddress getSocketAddress();

    public abstract IMessage getCurReadingMsg();

    public abstract void setCurReadingMsg(IMessage paramIMessage);

    public abstract IMessage getCurWritingMsg();

    public abstract void setCurWritingMsg(IMessage paramIMessage);

    public abstract boolean bufferHasRemaining();

    public abstract void setBufferHasRemaining(boolean paramBoolean);

    public abstract IMessage getNewSendMessage();

    public abstract ByteBuffer getBufRead();

    public abstract ByteBuffer getBufWrite();
}