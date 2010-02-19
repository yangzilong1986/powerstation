package com.hzjbbis.fk.model;

public class RtuTask {
    private String rtuId;
    private String taskTemplateID;
    private String taskTemplateProperty;
    private int rtuTaskNum;
    private String tn;

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public int getRtuTaskNum() {
        return this.rtuTaskNum;
    }

    public void setRtuTaskNum(int rtuaTaskNum) {
        this.rtuTaskNum = rtuaTaskNum;
    }

    public String getTaskTemplateID() {
        return this.taskTemplateID;
    }

    public void setTaskTemplateID(String taskTemplateID) {
        this.taskTemplateID = taskTemplateID;
    }

    public String getTaskTemplateProperty() {
        return this.taskTemplateProperty;
    }

    public void setTaskTemplateProperty(String taskTemplateProperty) {
        this.taskTemplateProperty = taskTemplateProperty;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}