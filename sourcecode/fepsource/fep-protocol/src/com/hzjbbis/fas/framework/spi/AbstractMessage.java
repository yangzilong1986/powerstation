package com.hzjbbis.fas.framework.spi;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public abstract class AbstractMessage implements IMessage {
    public MessageType msgType = MessageType.MSG_INVAL;
    protected int state = -1;
    private Object attachment;
    protected HashMap hmAttributes = new HashMap();
    protected int priority = 0;
    public ByteBuffer dataOut;
    public ByteBuffer dataIn;
    public String upRawString = "";
    public String downRawString = "";
    public AbstractMessage nextMessage;
    public long key = 0L;
    public long reqTime = System.currentTimeMillis();
    public long repTime = 0L;
    public int accesstime = 0;
    private boolean multiReply = false;

    private Stack modulerStack = new Stack();

    public AbstractMessage() {
        this.nextMessage = null;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean hasNextModule() {
        return (this.modulerStack.size() > 0);
    }

    public Object getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Object obj) {
        this.attachment = obj;
    }

    public MessageType getMessageType() {
        return this.msgType;
    }

    public IMessage getNextMessage() {
        return this.nextMessage;
    }

    public String getUpRawString() {
        return this.upRawString;
    }

    public String getDownRawString() {
        return this.downRawString;
    }

    public Object getAttribute(Object key) {
        return this.hmAttributes.get(key);
    }

    public void setAttribute(Object key, Object value) {
        if (this.hmAttributes.containsKey(key)) {
            this.hmAttributes.remove(key);
        }
        this.hmAttributes.put(key, value);
    }

    public void removeAttribute(Object key) {
        this.hmAttributes.remove(key);
    }

    public void copyAttributes(AbstractMessage src) {
        Iterator it = src.hmAttributes.keySet().iterator();

        while (it.hasNext()) {
            Object key = it.next();
            setAttribute(key, src.getAttribute(key));
        }
    }

    public int getRtuaIn() {
        return 0;
    }

    public int getRtuaOut() {
        return 0;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isMultiReply() {
        return this.multiReply;
    }

    public void setMultiReply(boolean multiReply) {
        this.multiReply = multiReply;
    }
}