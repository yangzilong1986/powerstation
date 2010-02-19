package com.hzjbbis.exception;

public class CopyException extends RuntimeException {
    private static final long serialVersionUID = 200603141603L;

    public CopyException() {
    }

    public CopyException(Exception ex) {
        super(ex);
    }
}