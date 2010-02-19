package com.hzjbbis.fk.monitor.client;

public abstract interface IMonitorReplyListener {
    public abstract void onSystemProfile(String paramString);

    public abstract void onModuleProfile(String paramString);

    public abstract void onEventHookProfile(String paramString);

    public abstract void onMultiSysProfile(String paramString);

    public abstract void onListLog(String paramString);

    public abstract void onListConfig(String paramString);

    public abstract void onGetFile();

    public abstract void onPutFile();

    public abstract void onReplyOK();

    public abstract void onReplyFailed(String paramString);

    public abstract void onRtuMessageInd(String paramString);

    public abstract void onConnect();

    public abstract void onClose();
}