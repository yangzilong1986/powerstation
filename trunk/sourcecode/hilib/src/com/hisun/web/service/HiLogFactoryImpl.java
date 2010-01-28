package com.hisun.web.service;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;

public class HiLogFactoryImpl implements HiLogFactory {
    private String logFile;
    private String logLevel;

    public HiLogFactoryImpl() {

        this.logFile = "mngmon.trc";

        this.logLevel = "DEBUG";
    }

    public Logger getLogger(String userid) {

        Logger log = HiLog.getTrcLogger(this.logFile, this.logLevel);

        return log;
    }

    public Logger getLogger() {

        return getLogger("");
    }

    public void setLogFile(String logFile) {

        this.logFile = logFile;
    }

    public void setLogLevel(String logLevel) {

        this.logLevel = logLevel;
    }
}