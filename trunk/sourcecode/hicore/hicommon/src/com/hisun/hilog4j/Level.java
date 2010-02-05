package com.hisun.hilog4j;

import org.apache.log4j.Priority;

import java.io.Serializable;

public class Level extends Priority implements Serializable {
    public static final int TRACE_INT = 5000;
    public static final int INFO_INT2 = 20002;
    public static final int INFO_INT3 = 20003;
    public static final Level OFF = new Level(2147483647, "OFF", 0);

    public static final Level FATAL = new Level(50000, "FATAL", 0);

    public static final Level ERROR = new Level(40000, "ERROR", 3);

    public static final Level WARN = new Level(30000, "WARN", 4);

    public static final Level INFO = new Level(20000, "INFO", 6);

    public static final Level INFO2 = new Level(20002, "INFO2", 6);

    public static final Level INFO3 = new Level(20003, "INFO3", 6);

    public static final Level DEBUG = new Level(10000, "DEBUG", 7);

    public static final Level TRACE = new Level(5000, "TRACE", 7);

    public static final Level ALL = new Level(-2147483648, "ALL", 7);
    static final long serialVersionUID = 3491141966387921974L;

    protected Level(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

    public static Level toLevel(String sArg) {
        return toLevel(sArg, DEBUG);
    }

    public static Level toLevel(int val) {
        return toLevel(val, DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        switch (val) {
            case -2147483648:
                return ALL;
            case 10000:
                return DEBUG;
            case 20000:
                return INFO;
            case 20002:
                return INFO2;
            case 20003:
                return INFO3;
            case 30000:
                return WARN;
            case 40000:
                return ERROR;
            case 50000:
                return FATAL;
            case 2147483647:
                return OFF;
            case 5000:
                return TRACE;
        }
        return defaultLevel;
    }

    public static Level toLevel(String sArg, Level defaultLevel) {
        if (sArg == null) {
            return defaultLevel;
        }
        String s = sArg.toUpperCase();

        if (s.equals("ALL")) return ALL;
        if (s.equals("DEBUG")) return DEBUG;
        if (s.equals("INFO")) return INFO;
        if (s.equals("INFO2")) return INFO2;
        if (s.equals("INFO3")) return INFO3;
        if (s.equals("WARN")) return WARN;
        if (s.equals("ERROR")) return ERROR;
        if (s.equals("FATAL")) return FATAL;
        if (s.equals("OFF")) return OFF;
        if (s.equals("TRACE")) return TRACE;
        return defaultLevel;
    }
}