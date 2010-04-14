/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.common.thread;

/**
 *
 * @author Thinkpad
 */
public class State {

    public static final State STOPPED = new State("停止状态", 0);
    public static final State STARTING = new State("正在启动", 1);
    public static final State RUNNING = new State("运行状态", 2);
    public static final State STOPPING = new State("正在停止", 3);
    public static final State RESTART = new State("自动重新启动", 4);
    private String desc;
    private int state;

    public State() {
        this.state = 0;
        this.desc = "停止状态";
    }

    private State(String desc, int val) {
        this.state = val;
        this.desc = desc;
    }

    public String toString() {
        return desc;
    }

    public int getState() {
        return state;
    }

    public boolean isStopped() {
        return state == 0;
    }

    public boolean isStarting() {
        return state == 1;
    }

    public boolean isActive() {
        return state == 2;
    }

    public boolean isRunning() {
        return state == 2;
    }

    public boolean isStopping() {
        return state == 3;
    }

    public void setStopped() {
        state = 0;
        desc = "停止状态";
    }

    public void setStarting() {
        state = 1;
        desc = "正在启动";
    }

    public void setRunning() {
        state = 2;
        desc = "正常运行";
    }

    public void setStopping() {
        state = 3;
        desc = "正在停止";
    }
}
