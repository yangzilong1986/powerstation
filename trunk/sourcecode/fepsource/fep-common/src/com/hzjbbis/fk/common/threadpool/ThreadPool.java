package com.hzjbbis.fk.common.threadpool;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.EventQueue;
import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.events.event.EventHandleTimeoutAlarm;
import com.hzjbbis.fk.common.events.event.KillThreadEvent;
import com.hzjbbis.fk.common.simpletimer.ITimerFunctor;
import com.hzjbbis.fk.common.simpletimer.TimerData;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadPool implements ITimerFunctor {
    private static final Logger log = Logger.getLogger(ThreadPool.class);
    private static final TraceLog tracer = TraceLog.getTracer();

    private String name = "threadpool";
    private static int poolSeq = 1;
    private int timeoutAlarm = 2;

    private int minSize = 1;
    private int maxSize = 20;
    private int timeout = 200;

    private volatile State state = new State();
    private IEventHandler executer = null;
    private List<WorkThread> works = Collections.synchronizedList(new ArrayList());
    private int threadPriority = 5;
    private EventQueue eventQueue;

    public ThreadPool(IEventHandler exec, EventQueue queue) {
        this.executer = exec;
        this.eventQueue = queue;
    }

    public boolean start() {
        if (!(this.state.isStopped())) return false;
        this.state = State.STARTING;

        forkThreads(this.minSize);
        while (this.works.size() < this.minSize) {
            Thread.yield();
            try {
                Thread.sleep(100L);
            } catch (Exception localException) {
            }
        }
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 30L));
        this.state = State.RUNNING;
        if (log.isDebugEnabled()) log.debug("线程池【" + this.name + "】启动成功。,size=" + this.minSize);
        return true;
    }

    public void stop() {
        this.state = State.STOPPING;
        synchronized (this.works) {
            for (WorkThread work : this.works) {
                work.interrupt();
            }
        }
        int cnt = 40;
        while ((cnt-- > 0) && (this.works.size() > 0)) {
            Thread.yield();
            try {
                Thread.sleep(50L);
            } catch (Exception localException) {
            }
            if (cnt < 20) continue;
            synchronized (this.works) {
                for (WorkThread work : this.works) {
                    work.interrupt();
                }
            }
        }
        if (log.isDebugEnabled()) log.debug("线程池【" + this.name + "】停止。,僵死线程数=" + this.works.size());
        this.works.clear();
        this.state = State.STOPPED;
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(256);
        sb.append("<threadpool name=\"").append(this.name).append("\">");
        sb.append("<minSize value=\"").append(this.minSize).append("\"/>");
        sb.append("<maxSize value=\"").append(this.maxSize).append("\"/>");
        sb.append("<size value=\"").append(size()).append("\"/>");
        sb.append("<timeoutAlarm value=\"").append(this.timeoutAlarm).append("\"/>");
        sb.append("<works>").append(toString()).append("</works>");
        sb.append("</threadpool>");
        return sb.toString();
    }

    public boolean isActive() {
        return this.state.isActive();
    }

    public int size() {
        return this.works.size();
    }

    public void onTimer(int id) {
        if (id == 0) {
            ArrayList list = new ArrayList(this.works);
            int count = 0;
            for (WorkThread work : list) {
                if (!(work.checkTimeout())) continue;
                ++count;
            }
        }
    }

    private void forkThreads(int delta) {
        if (delta == 0) {
            return;
        }
        if (delta > 0) {
            int maxDelta = this.maxSize - this.works.size();
            delta = Math.min(maxDelta, delta);
            if ((log.isDebugEnabled()) && (1 == delta)) log.debug("调整线程池大小(+1)");
            for (; delta > 0; --delta)
                new WorkThread();
        } else {
            delta = -delta;
            int n = this.works.size() - this.minSize;
            delta = Math.min(delta, n);
            if ((log.isDebugEnabled()) && (-1 == delta)) log.debug("调整线程池大小(-1)");
            for (; delta > 0; --delta)
                try {
                    this.eventQueue.addFirst(new KillThreadEvent());
                } catch (Exception exp) {
                    log.error(exp.getLocalizedMessage());
                }
        }
    }

    private void justThreadSize() {
        int n = this.eventQueue.size();
        if (n > 1000) {
            forkThreads(1);
        } else if (n < 2) forkThreads(-1);
    }

    public void setEventQueue(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public int getTimeoutAlarm() {
        return this.timeoutAlarm;
    }

    public void setTimeoutAlarm(int timeoutAlarm) {
        this.timeoutAlarm = timeoutAlarm;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThreadPriority() {
        return this.threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(256);
        sb.append(this.name);
        try {
            for (WorkThread work : this.works)
                sb.append(work);
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }

    private class WorkThread extends Thread {
        long beginTime;
        boolean busy = false;
        IEvent currentEvent = null;

        public WorkThread() {
            super(ThreadPool.this.name + "." + (ThreadPool.poolSeq++));
            super.start();
        }

        public void run() {
            synchronized (ThreadPool.this.works) {
                ThreadPool.this.works.add(this);
            }
            setPriority(ThreadPool.this.threadPriority);
            int count = 0;
            ThreadPool.log.info("threadpool.work running:" + getName());
            while ((!(ThreadPool.this.state.isStopping())) && (!(ThreadPool.this.state.isStopped()))) try {
                this.busy = false;
                this.currentEvent = ThreadPool.this.eventQueue.take();
                if (this.currentEvent != null) {
                    if (this.currentEvent.getType() == EventType.SYS_KILLTHREAD) break label177;
                    processEvent(this.currentEvent);

                    ++count;
                    if (count > 500) {
                        ThreadPool.this.justThreadSize();
                        count = 0;
                    }
                }
            } catch (Exception localException) {
            }
            synchronized (ThreadPool.this.works) {
                label177:
                ThreadPool.this.works.remove(this);
            }
            ThreadPool.log.info("线程池的工作线程退出:" + getName());
        }

        private void processEvent(IEvent event) {
            this.beginTime = System.currentTimeMillis();
            this.busy = true;
            ThreadPool.this.executer.handleEvent(event);
            long endTime = System.currentTimeMillis();
            if (endTime - this.beginTime <= ThreadPool.this.timeoutAlarm * 1000) return;
            EventHandleTimeoutAlarm ev = new EventHandleTimeoutAlarm(event);
            ev.setBeginTime(this.beginTime);
            ev.setEndTime(endTime);
            ev.setThreadName(getName());
            GlobalEventHandler.postEvent(ev);
        }

        public boolean checkTimeout() {
            if (!(this.busy)) return false;
            long endTime = System.currentTimeMillis();
            if (endTime - this.beginTime > ThreadPool.this.timeoutAlarm * 1000) {
                EventHandleTimeoutAlarm ev = new EventHandleTimeoutAlarm(this.currentEvent);
                ev.setBeginTime(this.beginTime);
                ev.setEndTime(endTime);
                ev.setStackTraceElement(getStackTrace());
                ThreadPool.tracer.trace(ev);
                GlobalEventHandler.postEvent(ev);
                interrupt();
            }
            return true;
        }

        public String toString() {
            String busyStatus = "idle";
            if (this.busy) {
                long timeConsume = System.currentTimeMillis() - this.beginTime;
                busyStatus = "当前处理时间(毫秒):" + timeConsume;
            }
            return "[" + getName() + "," + busyStatus + "];";
        }
    }
}