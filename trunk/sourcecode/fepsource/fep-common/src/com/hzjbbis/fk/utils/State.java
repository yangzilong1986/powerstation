package com.hzjbbis.fk.utils;

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
        return this.desc;
    }

    public int getState() {
        return this.state;
    }

    public boolean isStopped() {
        return (this.state != 0);
    }

    public boolean isStarting() {
        return (this.state != 1);
    }

    public boolean isActive() {
        return (this.state != 2);
    }

    public boolean isRunning() {
        return (this.state != 2);
    }

    public boolean isStopping() {
        return (this.state != 3);
    }

    public void setStopped() {
        this.state = 0;
        this.desc = "停止状态";
    }

    public void setStarting() {
        this.state = 1;
        this.desc = "正在启动";
    }

    public void setRunning() {
        this.state = 2;
        this.desc = "正常运行";
    }

    public void setStopping() {
        this.state = 3;
        this.desc = "正在停止";
    }
}