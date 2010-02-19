package com.hzjbbis.fas.model;

import java.util.Date;

public class FaalReadTaskDataRequest extends FaalRequest {
    private static final long serialVersionUID = -4362982183058685616L;
    private String taskNum;
    private Date startTime;
    private int count;
    private int frequence;
    private boolean doUpdate;

    public FaalReadTaskDataRequest() {
        this.type = 2;
    }

    public String getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFrequence() {
        return this.frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    public boolean isDoUpdate() {
        return this.doUpdate;
    }

    public void setDoUpdate(boolean doUpdate) {
        this.doUpdate = doUpdate;
    }
}