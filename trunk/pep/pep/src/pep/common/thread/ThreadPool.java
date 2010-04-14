/*
 * 通用线程池
 */
package pep.common.thread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.*;

import pep.common.timer.ITimerFunctor;
/**
 *
 * @author Thinkpad
 */
public class ThreadPool implements ITimerFunctor{
    //静态属性

    private static final Logger log = LoggerFactory .getLogger(ThreadPool.class);
    /**
     * 线程池名字，以便监控模块监控该线程池的执行状况。静态map来维护唯一的线程池名字。
     */
    private String name = "threadpool";
    private static int poolSeq = 1;
    private int timeoutAlarm = 2;		//事件处理时间超时警告，如数据保持超过2秒，需要告警。
    private int minSize = 1;		//线程池最小个数
    private int maxSize = 20;		//线程池最大个数
    private int timeout = 200;		//对于批量处理，如果timeout事件内没有取足批量上限内容，则触发批量执行事件。
    //对象内部状态
    private volatile State state = new State();
    private IEventHandler executer = null; //线程池的执行函数
    private List<WorkThread> works = Collections.synchronizedList(new ArrayList<WorkThread>());
    private int threadPriority = Thread.NORM_PRIORITY;
    private EventQueue eventQueue;

    /**
     * 构造函数
     * @param exec ：工作线程的执行对象
     * @param queue: 事件队列
     */
    public ThreadPool(IEventHandler exec, EventQueue queue) {
        executer = exec;
        eventQueue = queue;
    }

