/*
 * 被TimerScheduler使用
 */
package pep.common.timer;

/**
 *
 * @author Thinkpad
 */
public class TimerData {
//配置参数

    private ITimerFunctor functor;
    private int id = 0;
    private long period = 60 * 1000;	//间隔（秒）

    public TimerData() {
    }

    public TimerData(ITimerFunctor src, int id, long period) {
        this.functor = src;
        this.id = id;
        this.period = period * 1000;
    }
    //被TimerShceduler使用的参数
    private long lastActivate = System.currentTimeMillis();

    public ITimerFunctor getFunctor() {
        return functor;
    }

    public void setFunctor(ITimerFunctor functor) {
        this.functor = functor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period * 1000;
    }

    /**
     * 激活定时器对象
     */
    public void activate() {
        lastActivate = System.currentTimeMillis();
        functor.onTimer(id);
    }

    /**
     * 返回本定时器距离触发还剩余的毫秒数。
     * @return
     */
    public long distance() {
        return lastActivate + period - System.currentTimeMillis();
    }
}
