package com.hzjbbis.fas.model;

import java.io.Serializable;
import java.util.Date;

public class HostCommandResult implements Serializable {
    private static final long serialVersionUID = 278266439182006204L;
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_AMBIGUOUS = 1;
    public static final int STATUS_FAILED = 2;
    private Long commandId;
    private String tn;
    private String alertCode;
    private String code;
    private String value;
    private Date time;
    private Date programTime;
    private String channel;
    private int status;

    public Long getCommandId() {
        return this.commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getAlertCode() {
        return this.alertCode;
    }

    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getProgramTime() {
        return this.programTime;
    }

    public void setProgramTime(Date programTime) {
        this.programTime = programTime;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}