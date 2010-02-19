package com.hzjbbis.exception;

public class MessageDecodeException extends RuntimeException {
    private static final long serialVersionUID = 3337569199562775364L;

    public MessageDecodeException() {
    }

    public MessageDecodeException(String message) {
        super(message);
    }

    public MessageDecodeException(Throwable cause) {
        super(cause);
    }

    public MessageDecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}