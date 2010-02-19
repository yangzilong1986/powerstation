package com.hzjbbis.fk.common.simpletimer;

import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;

import java.util.*;

public class TimerScheduler {
    private static final Logger log = Logger.getLogger(TimerScheduler.class);
    private static final TraceLog tracer = TraceLog.getTracer(TimerScheduler.class);
    private List<TimerData> timers = new LinkedList();
    private final Object lock = new Object();
    private final ScheduleThread thread = new ScheduleThread();
    private static TimerScheduler timerScheduler = null;
    private volatile boolean running = false;
    private final Timer sysTimer = new Timer("TimerScheduler.sys", true);

    private TimerScheduler() {
        this.running = true;
        this.thread.setDaemon(true);
        this.thread.start();
    }

    public void destroy() {
        this.running = false;
        this.thread.interrupt();
        this.sysTimer.cancel();
    }

    public static final TimerScheduler getScheduler() {
        if (timerScheduler == null) {
            timerScheduler = new TimerScheduler();
        }
        return timerScheduler;
    }

    public void schedulerOnce(TimerTask task, Date time) {
        this.sysTimer.schedule(task, time);
    }

    public void addTimer(TimerData item) {
        if (!(this.running)) return;
        synchronized (this.lock) {
            removeTimer(item.getFunctor(), item.getId());
            this.timers.add(item);
            this.lock.notifyAll();
        }
    }

    public void removeTimer(ITimerFunctor source, int id) {
        if (!(this.running)) return;
        synchronized (this.lock) {
            for (TimerData item : this.timers)
                if ((item.getFunctor() == source) && (item.getId() == id)) {
                    this.timers.remove(item);
                    this.lock.notifyAll();
                    break;
                }
        }
    }

    private TimerData getNearestTimer() {
        TimerData result = null;
        long minDistance = 9223372036854775807L;
        synchronized (this.lock) {
            for (TimerData item : this.timers) {
                if (item.distance() < minDistance) {
                    result = item;
                    minDistance = item.distance();
                }
            }
        }

        return result;
    }

    public void setTimers(List<TimerData> timers) {
        synchronized (this.lock) {
            this.timers = timers;
        }
    }

    private class ScheduleThread extends Thread {
        public ScheduleThread() {
            super("gTimerThread");
        }

        public void run() {
            while (TimerScheduler.this.running) try {
                doWork();
            } catch (InterruptedException localInterruptedException) {
            } catch (Exception e) {
                TimerScheduler.log.error(e.getLocalizedMessage(), e);
            }
        }

        private void doWork() throws InterruptedException {
            synchronized (TimerScheduler.this.lock) {
                TimerData item = TimerScheduler.this.getNearestTimer();
                long waitTime = (item == null) ? 1000L : item.distance();
                if (waitTime <= 0L) {
                    waitTime = 20L;
                }
                TimerScheduler.this.lock.wait(waitTime);
                if ((item != null) && (item.distance() <= 0L)) {
                    TimerScheduler.tracer.trace("执行定时器：" + item.getFunctor());
                    item.activate();
                }
            }
        }
    }
}