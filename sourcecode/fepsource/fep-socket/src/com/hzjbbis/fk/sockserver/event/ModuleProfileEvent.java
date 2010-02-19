package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;
import org.apache.log4j.Logger;

public class ModuleProfileEvent implements IEvent {
    private static final Logger log = Logger.getLogger(ModuleProfileEvent.class);
    private EventType type = EventType.MODULE_PROFILE;
    private IModule module = null;
    private long lastReceiveTime = 0L;
    private long lastSendTime = 0L;
    private long totalRecvMessages = 0L;
    private long totalSendMessages = 0L;
    private int msgRecvPerMinute = 0;
    private int msgSendPerMinute = 0;
    private int clientSize = 0;

    public ModuleProfileEvent(IModule m) {
        this.module = m;
        this.lastReceiveTime = m.getLastReceiveTime();
        this.lastSendTime = m.getLastSendTime();
        this.totalRecvMessages = m.getTotalRecvMessages();
        this.totalSendMessages = m.getTotalSendMessages();
        this.msgRecvPerMinute = m.getMsgRecvPerMinute();
        this.msgSendPerMinute = m.getMsgSendPerMinute();
        if (m instanceof ISocketServer) this.clientSize = ((ISocketServer) m).getClientSize();
    }

    public Object getSource() {
        return this.module;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public void setType(EventType type) {
    }

    public final long getLastReceiveTime() {
        return this.lastReceiveTime;
    }

    public final long getLastSendTime() {
        return this.lastSendTime;
    }

    public final long getTotalRecvMessages() {
        return this.totalRecvMessages;
    }

    public final long getTotalSendMessages() {
        return this.totalSendMessages;
    }

    public final int getMsgRecvPerMinute() {
        return this.msgRecvPerMinute;
    }

    public final int getMsgSendPerMinute() {
        return this.msgSendPerMinute;
    }

    public final int getClientSize() {
        return this.clientSize;
    }

    public IMessage getMessage() {
        return null;
    }

    public String toString() {
        String ret = this.module.profile();
        if (log.isDebugEnabled()) log.debug(ret);
        return ret;
    }
}