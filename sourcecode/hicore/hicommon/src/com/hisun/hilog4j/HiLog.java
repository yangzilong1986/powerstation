package com.hisun.hilog4j;

import com.hisun.exception.HiAppException;
import com.hisun.exception.HiException;
import com.hisun.exception.HiSysException;
import com.hisun.message.HiMessage;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class HiLog {
    private static HiArrayBlockingQueue dyncLoggerCache = new HiArrayBlockingQueue(200);

    private static HashMap loggerCache = new HashMap();

    public static Logger getLogger(HiMessage mess) {
        String filename = mess.getHeadItem("FID");
        if (StringUtils.isEmpty(filename)) {
            filename = mess.getRequestId();
        }
        String level = mess.getHeadItem("STF");
        return getDynTrcLogger(filename, level);
    }

    public static synchronized Logger getDynTrcLogger(String name, String level) {
        if (Logger.toLevel(level).toInt() >= 30000) {
            return Logger.dummyLogger;
        }

        Logger log = null;
        if (dyncLoggerCache.containsKey(name)) {
            log = (Logger) dyncLoggerCache.get(name);
        } else {
            log = new Logger(new HiDynTrcFileName(name), level);
            dyncLoggerCache.put(name, log);
        }
        Level level1 = Logger.toLevel(level);
        if (level1.toInt() < log.level.toInt()) {
            log.setLevel(level1);
        }
        return log;
    }

    public static synchronized void clear(HiMessage mess) {
    }

    public static synchronized void close(HiMessage mess) {
    }

    public static Logger getLogger(String filename) {
        return getTrcLogger(filename);
    }

    public static synchronized Logger getErrorLogger(String filename) {
        Logger log = null;
        if (loggerCache.containsKey(filename)) {
            log = (Logger) loggerCache.get(filename);
        } else {
            log = new Logger(new HiLogFileName(filename));
            loggerCache.put(filename, log);
        }
        return log;
    }

    public static synchronized Logger getErrorLogger(String filename, String level) {
        Logger log = null;
        if (loggerCache.containsKey(filename)) {
            log = (Logger) loggerCache.get(filename);
        } else {
            log = new Logger(new HiLogFileName(filename), level);
            loggerCache.put(filename, log);
        }
        return log;
    }

    public static synchronized Logger getTrcLogger(String filename) {
        Logger log = null;
        if (loggerCache.containsKey(filename)) {
            log = (Logger) loggerCache.get(filename);
        } else {
            log = new Logger(new HiTrcFileName(filename));
            loggerCache.put(filename, log);
        }
        return log;
    }

    public static synchronized Logger getTrcLogger(String filename, String level) {
        Logger log = null;
        if (loggerCache.containsKey(filename)) {
            log = (Logger) loggerCache.get(filename);
        } else {
            log = new Logger(new HiTrcFileName(filename), level);
            loggerCache.put(filename, log);
        }
        return log;
    }

    public static void logServiceError(HiMessage mess, HiException e) {
        Logger tranlog = getLogger(mess);
        String AppId = mess.getHeadItem("APP");
        if (AppId == null) {
            AppId = "APP";
        }

        Logger applog = getErrorLogger(AppId + ".log");
        if (e instanceof HiSysException) {
            tranlog.fatal(e.getAppMessage());

            if (e.isLog()) {
                applog.fatal(mess.getRequestId() + ":" + e.getAppMessage());
                applog.fatal(mess.getRequestId() + "DUMP MESSAGE:" + mess);
            } else {
                applog.fatal(mess.getRequestId() + ":" + e.getAppStackMessage());

                applog.fatal(mess.getRequestId() + "DUMP MESSAGE:" + mess);
                applog.fatal(mess.getRequestId(), e);
                e.setLog(true);
            }
        } else if (e instanceof HiAppException) {
            tranlog.error(e.getAppMessage());

            if (e.isLog()) {
                applog.error(mess.getRequestId() + ":" + e.getAppMessage());
                applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
            } else {
                applog.error(mess.getRequestId() + ":" + e.getAppStackMessage());

                applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
                applog.error(mess.getRequestId(), e);
                e.setLog(true);
            }
        } else {
            tranlog.error(e.getAppMessage());

            if (e.isLog()) {
                applog.error(mess.getRequestId() + ":" + e.getAppMessage());
                applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
            } else {
                applog.error(mess.getRequestId() + ":" + e.getAppStackMessage());

                applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
                applog.error(mess.getRequestId(), e);
                e.setLog(true);
            }
        }
    }

    public static void logServiceWarn(HiMessage mess, HiException e) {
        Logger tranlog = getLogger(mess);
        String AppId = mess.getHeadItem("APP");
        if (AppId == null) AppId = "APP";
        Logger applog = getErrorLogger(AppId + ".log");

        if (e instanceof HiSysException) {
            tranlog.warn(e.getAppMessage());

            if (e.isLog()) {
                applog.warn(mess.getRequestId() + ":" + e.getAppMessage());
            } else {
                applog.warn(mess.getRequestId() + ":" + e.getAppStackMessage());
                applog.warn(mess.getRequestId(), e);
                e.setLog(true);
            }
        } else if (e instanceof HiAppException) {
            tranlog.warn(e.getAppMessage());

            if (e.isLog()) {
                applog.warn(mess.getRequestId() + ":" + e.getAppMessage());
            } else {
                applog.warn(mess.getRequestId() + ":" + e.getAppStackMessage());
                applog.warn(mess.getRequestId(), e);
                e.setLog(true);
            }
        } else {
            tranlog.warn(e.getAppMessage());

            if (e.isLog()) {
                applog.warn(mess.getRequestId() + ":" + e.getAppMessage());
            } else {
                applog.warn(mess.getRequestId() + ":" + e.getAppStackMessage());
                applog.warn(mess.getRequestId(), e);
                e.setLog(true);
            }
        }
    }

    public static void logServerError(String ServerName, HiMessage mess, HiException e) {
        String level = "ERROR";
        String requestId = "";
        String seperator = "";
        if (mess != null) {
            level = mess.getHeadItem("STF");
            if (StringUtils.isEmpty(level)) {
                level = "ERROR";
            }
            requestId = mess.getRequestId();
            seperator = ":";
        }
        if (!(StringUtils.isEmpty(requestId))) {
            Logger log = getLogger(mess);
            log.error(e.getAppMessage());
        }

        Logger serverlog = getErrorLogger(ServerName + ".log");

        if (e instanceof HiSysException) {
            serverlog.fatal(requestId + seperator + e.getAppMessage());

            if (e.isLog()) {
                serverlog.fatal(requestId + seperator + e.getAppMessage());
            } else {
                serverlog.fatal(requestId + seperator + e.getAppStackMessage());
                serverlog.fatal(requestId + "DUMP MESSAGE:" + mess);
                serverlog.fatal(requestId, e);
                e.setLog(true);
            }

        } else if (e.isLog()) {
            serverlog.error(requestId + seperator + e.getAppMessage());
        } else {
            serverlog.error(requestId + seperator + e.getAppStackMessage());
            serverlog.fatal(requestId + "DUMP MESSAGE:" + mess);
            serverlog.error(requestId + seperator, e);
            e.setLog(true);
        }
    }

    public static void logSysError(String msg, Throwable t) {
        Logger log = getErrorLogger("SYS.log");
        if (t instanceof HiException) {
            HiException e = (HiException) t;
            if (e.isLog()) {
                log.error(msg);
                log.error(e.getAppMessage());
            } else {
                log.error(e.getAppStackMessage());
                log.error(e, e);
            }
        } else {
            log.error(msg, t);
        }
    }
}