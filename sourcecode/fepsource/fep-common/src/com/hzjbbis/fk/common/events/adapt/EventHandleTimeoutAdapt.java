package com.hzjbbis.fk.common.events.adapt;

import com.hzjbbis.fk.common.events.event.EventHandleTimeoutAlarm;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventHandleTimeoutAdapt implements IEventHandler {
    private static final Logger log = Logger.getLogger(EventHandleTimeoutAdapt.class);
    private EventHandleTimeoutAlarm event;

    public void handleEvent(IEvent event) {
        this.event = ((EventHandleTimeoutAlarm) event);
        process();
    }

    protected void process() {
        if (log.isInfoEnabled()) {
            StringBuffer sb = new StringBuffer(1024);
            sb.append("事件处理超时。事件类型：").append(this.event.getTimeoutEvent().getType());
            sb.append(",begin=");
            Date date = new Date(this.event.getBeginTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
            sb.append(format.format(date)).append(",end=");
            sb.append(format.format(new Date(this.event.getEndTime()))).append("。事件内容：");
            sb.append(this.event.getTimeoutEvent().toString());
            log.info(sb.toString());
        }
    }
}