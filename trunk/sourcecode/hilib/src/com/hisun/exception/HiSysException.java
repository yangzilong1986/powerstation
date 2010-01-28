package com.hisun.exception;

public class HiSysException extends HiException {
    public HiSysException() {
    }

    public HiSysException(String code) {

        super(code);
    }

    public HiSysException(String code, String msg) {

        super(code, msg);
    }

    public HiSysException(String code, String arg0, String arg1) {

        super(code, arg0, arg1);
    }

    public HiSysException(String code, String arg0, String arg1, String arg2) {

        super(code, arg0, arg1, arg2);
    }

    public HiSysException(String code, String arg0, String arg1, String arg2, String arg3) {

        super(code, arg0, arg1, arg2, arg3);
    }

    public HiSysException(String code, String[] args) {

        super(code, args);
    }

    public HiSysException(Throwable nestedException) {

        super(nestedException);
    }

    public HiSysException(String code, String msg, Throwable nestedException) {

        super(code, msg, nestedException);
    }

    public HiSysException(String code, String[] msg, Throwable nestedException) {

        super(code, msg, nestedException);
    }
}