package com.hzjbbis.fk.common.logfile;

import org.apache.log4j.Logger;

public class LogProfile {
    private static final Logger log = Logger.getLogger(LogProfile.class);

    public static final Logger getLog() {
        return log;
    }
}