    public boolean start() {
        if (!state.isStopped()) {
            return false;
        }
        state = State.STARTING;

        forkThreads(minSize);
        while (works.size() < minSize) {
            Thread.yield();
            try {
                Thread.sleep(100);
            } catch (Exception exp) {
            }
        }
        //启动定时服务
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 30));	//定时器0，每30秒定时器
        state = State.RUNNING;
        if (log.isDebugEnabled()) {
            log.debug("线程池【" + name + "】启动成功。,size=" + minSize);
        }
        return true;
    }

    public void stop() {
        state = State.STOPPING;
        synchronized (works) {
            for (WorkThread work : works) {
                work.interrupt();
            }
        }
        int cnt = 40;
        while (cnt-- > 0 && works.size() > 0) {
            Thread.yield();
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
            if (cnt < 20) {
                continue;
            }
            synchronized (works) {
                for (WorkThread work : works) {
                    work.interrupt();
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("线程池【" + name + "】停止。,僵死线程数=" + works.size());
        }
        works.clear();
        state = State.STOPPED;
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(256);
        sb.append("<threadpool name=\"").append(name).append("\">");
        sb.append("<minSize value=\"").append(minSize).append("\"/>");
        sb.append("<maxSize value=\"").append(maxSize).append("\"/>");
        sb.append("<size value=\"").append(size()).append("\"/>");
        sb.append("<timeoutAlarm value=\"").append(timeoutAlarm).append("\"/>");
        sb.append("<works>").append(this.toString()).append("</works>");
        sb.append("</threadpool>");
        return sb.toString();
    }

    public boolean isActive() {
        return state.isActive();
    }

    public int size() {
        return works.size();
    }

    public void onTimer(int id) {
        if (0 == id) {
            ArrayList<WorkThread> list = new ArrayList<WorkThread>(works);
            int count = 0;
            for (WorkThread work : list) {
                if (work.checkTimeout()) {
                    //超时。
                    count++;
                }
            }
        }
    }

    private void forkThreads(int delta) {
        if (delta == 0) {
            return;
        }

        if (delta > 0) {
            //不能超过最大值
            int maxDelta = this.maxSize - works.size();
            delta = Math.min(maxDelta, delta);
            if (log.isDebugEnabled() && 1 == delta) {
                log.debug("调整线程池大小(+1)");
            }
            for (; delta > 0; delta--) {
                new WorkThread();
            }
        } else {
            //不能小于1
            delta = -delta;
            int n = works.size() - minSize;		//最多允许减少的线程数
            delta = Math.min(delta, n);
            if (log.isDebugEnabled() && -1 == delta) {
                log.debug("调整线程池大小(-1)");
            }
            for (; delta > 0; delta--) {
                try {
                    eventQueue.addFirst(new KillThreadEvent());
                } catch (Exception exp) {
                    log.error(exp.getLocalizedMessage());
                }
            }
        }
    }

    private void justThreadSize() {
        int n = eventQueue.size();
        if (n > 1000) {
            forkThreads(1);
        } else if (n < 2) {
            forkThreads(-1);
        }
    }

    private class WorkThread extends Thread {

        long beginTime;
        boolean busy = false;		//判断实现是否处于工作状态
        IEvent currentEvent = null;

        public WorkThread() {
            super(ThreadPool.this.name + "." + poolSeq++);
            super.start();
        }

        public void run() {
            synchronized (works) {
                works.add(this);
            }
            this.setPriority(threadPriority);
            int count = 0;		//每处理1000个事件检测线程池是否需要调整
            log.info("threadpool.work running:" + this.getName());
            while (!ThreadPool.this.state.isStopping() && !ThreadPool.this.state.isStopped()) {
                try {
                    busy = false;
                    currentEvent = ThreadPool.this.eventQueue.take();
                    if (null == currentEvent) //JDK存在缺陷，Object.wait()可能突然返回
                    {
                        continue;
                    }
                    if (currentEvent.getType() == EventType.SYS_KILLTHREAD) {
                        break;
                    }
                    processEvent(currentEvent);
                    //检测队列中事件个数。如果太多，增加线程。如果为0，减少线程
                    count++;
                    if (count > 500) {
                        justThreadSize();
                        count = 0;
                    }
                } catch (Exception exp) {
                    //线程被中断。检查是否需要关闭
                    continue;
                }
            }
            synchronized (works) {
                works.remove(this);
            }
            log.info("线程池的工作线程退出:" + this.getName());
        }

        /**
         * 事件处理过程。需要检测事件处理时间过长情况。
         * @param event
         */
        private void processEvent(IEvent event) {
            beginTime = System.currentTimeMillis();
            busy = true;
            executer.handleEvent(event);
            long endTime = System.currentTimeMillis();
            if (endTime - beginTime > timeoutAlarm * 1000) {
                //事件处理超时告警
                EventHandleTimeoutAlarm ev = new EventHandleTimeoutAlarm(event);
                ev.setBeginTime(beginTime);
                ev.setEndTime(endTime);
                ev.setThreadName(WorkThread.this.getName());
                GlobalEventHandler.postEvent(ev);
            }
        }

        public boolean checkTimeout() {
            if (!busy) {
                return false;
            }
            long endTime = System.currentTimeMillis();
            if (endTime - beginTime > timeoutAlarm * 1000) {
                //事件处理超时告警
                EventHandleTimeoutAlarm ev = new EventHandleTimeoutAlarm(currentEvent);
                ev.setBeginTime(beginTime);
                ev.setEndTime(endTime);
                ev.setStackTraceElement(this.getStackTrace());
                tracer.trace(ev);
                GlobalEventHandler.postEvent(ev);
                this.interrupt();
            }
            return true;
        }

        public String toString() {
            String busyStatus = "idle";
            if (busy) {
                long timeConsume = System.currentTimeMillis() - beginTime;
                busyStatus = "当前处理时间(毫秒):" + timeConsume;
            }
            return "[" + getName() + "," + busyStatus + "];";
        }
    }

    public void setEventQueue(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public int getTimeoutAlarm() {
        return timeoutAlarm;
    }

    public void setTimeoutAlarm(int timeoutAlarm) {
        this.timeoutAlarm = timeoutAlarm;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(256);
        sb.append(name);
        try {
            for (WorkThread work : works) {
                sb.append(work);
            }
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }
}
