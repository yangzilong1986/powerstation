package com.hzjbbis.fk.common.events;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.common.spi.IEventHook;
import com.hzjbbis.fk.common.spi.IEventTrace;
import com.hzjbbis.fk.common.spi.abstra.BaseModule;
import com.hzjbbis.fk.common.threadpool.ThreadPool;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;

import java.util.*;

public class BasicEventHook extends BaseModule implements IEventHook {
    private static final Logger log = Logger.getLogger(BasicEventHook.class);
    private static final TraceLog tracer = TraceLog.getTracer();

    protected int minSize = 2;
    protected int maxSize = 20;
    protected int timeoutAlarm = 2;
    protected String name = "evhandler";
    protected Map<EventType, IEventHandler> handlerMap;
    protected Object[] sources = null;
    protected IEventTrace eventTrace = null;
    protected Map<EventType, IEventHandler> include;
    protected List<EventType> exclude;
    protected int threadPriority = Math.max(8, 5);
    protected EventQueue queue = new EventQueue();

    protected ThreadPool pool = null;
    private boolean initialized = false;
    protected IEventHandler[] handlers = new IEventHandler[EventType.getMaxIndex() + 1];
    private long lastEventTime = System.currentTimeMillis();

    public String getModuleType() {
        return "eventHook";
    }

    public boolean isActive() {
        return ((this.pool == null) || (!(this.pool.isActive())));
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(256);
        sb.append("\r\n<eventhook-profile type=\"").append(getModuleType()).append("\">");
        sb.append("\r\n    ").append("<name>").append(this.name).append("</name>");
        sb.append("\r\n    ").append("<state>").append(isActive()).append("</state>");
        sb.append("\r\n    ").append("<queue-size>").append(this.queue.size()).append("</queue-size>");
        sb.append("\r\n    ").append("<source>").append(getSource()).append("</source>");

        sb.append("\r\n    ").append("<tp-minsize>").append(this.pool.getMinSize()).append("</tp-minsize>");
        sb.append("\r\n    ").append("<tp-maxsize>").append(this.pool.getMaxSize()).append("</tp-maxsize>");
        sb.append("\r\n    ").append("<tp-size>").append(this.pool.size()).append("</tp-size>");
        sb.append("\r\n    ").append("<tp-timeout-alarm>").append(this.pool.getTimeoutAlarm()).append("</tp-timeout-alarm>");
        sb.append("\r\n    ").append("<tp-works>").append(this.pool.toString()).append("</tp-works>");
        sb.append("\r\n</eventhook-profile>");
        return sb.toString();
    }

