/*定时器调度器
 */
package pep.common.timer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.*;
/**
 *
 * @author Thinkpad
 */
public class TimerScheduler {

    private static final Logger log = LoggerFactory .getLogger(TimerScheduler.class);
    //private static final TraceLog tracer = TraceLog.getTracer(TimerScheduler.class);
    private List<TimerData> timers = new LinkedList<TimerData>();
    private final Object lock = new Object();
    private final ScheduleThread thread = new ScheduleThread();
    private static TimerScheduler timerScheduler = null;
    private volatile boolean running = false;
    private final Timer sysTimer = new Timer("TimerScheduler.sys", true);

    private TimerScheduler() {
        running = true;
        thread.setDaemon(true);
        thread.start();
    }

    public void destroy() {
        running = false;
        thread.interrupt();
        sysTimer.cancel();
    }

    public static final TimerScheduler getScheduler() {
        if (null == timerScheduler) {
            timerScheduler = new TimerScheduler();
        }
        return timerScheduler;
    }

    public void schedulerOnce(TimerTask task, Date time) {
        sysTimer.schedule(task, time);
    }

    public void addTimer(TimerData item) {
        if (!running) {
            return;
        }
        synchronized (lock) {
            removeTimer(item.getFunctor(), item.getId());
            timers.add(item);
            lock.notifyAll();
        }
    }

    public void removeTimer(ITimerFunctor source, int id) {
        if (!running) {
            return;
        }
        synchronized (lock) {
            for (TimerData item : timers) {
                if (item.getFunctor() == source && item.getId() == id) {
                    timers.remove(item);
                    lock.notifyAll();
                    break;
                }
            }
        }
    }

    private TimerData getNearestTimer() {
        TimerData result = null;
        long minDistance = Long.MAX_VALUE;
        synchronized (lock) {
            for (TimerData item : timers) {
                if (item.distance() < minDistance) {
                    result = item;
                    minDistance = item.distance();
                    continue;
                }
            }
        }
        return result;
    }

    private class ScheduleThread extends Thread {

        public ScheduleThread() {
            super("gTimerThread");
        }

        @Override
        public void run() {
            while (running) {
                try {
                    doWork();
                } catch (InterruptedException e) {
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage(), e);
                }
            }
        }

        private void doWork() throws InterruptedException {
            synchronized (lock) {
                TimerData item = getNearestTimer();
                long waitTime = null == item ? 1000 : item.distance();
                if (waitTime <= 0) {
                    waitTime = 20;
                }
                lock.wait(waitTime);
                if (null != item && item.distance() <= 0) {
                    log.trace("执行定时器：" + item.getFunctor());
                    item.activate();
                }
            }
        }
    }

    public void setTimers(List<TimerData> timers) {
        synchronized (lock) {
            this.timers = timers;
        }
    }
}
