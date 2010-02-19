package com.hzjbbis.fk.common.events;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.common.spi.IEventHook;
import com.hzjbbis.fk.common.spi.IEventPump;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public final class GlobalEventHandler implements IEventPump {
    private final EventQueue queue = new EventQueue();

    private static final Object hookLock = new Object();
    private static ArrayList<IEventHook>[] hooks = (ArrayList[]) Array.newInstance(ArrayList.class, 512);
    private static ArrayList<IEventHandler>[] handlers;
    private volatile State state = State.STOPPED;
    private static Logger log = Logger.getLogger(GlobalEventHandler.class);
    private static TraceLog tracer = TraceLog.getTracer();

    private static GlobalEventHandler gHandler = null;
    private EventPumpThread pump = null;

    static {
        gHandler = getInstance();
    }

    private GlobalEventHandler() {
        this.pump = new EventPumpThread();
        this.pump.start();
    }

    public static final GlobalEventHandler getInstance() {
        if (gHandler == null) gHandler = new GlobalEventHandler();
        return gHandler;
    }

    public void destroy() {
        this.state = State.STOPPING;
        this.pump.interrupt();
    }

    public static final void registerHook(IEventHook hook, EventType type) {
        synchronized (hookLock) {
            if (hooks == null) {
                hooks = new ArrayList[128];
                Arrays.fill(hooks, null);
            }
            if (hooks.length < EventType.getMaxIndex()) {
                ArrayList[] temp = new ArrayList[EventType.getMaxIndex()];
                Arrays.fill(temp, null);
                System.arraycopy(hooks, 0, temp, 0, hooks.length);
                hooks = temp;
            }
            if (hooks[type.toInt()] == null) {
                hooks[type.toInt()] = new ArrayList();
            }
            hooks[type.toInt()].add(hook);
        }
    }

    public static final void registerHandler(IEventHandler handler, EventType type) {
        synchronized (hookLock) {
            if (handlers == null) {
                handlers = new ArrayList[128];
                Arrays.fill(handlers, null);
            }
            if (handlers.length < EventType.getMaxIndex()) {
                ArrayList[] temp = new ArrayList[EventType.getMaxIndex()];
                Arrays.fill(temp, null);
                System.arraycopy(handlers, 0, temp, 0, handlers.length);
                handlers = temp;
            }
            if (handlers[type.toInt()] == null) {
                handlers[type.toInt()] = new ArrayList();
            }
            handlers[type.toInt()].add(handler);
        }
    }

    public static final void deregisterHook(IEventHook hook, EventType type) {
        synchronized (hookLock) {
            try {
                hooks[type.toInt()].remove(hook);
            } catch (Exception e) {
                log.error("deregisterHook: " + e.getLocalizedMessage(), e);
            }
        }
    }

    public static final void deregisterHandler(IEventHandler handler, EventType type) {
        synchronized (hookLock) {
            try {
                handlers[type.toInt()].remove(handler);
            } catch (Exception e) {
                log.error("deregisterHandler: " + e.getLocalizedMessage(), e);
            }
        }
    }

    public final void handleEvent(IEvent e) {
        if (EventType.SYS_MEMORY_PROFILE == e.getType()) {
            log.info("profile事件: " + e);
        } else log.debug("全局事件处理器:" + e);
    }

    public static void postEvent(IEvent e) {
        gHandler.post(e);
    }

    public final void post(IEvent e) {
        boolean hooked = false;
        ArrayList list = hooks[e.getType().toInt()];
        if ((list != null) && (list.size() > 0)) {
            try {
                for (IEventHook hook : list) {
                    hook.postEvent(e);
                }
                hooked = true;
            } catch (Exception exp) {
                log.error("hook.postEvent:" + exp.getLocalizedMessage(), exp);
            }

        }

        if ((handlers != null) && (handlers.length > e.getType().toInt())) {
            ArrayList arHandlers = handlers[e.getType().toInt()];
            if ((arHandlers != null) && (arHandlers.size() > 0)) {
                hooked = true;
                for (IEventHandler handler : arHandlers) {
                    try {
                        handler.handleEvent(e);
                    } catch (Exception exp) {
                        log.error("handler.handleEvent: " + exp.getLocalizedMessage(), exp);
                    }
                }
            }

        }

        if (hooked) return;
        try {
            this.queue.offer(e);
        } catch (Exception arHandlers) {
            String info = "全局事件队列插入失败。event=" + e.toString();
            tracer.trace(info, exp);
            log.error(info, exp);
        }
    }

    private class EventPumpThread extends Thread {
        public EventPumpThread() {
            super("GlobalEventPumpThread");
            setDaemon(true);
        }

        public void run() {
            GlobalEventHandler.this.state = State.RUNNING;
            GlobalEventHandler.log.info("Global event handler thread running");
            long pre = System.currentTimeMillis();
            int cnt = 0;
            label129:
            while (GlobalEventHandler.this.state != State.STOPPING) {
                try {
                    IEvent e = GlobalEventHandler.this.queue.take();
                    if (e == null) {
                        ++cnt;
                        if (cnt >= 20) {
                            long now = System.currentTimeMillis();
                            if (Math.abs(now - pre) < 1000L) {
                                GlobalEventHandler.log.warn("检测到死循环。");
                            }
                            pre = System.currentTimeMillis();
                            cnt = 0;

                            break label129:}
                    }
                    GlobalEventHandler.this.handleEvent(e);
                } catch (Exception e) {
                    GlobalEventHandler.log.warn("Global event handler event pump catch exception:" + e.getLocalizedMessage());
                }
            }
            GlobalEventHandler.this.state = State.STOPPED;
        }
    }
}