    public void init() {
        Iterator it;
        if (this.initialized) return;
        if (this.handlerMap == null) this.handlerMap = new HashMap();
        if (this.include != null) {
            it = this.include.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                this.handlerMap.put((EventType) entry.getKey(), (IEventHandler) entry.getValue());
            }
        }
        if (this.exclude != null) {
            it = this.exclude.iterator();
            while (it.hasNext()) this.handlerMap.remove(it.next());
        }
        this.initialized = true;
    }

    public boolean canHook(IEvent e) {
        if (this.sources == null) return true;
        if (this.sources != null) {
            for (Object iter : this.sources) {
                if (iter == e.getSource()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void postEvent(IEvent e) {
        if (!(canHook(e))) {
            return;
        }

        int cnt = 10;
        try {
            this.queue.offer(e);
        } catch (Exception exp) {
            do {
                String info = "BasicEventHook can not postEvent. reason is " + exp.getLocalizedMessage() + ". event is" + e.toString();
                log.fatal(info, exp);
                tracer.trace(info, exp);
                try {
                    Thread.sleep(50L);
                } catch (Exception localException1) {
                }
            } while (cnt-- > 0);
        }
    }

    protected void createThreadPool() {
        this.pool = new ThreadPool(this, this.queue);
        this.pool.setThreadPriority(this.threadPriority);
        this.pool.setMinSize(this.minSize);
        this.pool.setMaxSize(this.maxSize);
        this.pool.setTimeoutAlarm(this.timeoutAlarm);
        this.pool.setName(this.name);
    }

    public boolean start() {
        init();
        if ((this.pool != null) && (this.pool.isActive())) {
            return true;
        }
        for (EventType type : this.handlerMap.keySet()) {
            if (type.toInt() > this.handlers.length) {
                IEventHandler[] temp = new IEventHandler[type.toInt() * 2];
                System.arraycopy(this.handlers, 0, temp, 0, this.handlers.length);
                this.handlers = temp;
            }

            this.handlers[type.toInt()] = ((IEventHandler) this.handlerMap.get(type));
            GlobalEventHandler.registerHook(this, type);
        }
        createThreadPool();
        this.pool.start();

        return true;
    }

    public void stop() {
        this.queue.enableOffer(false);
        int cnt = 100;
        while ((cnt-- > 0) && (this.queue.size() > 0) && (this.queue.enableTake())) try {
            Thread.sleep(50L);
        } catch (Exception localException) {
        }
        for (EventType type : this.handlerMap.keySet()) {
            GlobalEventHandler.deregisterHook(this, type);
        }
        if (this.pool != null) this.pool.stop();
        this.pool = null;
    }

    public void handleEvent(IEvent event) {
        IEventHandler handler = this.handlers[event.getType().toInt()];
        if (handler != null) {
            handler.handleEvent(event);
            this.lastEventTime = System.currentTimeMillis();

            if (this.eventTrace == null) return;
            try {
                this.eventTrace.traceEvent(event);
            } catch (Exception e) {
                log.warn("事件跟踪器执行异常:" + e.getLocalizedMessage(), e);
            }
        }
    }

    public long getLastEventTime() {
        return this.lastEventTime;
    }

    public int getMinSize() {
        return this.minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getTimeoutAlarm() {
        return this.timeoutAlarm;
    }

    public void setTimeoutAlarm(int timeoutAlarm) {
        this.timeoutAlarm = timeoutAlarm;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<EventType, IEventHandler> getHandlerMap() {
        return this.handlerMap;
    }

    public void setHandlerMap(Map<EventType, IEventHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void addHandler(EventType type, IEventHandler handler) {
        if (this.handlerMap == null) this.handlerMap = new HashMap();
        this.handlerMap.put(type, handler);
    }

    public int getThreadPriority() {
        return this.threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public Object getSource() {
        if ((this.sources != null) && (this.sources.length > 0)) {
            return this.sources[0];
        }
        return null;
    }

    public void setSource(Object source) {
        if (this.sources == null) {
            this.sources = new Object[1];
            this.sources[0] = source;
        } else {
            for (Object src : this.sources) {
                if (src == source) return;
            }
            Object[] dest = new Object[this.sources.length + 1];
            System.arraycopy(this.sources, 0, dest, 0, this.sources.length);
            dest[this.sources.length] = source;
            this.sources = null;
            this.sources = dest;
        }
    }

    public void addSource(Object source) {
        setSource(source);
    }

    public void setSource(List<Object> srcs) {
        if ((srcs == null) || (srcs.size() == 0)) return;
        setSource(srcs.toArray());
    }

    public void setSource(Object[] srcs) {
        if (this.sources == null) {
            this.sources = srcs;
        } else {
            Vector uniqs = new Vector();
            for (Object src : srcs) {
                boolean found = false;
                for (Object iter : this.sources) {
                    if (iter == src) {
                        found = true;
                        break;
                    }
                }
                if (!(found)) uniqs.add(src);
            }
            Object[] srcss = uniqs.toArray();
            Object[] dest = new Object[this.sources.length + srcss.length];
            System.arraycopy(this.sources, 0, dest, 0, this.sources.length);
            System.arraycopy(srcss, 0, dest, this.sources.length, srcss.length);
            this.sources = null;
            srcss = (Object[]) null;
            this.sources = dest;
        }
    }

    public void addSource(Object[] srcs) {
        setSource(srcs);
    }

    public void setExclude(List<EventType> exclude) {
        this.exclude = exclude;
    }

    public void setInclude(Map<EventType, IEventHandler> include) {
        this.include = include;
    }

    public void setEventTrace(IEventTrace eTrace) {
        this.eventTrace = eTrace;
    }

    public void setQueue(EventQueue queue) {
        this.queue = queue;
    }
}