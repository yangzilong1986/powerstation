package com.hzjbbis.fas.model;

public class RtuAlertArg {
    private Long alertId;
    private String code;
    private String value;
    private String correlValue;

    public Long getAlertId() {
        return this.alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
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

    public String getCorrelValue() {
        return this.correlValue;
    }

    public void setCorrelValue(String correlValue) {
        this.correlValue = correlValue;
    }
}