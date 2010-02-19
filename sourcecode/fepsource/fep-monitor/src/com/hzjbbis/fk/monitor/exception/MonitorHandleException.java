package com.hzjbbis.fk.monitor.exception;

public class MonitorHandleException extends RuntimeException {
    private static final long serialVersionUID = -1099085988107532584L;

    public MonitorHandleException(String info) {
        super(info);
    }

    public MonitorHandleException(Exception exp) {
        super(exp);
    }
}