package com.hzjbbis.exception;

public class CastorException extends RuntimeException {
    private static final long serialVersionUID = -4800973432327233301L;

    public CastorException() {
    }

    public CastorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CastorException(String message) {
        super(message);
    }

    public CastorException(Throwable cause) {
        super(cause);
    }
}