package com.hzjbbis.fk.common.events.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SystemTimerEvent implements IEvent {
    private static final Logger log = Logger.getLogger(SystemTimerEvent.class);
    private static final EventType type = EventType.SYS_TIMER;

    private static final ArrayList<SystemTimerEvent> events = new ArrayList(128);

    protected String name = "";
    protected IMessage message = null;
    protected Object source = null;
    protected int delay = 1;
    private long beginTime = System.currentTimeMillis();

    static {
        SysTimerThread timerThread = new SysTimerThread();
        timerThread.start();
    }

    public SystemTimerEvent(String name, Object source, IMessage msg, int delay) {
        this.name = name;
        this.source = source;
        this.message = msg;
        this.delay = (delay * 1000);
    }

    public IMessage getMessage() {
        return this.message;
    }

    public Object getSource() {
        return this.source;
    }

    public EventType getType() {
        return type;
    }

    public void setSource(Object src) {
        this.source = src;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void schedule(SystemTimerEvent event) {
        if (event == null) return;
        long now = System.currentTimeMillis();
        event.beginTime = now;
        synchronized (events) {
            for (int i = 0; i < events.size(); ++i) {
                SystemTimerEvent e = (SystemTimerEvent) events.get(i);
                long remain = e.beginTime + e.delay - now;
                if (event.delay < remain) {
                    events.add(i, event);
                    events.notifyAll();

                    return;
                }
            }
            events.add(event);
            events.notifyAll();
        }
    }

    static class SysTimerThread extends Thread {
        public SysTimerThread() {
            super("SysTimerThread");
            setDaemon(true);
        }

        public void run() {
            int cnt = 0;
            long checkTime = 0L;
            SystemTimerEvent.log.info("系统定时器守护线程开始运行...");

            synchronized (SystemTimerEvent.events) {
                try {
                    if (0L == checkTime) checkTime = System.currentTimeMillis();
                    dealList();
                    ++cnt;
                } catch (InterruptedException localInterruptedException) {
                } catch (Exception e) {
                    SystemTimerEvent.log.warn(e.getLocalizedMessage(), e);
                }
                if (cnt > 1024) {
                    cnt = 0;
                    long now = System.currentTimeMillis();
                    if (now - checkTime < 200L) {
                        SystemTimerEvent.log.error("系统定时器估计进入死循环。");
                    }
                    checkTime = now;
                }
            }
        }

        private void dealList() throws InterruptedException {
            while (SystemTimerEvent.events.size() == 0) SystemTimerEvent.events.wait(1000L);
            long now = System.currentTimeMillis();
            SystemTimerEvent ev = (SystemTimerEvent) SystemTimerEvent.events.get(0);

            long dif = now - ev.beginTime;
            if (dif >= ev.delay) {
                SystemTimerEvent.events.remove(0);
                GlobalEventHandler.postEvent(ev);
                return;
            }
            if (dif < 0L) return;
            if (dif < 100L) dif = 100L;
            SystemTimerEvent.events.wait(dif);
        }
    }
}