package com.hzjbbis.fk.common.simpletimer;

public class TimerData {
    private ITimerFunctor functor;
    private int id = 0;
    private long period = 60000L;

    private long lastActivate = System.currentTimeMillis();

    public TimerData() {
    }

    public TimerData(ITimerFunctor src, int id, long period) {
        this.functor = src;
        this.id = id;
        this.period = (period * 1000L);
    }

    public ITimerFunctor getFunctor() {
        return this.functor;
    }

    public void setFunctor(ITimerFunctor functor) {
        this.functor = functor;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPeriod() {
        return this.period;
    }

    public void setPeriod(long period) {
        this.period = (period * 1000L);
    }

    public void activate() {
        this.lastActivate = System.currentTimeMillis();
        this.functor.onTimer(this.id);
    }

    public long distance() {
        return (this.lastActivate + this.period - System.currentTimeMillis());
    }
}