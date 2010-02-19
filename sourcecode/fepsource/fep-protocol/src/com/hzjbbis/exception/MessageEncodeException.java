package com.hzjbbis.exception;

public class MessageEncodeException extends RuntimeException {
    private int errcode;
    private String code;
    private static final long serialVersionUID = -2488563605733447133L;

    public MessageEncodeException() {
    }

    public MessageEncodeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public MessageEncodeException(String message) {
        super(message);
    }

    public MessageEncodeException(Throwable cause) {
        super(cause);
    }

    public MessageEncodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrcode() {
        return this.errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}