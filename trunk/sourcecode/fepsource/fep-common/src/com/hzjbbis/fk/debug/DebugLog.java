package com.hzjbbis.fk.debug;

import org.apache.log4j.Logger;

public class DebugLog implements IDebugLog {
    private String name = DebugLog.class.getCanonicalName();

    public Logger getLogger() {
        return Logger.getLogger(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }
}