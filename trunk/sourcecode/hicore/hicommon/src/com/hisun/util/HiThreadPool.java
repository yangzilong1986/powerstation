package com.hisun.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HiThreadPool extends ThreadPoolExecutor {
    public HiThreadPool() {
        super(5, 50, 60L, TimeUnit.SECONDS, new SynchronousQueue());
    }

    private HiThreadPool(int min, int max, int queue) {
        super(min, max, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(queue));
    }

    private HiThreadPool(int min, int max) {
        super(min, max, 60L, TimeUnit.SECONDS, new SynchronousQueue());
    }

    public static HiThreadPool createThreadPool() {
        return new HiThreadPool();
    }

    public static HiThreadPool createThreadPool(int minThreads, int maxThreads) {
        return new HiThreadPool(minThreads, maxThreads);
    }

    public static HiThreadPool createThreadPool(int minThreads, int maxThreads, int queueSize) {
        return new HiThreadPool(minThreads, maxThreads, queueSize);
    }

    public static HiThreadPool createThreadPool(String name) {
        HiThreadPool pool = new HiThreadPool();
        if (name != null) pool.setThreadFactory(hisunThreadFactory(name));
        return pool;
    }

    public static HiThreadPool createTimeOutCtlThreadPool(int timeout) {
        return new TimeOutCtlThreadPool(timeout);
    }

    public static ThreadFactory hisunThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

        AtomicInteger threadNumber = new AtomicInteger(1);
        String namePrefix = "hisun-pool-" + name + "-thread-";

        return new ThreadFactory(group, namePrefix, threadNumber) {
            private final ThreadGroup val$group;
            private final String val$namePrefix;
            private final AtomicInteger val$threadNumber;

            public Thread newThread(Runnable r) {
                Thread t = new Thread(this.val$group, r, this.val$namePrefix + this.val$threadNumber.getAndIncrement(), 0L);

                if (t.isDaemon()) t.setDaemon(false);
                if (t.getPriority() != 5) t.setPriority(5);
                return t;
            }
        };
    }
}