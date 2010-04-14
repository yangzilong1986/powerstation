/*
 * 定时器执行的函数对象
 */
package pep.common.timer;

/**
 *
 * @author Thinkpad
 */
public interface ITimerFunctor {

    /**
     * 定时器触发的函数。
     * 每个对象，允许多个定时器，由ID进行区分。
     * @param id：哪个定时器被触发。
     */
    void onTimer(int id);
}
