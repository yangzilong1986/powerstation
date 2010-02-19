package com.hzjbbis.fk.monitor;

public abstract interface MonitorCommand {
    public static final short CMD_INVALID = 0;
    public static final short CMD_LOG_LIST = 1;
    public static final short CMD_CONFIG_LIST = 2;
    public static final short CMD_GET_FILE = 3;
    public static final short CMD_PUT_FILE = 4;
    public static final short CMD_GATHER_PROFILE = 31;
    public static final short CMD_SYS_PROFILE = 16;
    public static final short CMD_MODULE_PROFILE = 17;
    public static final short CMD_EVENT_HOOK_PROFILE = 18;
    public static final short CMD_MODULE_START = 19;
    public static final short CMD_MODULE_STOP = 20;
    public static final short CMD_SYS_START = 21;
    public static final short CMD_SYS_STOP = 22;
    public static final short CMD_TRACE_RTU = 23;
    public static final short CMD_TRACE_ABORT = 24;
    public static final short CMD_TRACE_IND = 25;
}