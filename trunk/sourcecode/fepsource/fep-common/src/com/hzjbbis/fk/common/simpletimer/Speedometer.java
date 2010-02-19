package com.hzjbbis.fk.common.simpletimer;

public class Speedometer implements ITimerFunctor {
    private int pointCount = 60;
    private int[] mpoints;
    private final Object lock = new Object();
    private int curPosition = 0;

    public Speedometer() {
        this.mpoints = new int[this.pointCount + 1];
        for (int i = 0; i < this.mpoints.length; ++i)
            this.mpoints[i] = 0;
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 60L));
    }

    public Speedometer(int pointCount) {
        if (pointCount < 1) pointCount = 60;
        this.pointCount = pointCount;
        this.mpoints = new int[this.pointCount + 1];
        for (int i = 0; i < this.mpoints.length; ++i)
            this.mpoints[i] = 0;
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 60L));
    }

    private void moveNext() {
        this.curPosition += 1;
        if (this.curPosition > this.pointCount) this.curPosition = 0;
        this.mpoints[this.curPosition] = 0;
    }

    public void onTimer(int id) {
        synchronized (this.lock) {
            moveNext();
        }
    }

    public void add(int flow) {
        synchronized (this.lock) {
            this.mpoints[this.curPosition] += flow;
        }
    }

    public int getSpeed() {
        int speed = 0;
        synchronized (this.lock) {
            for (int i = 0; i < this.mpoints.length; ++i) {
                if (i != this.curPosition) speed += this.mpoints[i];
            }
        }
        return speed;
    }

    public int getSpeed1() {
        int speed = 0;
        speed = this.mpoints[this.curPosition];
        return speed;
    }
}