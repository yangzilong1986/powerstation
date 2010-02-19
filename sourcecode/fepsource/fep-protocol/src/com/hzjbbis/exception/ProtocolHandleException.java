package com.hzjbbis.exception;

public class ProtocolHandleException extends RuntimeException {
    private static final long serialVersionUID = -5056449095935802236L;

    public ProtocolHandleException() {
    }

    public ProtocolHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolHandleException(String message) {
        super(message);
    }

    public ProtocolHandleException(Throwable cause) {
        super(cause);
    }
}