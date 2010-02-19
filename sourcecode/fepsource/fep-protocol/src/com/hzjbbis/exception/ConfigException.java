package com.hzjbbis.exception;

public class ConfigException extends RuntimeException {
    private static final long serialVersionUID = -8071053409130303967L;

    public ConfigException() {
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }
}