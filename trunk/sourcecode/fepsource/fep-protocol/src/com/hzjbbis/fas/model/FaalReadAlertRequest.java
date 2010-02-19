package com.hzjbbis.fas.model;

import java.util.Calendar;

public class FaalReadAlertRequest extends FaalRequest {
    private static final long serialVersionUID = -654318745767829688L;
    private String tn;
    private Calendar startTime;
    private int count;
    private boolean doUpdate;

    public FaalReadAlertRequest() {
        this.type = 9;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public Calendar getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isDoUpdate() {
        return this.doUpdate;
    }

    public void setDoUpdate(boolean doUpdate) {
        this.doUpdate = doUpdate;
    }
}