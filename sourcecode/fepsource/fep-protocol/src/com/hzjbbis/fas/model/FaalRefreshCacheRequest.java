package com.hzjbbis.fas.model;

public class FaalRefreshCacheRequest extends FaalRequest {
    private static final long serialVersionUID = -5642795098417472194L;
    private String taskNum;

    public FaalRefreshCacheRequest() {
        this.type = 254;
    }

    public String getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }
}