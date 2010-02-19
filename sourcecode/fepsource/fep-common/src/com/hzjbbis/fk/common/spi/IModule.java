package com.hzjbbis.fk.common.spi;

public abstract interface IModule extends IModStatistics, IProfile {
    public static final String MODULE_TYPE_SOCKET_SERVER = "socketServer";
    public static final String MODULE_TYPE_SOCKET_CLIENT = "socketClient";
    public static final String MODULE_TYPE_MESSAGE_QUEUE = "messageQueue";
    public static final String MODULE_TYPE_EVENT_HOOK = "eventHook";
    public static final String MODULE_TYPE_GPRS_CLIENT = "gprsClient";
    public static final String MODULE_TYPE_UMS_CLIENT = "umsClient";
    public static final String MODULE_TYPE_DB_SERVICE = "dbService";
    public static final String MODULE_TYPE_BP = "businessProcessor";
    public static final String MODULE_TYPE_CONTAINER = "moduleContainer";

    public abstract String getModuleType();

    public abstract String getName();

    public abstract boolean start();

    public abstract void stop();

    public abstract boolean isActive();

    public abstract String getTxfs();
}