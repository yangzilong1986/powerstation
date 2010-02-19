package com.hzjbbis.fk.monitor.biz.eventtrace;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventTrace;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.monitor.message.MonitorMessage;
import com.hzjbbis.fk.utils.CalendarUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class EventTracer implements IEventTrace {
    private ArrayList<IChannel> monitorClients = new ArrayList();
    private int[] rtus = null;

    public EventTracer(IChannel client) {
        this.monitorClients.add(client);
    }

    public void addClient(IChannel client) {
        synchronized (this.monitorClients) {
            this.monitorClients.remove(client);
            this.monitorClients.add(client);
        }
    }

    public int removeClient(IChannel client) {
        synchronized (this.monitorClients) {
            this.monitorClients.remove(client);
            if (this.monitorClients.size() == 0) this.rtus = null;
            return this.monitorClients.size();
        }
    }

    public void traceRtus(int[] tobeTraced) {
        if (tobeTraced == null) return;
        if (this.rtus == null) {
            this.rtus = tobeTraced;
            return;
        }
        int count = 0;
        int[] tp = new int[tobeTraced.length];
        for (int i = 0; i < tobeTraced.length; ++i) {
            boolean found = false;
            for (int j = 0; j < this.rtus.length; ++j) {
                if (this.rtus[j] == tobeTraced[i]) {
                    found = true;
                    break;
                }
            }
            if (!(found)) {
                tp[count] = tobeTraced[i];
                ++count;
            }
        }
        if (count == 0) return;
        int[] newRtus = new int[this.rtus.length + count];
        System.arraycopy(this.rtus, 0, newRtus, 0, this.rtus.length);
        System.arraycopy(tp, 0, newRtus, this.rtus.length, count);
        this.rtus = newRtus;
    }

    public void traceEvent(IEvent e) {
        IMessage msg;
        IChannel client;
        MessageZj message;
        StringBuffer sb;
        if (e.getType() == EventType.MSG_RECV) {
            msg = e.getMessage();
            client = msg.getSource();

            message = null;
            if (msg instanceof MessageZj) {
                message = (MessageZj) msg;
            } else if (msg.getMessageType() == MessageType.MSG_GATE) {
                message = (MessageZj) ((MessageGate) msg).getInnerMessage();
                if (message == null) return;
                message.setIoTime(msg.getIoTime());
            } else {
                return;
            }
            if (!(_isMonited(message.head.rtua))) return;
            sb = new StringBuffer(400);
            sb.append("收到:【").append(client.getServer().getName()).append("】,时间=");
            sb.append(CalendarUtil.getMilliDateTimeString(message.getIoTime()));
            sb.append(", 报文=").append(message.getRawPacketString()).append("\r\n");
            _sendIndication(sb.toString());
        } else if (e.getType() == EventType.MSG_SENT) {
            msg = e.getMessage();
            client = msg.getSource();

            message = null;
            if (msg instanceof MessageZj) {
                message = (MessageZj) msg;
            } else if (msg.getMessageType() == MessageType.MSG_GATE) {
                message = (MessageZj) ((MessageGate) msg).getInnerMessage();
                if (message == null) return;
                message.setIoTime(msg.getIoTime());
            } else {
                return;
            }
            if (!(_isMonited(message.head.rtua))) {
                return;
            }
            sb = new StringBuffer(400);
            sb.append("发送:【").append(client.getServer().getName()).append("】,时间=");
            sb.append(CalendarUtil.getMilliDateTimeString(message.getIoTime()));
            sb.append(", 报文=").append(message.getRawPacketString()).append("\r\n");
            _sendIndication(sb.toString());
        }
    }

    private void _sendIndication(String info) {
        MonitorMessage msg = new MonitorMessage();
        msg.setCommand(25);
        ByteBuffer body = ByteBuffer.wrap(info.getBytes());
        msg.setBody(body);
        synchronized (this.monitorClients) {
            for (IChannel client : this.monitorClients)
                client.send(msg);
        }
    }

    private boolean _isMonited(int rtua) {
        try {
            for (int i = 0; i < this.rtus.length; ++i)
                if (this.rtus[i] == rtua) return true;
        } catch (Exception localException) {
        }
        return false;
    }
}