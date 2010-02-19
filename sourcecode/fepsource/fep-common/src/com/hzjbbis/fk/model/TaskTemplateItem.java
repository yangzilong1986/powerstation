package com.hzjbbis.fk.model;

public class TaskTemplateItem {
    private String taskTemplateID;
    private String code;

    public String toString() {
        return "[taskPlateID=" + this.taskTemplateID + ", code=" + this.code + "]";
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskTemplateID() {
        return this.taskTemplateID;
    }

    public void setTaskTemplateID(String taskTemplateID) {
        this.taskTemplateID = taskTemplateID;
    }
}