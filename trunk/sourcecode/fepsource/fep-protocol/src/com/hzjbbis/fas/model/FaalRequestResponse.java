package com.hzjbbis.fas.model;

import java.io.Serializable;
import java.util.Map;

public class FaalRequestResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long cmdId;
    private String rtuId;
    private String rtuType;
    private String cmdStatus;
    private Map<String, String> params;

    public Long getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(Long cmdId) {
        this.cmdId = cmdId;
    }

    public String getCmdStatus() {
        return this.cmdStatus;
    }

    public void setCmdStatus(String cmdStatus) {
        this.cmdStatus = cmdStatus;
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getRtuType() {
        return this.rtuType;
    }

    public void setRtuType(String rtuType) {
        this.rtuType = rtuType;
    }
}