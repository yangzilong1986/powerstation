package com.hzjbbis.fas.model;

import java.util.Calendar;

public class FaalReadProgramLogRequest extends FaalRequest {
    private static final long serialVersionUID = -2603907087782103968L;
    private String tn;
    private Calendar startTime;
    private int count;

    public FaalReadProgramLogRequest() {
        this.type = 4;
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
}