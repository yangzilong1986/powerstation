package com.hzjbbis.fk.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Counter {
    private static final Log log = LogFactory.getLog(Counter.class);
    public static final long DEFAULT_LIMIT_VAL = 1000L;
    private long count;
    private long limit;
    private long time;
    private long guard;
    private long speed;
    private String name;

    public Counter() {
        this(1000L, "");
    }

    public Counter(long limit, String name) {
        this.time = System.currentTimeMillis();
        this.count = 0L;
        this.limit = limit;
        this.guard = 0L;
        this.speed = 0L;
        this.name = name;
    }

    public synchronized void add() {
        try {
            this.count += 1L;
            this.guard += 1L;
            if (this.guard >= this.limit) {
                this.speed = (this.guard * 60000L / (System.currentTimeMillis() - this.time));
                this.time = System.currentTimeMillis();
                this.guard = 0L;
                log.info(" counter--" + this.name + "'s speed is:" + this.speed + "/min , sum is " + this.count);
            }
        } catch (Exception e) {
            this.count = 0L;
            this.guard = 0L;
        }
    }

    public synchronized void add(long cc) {
        try {
            this.count += cc;
            this.guard += cc;
            if (this.guard >= this.limit) {
                long speed = this.guard * 60000L / (System.currentTimeMillis() - this.time);
                this.time = System.currentTimeMillis();
                this.guard = 0L;
                log.info(" counter--" + this.name + "'s speed is:" + speed + "/min , sum is " + this.count);
            }
        } catch (Exception localException) {
        }
    }

    public long getSpeed() {
        return this.speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}