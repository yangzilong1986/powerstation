package com.hzjbbis.fk.common.events.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventHandleTimeoutAlarm implements IEvent {
    private final EventType type = EventType.SYS_EVENT_PROCESS_TIMEOUT;
    private Object source;
    private IEvent event;
    private String threadName;
    private long beginTime;
    private long endTime;
    private List<StackTraceElement> stackTraces = new ArrayList();

    public long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public EventHandleTimeoutAlarm(IEvent ev) {
        this.event = ev;
        this.threadName = Thread.currentThread().getName();
    }

    public Object getSource() {
        return this.source;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
        this.source = src;
    }

    public void setType(EventType type) {
    }

    public IEvent getTimeoutEvent() {
        return this.event;
    }

    public IMessage getMessage() {
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("事件处理超时。类型：").append(this.event.getType());
        sb.append(",thread=").append(this.threadName);
        sb.append(",begin=");
        Date date = new Date(getBeginTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
        sb.append(format.format(date)).append(",end=");
        sb.append(format.format(new Date(getEndTime()))).append("。事件内容：");
        sb.append(this.event.toString());
        sb.append("。StackTraceElement:");
        for (StackTraceElement st : this.stackTraces) {
            sb.append("\r\n\t").append(st.toString());
        }
        return sb.toString();
    }

    public String getThreadName() {
        return this.threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void setStackTraceElement(StackTraceElement[] trace) {
        if ((trace == null) || (trace.length == 0)) return;
        for (StackTraceElement st : trace)
            this.stackTraces.add(st);
    }
}