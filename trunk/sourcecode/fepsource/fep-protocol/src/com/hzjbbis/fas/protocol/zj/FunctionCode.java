package com.hzjbbis.fas.protocol.zj;

public abstract class FunctionCode {
    public static final int READ_FORWARD_DATA = 0;
    public static final int READ_CURRENT_DATA = 1;
    public static final int READ_TASK_DATA = 2;
    public static final int READ_PROGRAM_LOG = 4;
    public static final int REALTIME_WRITE_PARAMS = 7;
    public static final int WRITE_PARAMS = 8;
    public static final int READ_ALERT = 9;
    public static final int CONFIRM_ALERT = 10;
    public static final int SEND_SMS = 40;
    public static final int REFRESH_CACHE = 254;
    public static final int OTHER = 255;
}