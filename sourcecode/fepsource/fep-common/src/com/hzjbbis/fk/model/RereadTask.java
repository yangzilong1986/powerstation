package com.hzjbbis.fk.model;

import java.util.HashMap;
import java.util.Map;

public class RereadTask {
    private String rtuId;
    private String taskNum;
    private String taskInterval;
    private Integer rereadPolicyID;
    private Map<Long, RereadInfo> datemaps = new HashMap();

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public String getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getTaskInterval() {
        return this.taskInterval;
    }

    public void setTaskInterval(String taskInterval) {
        this.taskInterval = taskInterval;
    }

    public Integer getRereadPolicyID() {
        return this.rereadPolicyID;
    }

    public void setRereadPolicyID(Integer rereadPolicyID) {
        this.rereadPolicyID = rereadPolicyID;
    }

    public Map<Long, RereadInfo> getDatemaps() {
        return this.datemaps;
    }

    public void setDatemaps(Map<Long, RereadInfo> datemaps) {
        this.datemaps = datemaps;
    }
}