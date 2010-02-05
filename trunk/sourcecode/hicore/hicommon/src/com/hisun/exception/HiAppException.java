package com.hisun.exception;

public class HiAppException extends HiException {
    private int retCode = -1;

    public HiAppException(int retCode, String code) {
        super(code, "");
        this.retCode = retCode;
    }

    public HiAppException(int retCode, String code, String msg) {
        super(code, msg);
        this.retCode = retCode;
    }

    public HiAppException(int retCode, String code, String arg0, String arg1) {
        super(code, arg0, arg1);
        this.retCode = retCode;
    }

    public HiAppException(int retCode, String code, String[] args) {
        super(code, args);

        this.retCode = retCode;
    }

    public HiAppException(int retCode, String code, String msg, Throwable nestedException) {
        super(code, msg, nestedException);
        this.retCode = retCode;
    }

    public HiAppException(int retCode, String code, String[] msg, Throwable nestedException) {
        super(code, msg, nestedException);
        this.retCode = retCode;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String toString() {
        return "retCode[" + this.retCode + "] \n" + super.toString();
    }
}