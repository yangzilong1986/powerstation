package com.hzjbbis.fk.sockclient.async.eventhandler;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.BasicEventHook;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.sockclient.async.event.adapt.OnClientClosed;
import com.hzjbbis.fk.sockclient.async.event.adapt.OnClientConnected;
import com.hzjbbis.fk.sockclient.async.event.adapt.OnClientRecvMsg;
import com.hzjbbis.fk.sockclient.async.event.adapt.OnClientSendMsg;

import java.util.HashMap;

public class AsyncSocketEventHandler extends BasicEventHook {
    private IEventHandler listener;

    public void init() {
        if (this.include == null) {
            this.include = new HashMap();
            this.include.put(EventType.CLIENT_CONNECTED, new OnClientConnected());
            this.include.put(EventType.CLIENTCLOSE, new OnClientClosed());
            this.include.put(EventType.MSG_RECV, new OnClientRecvMsg());
            this.include.put(EventType.MSG_SENT, new OnClientSendMsg());
        }
        super.init();
    }

    public void handleEvent(IEvent event) {
        super.handleEvent(event);
        if (this.listener != null) this.listener.handleEvent(event);
    }

    public IEventHandler getListener() {
        return this.listener;
    }

    public void setListener(IEventHandler listener) {
        this.listener = listener;
    }
}