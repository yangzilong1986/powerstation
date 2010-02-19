package com.hzjbbis.fas.model;

import java.io.Serializable;
import java.util.Date;

public class FaalRereadTaskResponse implements Serializable {
    private static final long serialVersionUID = 2L;
    private String logicAddress;
    private String taskNum;
    private String taskTemplateID;
    private Date SJSJ;
    private int rereadTag;

    public String getLogicAddress() {
        return this.logicAddress;
    }

    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    public String getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public Date getSJSJ() {
        return this.SJSJ;
    }

    public void setSJSJ(Date sjsj) {
        this.SJSJ = sjsj;
    }

    public int getRereadTag() {
        return this.rereadTag;
    }

    public void setRereadTag(int rereadTag) {
        this.rereadTag = rereadTag;
    }

    public String getTaskTemplateID() {
        return this.taskTemplateID;
    }

    public void setTaskTemplateID(String taskTemplateID) {
        this.taskTemplateID = taskTemplateID;
    }
}