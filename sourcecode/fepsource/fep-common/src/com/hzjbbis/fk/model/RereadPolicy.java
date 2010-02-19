package com.hzjbbis.fk.model;

public class RereadPolicy {
    private Integer rereadPolicyID;
    private int rereadInterval;
    private int rereadRange;
    private int rereadStartTime;
    private int rereadAdvanceTime;
    private boolean rereadStartTag;

    public Integer getRereadPolicyID() {
        return this.rereadPolicyID;
    }

    public void setRereadPolicyID(Integer rereadPolicyID) {
        this.rereadPolicyID = rereadPolicyID;
    }

    public int getRereadInterval() {
        return this.rereadInterval;
    }

    public void setRereadInterval(int rereadInterval) {
        this.rereadInterval = rereadInterval;
    }

    public int getRereadRange() {
        return this.rereadRange;
    }

    public void setRereadRange(int rereadRange) {
        this.rereadRange = rereadRange;
    }

    public int getRereadStartTime() {
        return this.rereadStartTime;
    }

    public void setRereadStartTime(int rereadStartTime) {
        this.rereadStartTime = rereadStartTime;
    }

    public int getRereadAdvanceTime() {
        return this.rereadAdvanceTime;
    }

    public void setRereadAdvanceTime(int rereadAdvanceTime) {
        this.rereadAdvanceTime = rereadAdvanceTime;
    }

    public boolean isRereadStartTag() {
        return this.rereadStartTag;
    }

    public void setRereadStartTag(boolean rereadStartTag) {
        this.rereadStartTag = rereadStartTag;
    }
}