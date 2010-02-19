package com.hzjbbis.fk.message;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.exception.MessageParseException;

import java.nio.ByteBuffer;

public abstract interface IMessage {
    public static final Integer DIRECTION_UP = new Integer(0);
    public static final Integer DIRECTION_DOWN = new Integer(1);
    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_VIP = 3;
    public static final int PRIORITY_MAX = 5;
    public static final int STATE_INVALID = -1;
    public static final int STATE_READ_HEAD = 1;
    public static final int STATE_READ_DATA = 2;
    public static final int STATE_READ_TAIL = 3;
    public static final int STATE_READ_DONE = 15;
    public static final int STATE_SEND_HEAD = 17;
    public static final int STATE_SEND_DATA = 18;
    public static final int STATE_SEND_TAIL = 19;
    public static final int STATE_SEND_DONE = 47;

    public abstract IChannel getSource();

    public abstract void setSource(IChannel paramIChannel);

    public abstract MessageType getMessageType();

    public abstract boolean read(ByteBuffer paramByteBuffer) throws MessageParseException;

    public abstract boolean write(ByteBuffer paramByteBuffer);

    public abstract long getIoTime();

    public abstract void setIoTime(long paramLong);

    public abstract String getPeerAddr();

    public abstract void setPeerAddr(String paramString);

    public abstract String getServerAddress();

    public abstract void setServerAddress(String paramString);

    public abstract String getTxfs();

    public abstract void setTxfs(String paramString);

    public abstract int getRtua();

    public abstract String getStatus();

    public abstract void setStatus(String paramString);

    public abstract Long getCmdId();

    public abstract int getPriority();

    public abstract void setPriority(int paramInt);

    public abstract byte[] getRawPacket();

    public abstract String getRawPacketString();

    public abstract boolean isHeartbeat();

    public abstract boolean isTask();

    public abstract void setTask(boolean paramBoolean);

    public abstract int length();
}