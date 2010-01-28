package com.hisun.hilog4j;


import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessageContext;
import com.hisun.util.HiConvHelper;
import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger implements HiCloseable {
    public static int DEFAULT_LIMIT_SIZE = 20971520;
    public static int DEFAULT_LIMIT_LINES = 20;
    public static int DEFAULT_QUEUE_MAX_SIZE = 1000;
    public static final String SYS_LOG = "SYS.log";
    public static Logger dummyLogger = new Logger(new HiLogFileName("DUMMY.log"), Level.ERROR);

    private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss,SSS");

    protected int limitsLines = DEFAULT_LIMIT_LINES;
    protected int limitsSize = DEFAULT_LIMIT_SIZE;
    protected Level level = Level.DEBUG;
    protected IFileName fileName = null;
    protected boolean hasOfHead = true;
    protected ILogCache logCache;
    protected String msgId;


    public static Logger getLogger(String name) {

        return new Logger(new HiTrcFileName(name), Level.DEBUG);

    }


    public Logger(String name) {

        construct(new HiTrcFileName(name));

    }


    public Logger(String name, String level) {

        construct(new HiTrcFileName(name), toLevel(level));

    }


    public Logger(String name, Level level) {

        construct(new HiTrcFileName(name), level);

    }


    public Logger(IFileName name) {

        construct(name);

    }


    public Logger(IFileName name, String level) {

        construct(name, toLevel(level));

    }


    public Logger(IFileName name, Level level) {

        construct(name, level);

    }


    protected void construct(IFileName name) {

        String tmp1 = HiICSProperty.getProperty("log.level", "DEBUG");

        construct(name, toLevel(tmp1));

    }


    protected void construct(IFileName name, Level level) {

        String tmp2 = HiICSProperty.getProperty("log.limits_lines", "20");

        this.limitsLines = NumberUtils.toInt(tmp2);

        tmp2 = HiICSProperty.getProperty("log.limits_size", "20");

        this.limitsSize = (NumberUtils.toInt(tmp2) * 1024 * 1024);

        if (this.limitsSize <= 0) {

            this.limitsSize = DEFAULT_LIMIT_SIZE;

        }

        this.level = level;

        this.fileName = name;

        this.logCache = new HiDirectLogCache(this.limitsSize);

    }


    public void setLevel(org.apache.log4j.Level level) {

        this.level = Level.toLevel(level.toInt());

    }


    public void setLevel(Level level) {

        this.level = level;

    }


    public Level getLevel() {

        return this.level;

    }


    public static Logger getLogger(Class clazz) {

        String name = clazz.getName();

        int idx1 = name.lastIndexOf(46);

        if (idx1 != -1) {

            name = name.substring(idx1 + 1);

        }

        return getLogger(name);

    }


    public boolean isDebugEnabled() {

        return (this.level.toInt() > 10000);

    }


    public void debug(Object msg) {

        debug(new Object[]{msg});

    }


    public void debug(Object msg1, Object msg2) {

        debug(new Object[]{msg1, msg2});

    }


    public void debug(Object msg1, Object msg2, Object msg3) {

        debug(new Object[]{msg1, msg2, msg3});

    }


    public void debug(Object msg1, Object msg2, Object msg3, Object msg4) {

        debug(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void debug(Object msg, Throwable t) {

        debug(new Object[]{msg}, t);

    }


    public void debug(Object msg1, Object msg2, Throwable t) {

        debug(new Object[]{msg1, msg2}, t);

    }


    public void debug(Object msg1, Object msg2, Object msg3, Throwable t) {

        debug(new Object[]{msg1, msg2, msg3}, t);

    }


    public void debug(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        debug(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void debug(Object[] msgs) {

        if (isDebugEnabled()) log(Level.DEBUG, msgs);

    }


    public void debug(Object[] msgs, Throwable t) {

        if (isDebugEnabled()) log(Level.DEBUG, msgs, t);

    }


    public boolean isInfoEnabled() {

        return (this.level.toInt() > 20000);

    }


    public void info(Object msg) {

        info(new Object[]{msg});

    }


    public void info(Object msg1, Object msg2) {

        info(new Object[]{msg1, msg2});

    }


    public void info(Object msg1, Object msg2, Object msg3) {

        info(new Object[]{msg1, msg2, msg3});

    }


    public void info(Object msg1, Object msg2, Object msg3, Object msg4) {

        info(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void info(Object msg, Throwable t) {

        info(new Object[]{msg}, t);

    }


    public void info(Object msg1, Object msg2, Throwable t) {

        info(new Object[]{msg1, msg2}, t);

    }


    public void info(Object msg1, Object msg2, Object msg3, Throwable t) {

        info(new Object[]{msg1, msg2, msg3}, t);

    }


    public void info(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        info(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void info(Object[] msgs, Throwable t) {

        if (isInfoEnabled()) log(Level.INFO, msgs, t);

    }


    public void info(Object[] msgs) {

        if (isInfoEnabled()) log(Level.INFO, msgs);

    }


    public boolean isInfo2Enabled() {

        return (this.level.toInt() > 20002);

    }


    public void info2(Object msg) {

        info2(new Object[]{msg});

    }


    public void info2(Object msg1, Object msg2) {

        info2(new Object[]{msg1, msg2});

    }


    public void info2(Object msg1, Object msg2, Object msg3) {

        info2(new Object[]{msg1, msg2, msg3});

    }


    public void info2(Object msg1, Object msg2, Object msg3, Object msg4) {

        info2(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void info2(Object msg, Throwable t) {

        info2(new Object[]{msg}, t);

    }


    public void info2(Object msg1, Object msg2, Throwable t) {

        info2(new Object[]{msg1, msg2}, t);

    }


    public void info2(Object msg1, Object msg2, Object msg3, Throwable t) {

        info2(new Object[]{msg1, msg2, msg3}, t);

    }


    public void info2(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        info2(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void info2(Object[] msgs, Throwable t) {

        if (isInfo2Enabled()) log(Level.INFO2, msgs, t);

    }


    public void info2(Object[] msgs) {

        if (isInfo2Enabled()) log(Level.INFO2, msgs);

    }


    public boolean isInfo3Enabled() {

        return (this.level.toInt() > 20003);

    }


    public void info3(Object msg) {

        info3(new Object[]{msg});

    }


    public void info3(Object msg1, Object msg2) {

        info3(new Object[]{msg1, msg2});

    }


    public void info3(Object msg1, Object msg2, Object msg3) {

        info3(new Object[]{msg1, msg2, msg3});

    }


    public void info3(Object msg1, Object msg2, Object msg3, Object msg4) {

        info3(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void info3(Object msg, Throwable t) {

        info3(new Object[]{msg}, t);

    }


    public void info3(Object msg1, Object msg2, Throwable t) {

        info3(new Object[]{msg1, msg2}, t);

    }


    public void info3(Object msg1, Object msg2, Object msg3, Throwable t) {

        info3(new Object[]{msg1, msg2, msg3}, t);

    }


    public void info3(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        info3(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void info3(Object[] msgs, Throwable t) {

        if (isInfo3Enabled()) log(Level.INFO3, msgs, t);

    }


    public void info3(Object[] msgs) {

        if (isInfo3Enabled()) log(Level.INFO3, msgs);

    }


    public boolean isWarnEnabled() {

        return (this.level.toInt() > 30000);

    }


    public void warn(Object msg) {

        warn(new Object[]{msg});

    }


    public void warn(Object msg1, Object msg2) {

        warn(new Object[]{msg1, msg2});

    }


    public void warn(Object msg1, Object msg2, Object msg3) {

        warn(new Object[]{msg1, msg2, msg3});

    }


    public void warn(Object msg1, Object msg2, Object msg3, Object msg4) {

        warn(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void warn(Object msg, Throwable t) {

        warn(new Object[]{msg}, t);

    }


    public void warn(Object msg1, Object msg2, Throwable t) {

        warn(new Object[]{msg1, msg2}, t);

    }


    public void warn(Object msg1, Object msg2, Object msg3, Throwable t) {

        warn(new Object[]{msg1, msg2, msg3}, t);

    }


    public void warn(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        warn(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void warn(Object[] msgs) {

        if (isWarnEnabled()) log(Level.WARN, msgs);

    }


    public void warn(Object[] msgs, Throwable t) {

        if (isWarnEnabled()) log(Level.WARN, msgs, t);

    }


    public boolean isErrorEnabled() {

        return (this.level.toInt() > 40000);

    }


    public void error(Object msg) {

        error(new Object[]{msg});

    }


    public void error(Object msg1, Object msg2) {

        error(new Object[]{msg1, msg2});

    }


    public void error(Object msg1, Object msg2, Object msg3) {

        error(new Object[]{msg1, msg2, msg3});

    }


    public void error(Object msg1, Object msg2, Object msg3, Object msg4) {

        error(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void error(Object msg, Throwable t) {

        error(new Object[]{msg}, t);

    }


    public void error(Object msg1, Object msg2, Throwable t) {

        error(new Object[]{msg1, msg2}, t);

    }


    public void error(Object msg1, Object msg2, Object msg3, Throwable t) {

        error(new Object[]{msg1, msg2, msg3}, t);

    }


    public void error(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        error(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void error(Object[] msgs) {

        log(Level.ERROR, msgs);

    }


    public void error(Object[] msgs, Throwable t) {

        log(Level.ERROR, msgs, t);

    }


    public boolean isFatalEnabled() {

        return (this.level.toInt() > 50000);

    }


    public void fatal(Object msg) {

        fatal(new Object[]{msg});

    }


    public void fatal(Object msg1, Object msg2) {

        fatal(new Object[]{msg1, msg2});

    }


    public void fatal(Object msg1, Object msg2, Object msg3) {

        fatal(new Object[]{msg1, msg2, msg3});

    }


    public void fatal(Object msg1, Object msg2, Object msg3, Object msg4) {

        fatal(new Object[]{msg1, msg2, msg3, msg4});

    }


    public void fatal(Object msg, Throwable t) {

        fatal(new Object[]{msg}, t);

    }


    public void fatal(Object msg1, Object msg2, Throwable t) {

        fatal(new Object[]{msg1, msg2}, t);

    }


    public void fatal(Object msg1, Object msg2, Object msg3, Throwable t) {

        fatal(new Object[]{msg1, msg2, msg3}, t);

    }


    public void fatal(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t) {

        fatal(new Object[]{msg1, msg2, msg3, msg4}, t);

    }


    public void fatal(Object[] msgs) {

        log(Level.FATAL, msgs);

        log(new HiLogFileName("SYS.log"), Level.FATAL, msgs);

    }


    public void fatal(Object[] msgs, Throwable t) {

        log(Level.FATAL, msgs, t);

        log(new HiLogFileName("SYS.log"), Level.FATAL, msgs, t);

    }


    protected void log(Level level, Object[] msgs, Throwable t) {

        log(this.fileName, level, msgs, t);

    }


    protected void log(IFileName name, Level level, Object[] msgs, Throwable t) {

        Throwable t1;

        StringBuilder buffer = new StringBuilder(81);

        println(name, buffer, level, msgs);

        buffer.append(t.toString());

        buffer.append(SystemUtils.LINE_SEPARATOR);


        StackTraceElement[] elements = t.getStackTrace();

        for (int i = 0; (elements != null) && (i < elements.length); ++i) {

            if (this.msgId != null) {

                buffer.append(this.msgId);

                buffer.append(' ');

            }

            buffer.append("        at ");

            buffer.append(elements[i].toString());

            buffer.append(SystemUtils.LINE_SEPARATOR);

            if ((i == this.limitsLines) && (i < elements.length - 1)) {

                if (this.msgId != null) {

                    buffer.append(this.msgId);

                    buffer.append(' ');

                }

                buffer.append("        Truncated...........");

                buffer.append(SystemUtils.LINE_SEPARATOR);

                break;

            }


        }


        if (t instanceof HiException) t1 = ((HiException) t).getNestedException();

        else {

            t1 = t.getCause();

        }

        while (t1 != null) {

            buffer.append("Nested Exception:");

            buffer.append(SystemUtils.LINE_SEPARATOR);

            buffer.append(t1.toString());

            buffer.append(SystemUtils.LINE_SEPARATOR);


            elements = t1.getStackTrace();

            for (int i = 0; (elements != null) && (i < elements.length); ++i) {

                if (this.msgId != null) {

                    buffer.append(this.msgId);

                    buffer.append(' ');

                }

                buffer.append("           ");

                buffer.append(elements[i].toString());

                buffer.append(SystemUtils.LINE_SEPARATOR);

                if ((i == this.limitsLines) && (i < elements.length - 1)) {

                    if (this.msgId != null) {

                        buffer.append(this.msgId);

                        buffer.append(' ');

                    }

                    buffer.append("        Truncated..........");

                    buffer.append(SystemUtils.LINE_SEPARATOR);

                    break;

                }

            }

            if (t1 instanceof HiException) {

                t1 = ((HiException) t1).getNestedException();

            }

            t1 = t1.getCause();

        }


        try {

            this.logCache.put(new HiLogInfo(name, buffer));

        } catch (IOException e) {

            System.out.println(name.get());

            e.printStackTrace();

        }

    }


    protected void log(IFileName name, Level level, Object[] msgs) {

        StringBuilder buffer = new StringBuilder(81);

        println(name, buffer, level, msgs);

        try {

            this.logCache.put(new HiLogInfo(name, buffer));

        } catch (IOException e) {

            System.out.println(name.get());

            e.printStackTrace();

        }

    }


    protected void log(Level level, Object[] msgs) {

        log(this.fileName, level, msgs);

    }


    protected void println(IFileName name, StringBuilder buffer, Level level, Object[] msgs) {

        if (this.msgId != null) {

            String tmpMsgId = "";

            HiContext ctx = HiContext.getCurrentContext();

            if (ctx instanceof HiMessageContext) {

                tmpMsgId = ((HiMessageContext) ctx).getCurrentMsg().getRequestId();

                buffer.append(tmpMsgId);

                buffer.append(' ');

            }

        }


        if (this.hasOfHead) {

            buffer.append(df.format(new Date()));

            buffer.append(' ');

            buffer.append(level.toString());

            buffer.append(' ');

            buffer.append('-');

            buffer.append(' ');

        }


        for (int i = 0; i < msgs.length; ++i) {

            if (msgs[i] instanceof byte[]) buffer.append(HiConvHelper.binToAscStr((byte[]) (byte[]) msgs[i]));

            else {

                buffer.append(String.valueOf(msgs[i]));

            }

            if (i != msgs.length - 1) {

                buffer.append(':');

            }

        }


        if (name.getLineLength() != -1) {

            i = buffer.toString().getBytes().length;

            for (; i < name.getLineLength(); ++i) {

                buffer.append(' ');

            }

        }

        buffer.append(SystemUtils.LINE_SEPARATOR);

    }


    public static Level toLevel(String strLev) {

        Level level = null;

        if ((StringUtils.equals(strLev, "0")) || (StringUtils.equalsIgnoreCase(strLev, "no"))) {

            level = Level.ERROR;

        } else if ((StringUtils.equals(strLev, "1")) || (StringUtils.equalsIgnoreCase(strLev, "yes"))) {

            level = Level.INFO;

        } else if (StringUtils.equals(strLev, "2")) level = Level.INFO2;

        else if (StringUtils.equals(strLev, "3")) level = Level.INFO3;

        else if (StringUtils.equals(strLev, "4")) level = Level.WARN;

        else if (StringUtils.equals(strLev, "5")) level = Level.ERROR;

        else {

            level = Level.toLevel(strLev, Level.ERROR);

        }

        return level;

    }


    public void setHasOfHead(boolean hasOfHead) {

        this.hasOfHead = hasOfHead;

    }


    public void clear() {

        this.logCache.clear();

    }


    public void flush() {

        try {

            this.logCache.flush();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public void close() {

        try {

            this.logCache.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public void setFixSizeable(boolean fixsizeable) {

        this.fileName.setFixedSizeable(fixsizeable);

    }


    public void setLineLength(int lineLength) {

        this.fileName.setLineLength(lineLength);

    }


    public String getMsgId() {

        return this.msgId;

    }


    public void setMsgId(String msgId) {

        this.msgId = msgId;

    }

}