package com.hzjbbis.fk.tracelog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TraceLog {
    private static final Map<String, TraceLog> logMap = new HashMap();
    private static final String PROP_MAX_FILE_SIZE = "traceLog.maxFileSize";
    private static final String PROP_FILE_COUNT = "traceLog.fileCount";
    private static final String PROP_TRACE_ENABLED = "traceLog.enabled";
    private static int MAX_FILE_SIZE = 50;
    private static int FILE_COUNT = 1;
    private static boolean TRACE_ENABLED = false;
    private static String rootPath;
    private static String defaultKey = "trace";

    private static PropFileMonitor monitor = null;
    private static String propFilePath;
    private static Properties g_props = null;
    private static long propFileLastModified = 0L;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean enabled = false;
    private String key = defaultKey;
    private String filePath;
    private PrintStream out;

    static {
        try {
            File f = new File("traceLog.properties");
            if (!(f.exists())) f = new File("bin" + File.separatorChar + "traceLog.properties");
            if (!(f.exists())) f = new File("config" + File.separatorChar + "traceLog.properties");
            if (f.exists()) {
                propFilePath = f.getCanonicalPath();
                propFileLastModified = f.lastModified();
                g_props = new Properties();
                g_props.load(new FileInputStream(f));
                String strMaxFileSize = g_props.getProperty("traceLog.maxFileSize");
                if (strMaxFileSize == null) strMaxFileSize = System.getProperty("traceLog.maxFileSize");
                if (strMaxFileSize != null) strMaxFileSize = strMaxFileSize.trim();
                if ((strMaxFileSize != null) && (strMaxFileSize.length() > 0)) {
                    if (strMaxFileSize.substring(strMaxFileSize.length() - 1).toUpperCase().equals("M"))
                        strMaxFileSize = strMaxFileSize.substring(0, strMaxFileSize.length() - 1);
                    MAX_FILE_SIZE = Integer.parseInt(strMaxFileSize);
                    if ((MAX_FILE_SIZE <= 0) || (MAX_FILE_SIZE > 50000)) MAX_FILE_SIZE = 50;
                }
                String strFileCount = g_props.getProperty("traceLog.fileCount");
                if (strFileCount == null) strFileCount = System.getProperty("traceLog.fileCount");
                if (strFileCount != null) strFileCount = strFileCount.trim();
                if ((strFileCount != null) && (strFileCount.length() > 0)) {
                    FILE_COUNT = Integer.parseInt(strFileCount);
                    if ((FILE_COUNT <= 0) && (FILE_COUNT > 1000)) FILE_COUNT = 1;
                }
                String strEnabled = g_props.getProperty("traceLog.enabled");
                if (strEnabled == null) strEnabled = System.getProperty("traceLog.enabled");
                if ((strEnabled != null) && (strEnabled.length() > 0)) {
                    try {
                        TRACE_ENABLED = Boolean.parseBoolean(strEnabled);
                    } catch (Exception e1) {
                        try {
                            int iEnabled = Integer.parseInt(strEnabled);
                            if (iEnabled != 0) {
                                TRACE_ENABLED = true;
                            }
                            TRACE_ENABLED = false;
                        } catch (Exception localException1) {
                        }
                    }
                }
                label415:
                monitor = new PropFileMonitor();
                monitor.start();
            }
        } catch (Exception localException2) {
        }
        try {
            File file = new File("trace");
            file.mkdirs();
            rootPath = file.getAbsolutePath() + File.separatorChar;
        } catch (Exception localException3) {
        }
    }

    public static TraceLog getTracer() {
        return getTracer(defaultKey);
    }

    public static TraceLog getTracer(String myKey) {
        if (myKey == null) myKey = defaultKey;
        TraceLog log = (TraceLog) logMap.get(myKey);
        if (log == null) {
            log = new TraceLog(myKey);
            logMap.put(log.key, log);
            if (g_props != null) {
                String suffix = ".traceLog.enabled";
                String propKey = null;
                Iterator iter2 = g_props.keySet().iterator();
                while (iter2.hasNext()) {
                    String pkey = (String) iter2.next();
                    int kindex = pkey.indexOf(suffix);
                    if (kindex > 0) {
                        String prefix = pkey.substring(0, kindex);
                        if ((prefix.equals(log.key)) || (log.key.indexOf(prefix) == 0)) {
                            propKey = pkey;
                            break;
                        }
                    }
                }
                if (propKey != null) {
                    String strEnabled = g_props.getProperty(propKey);
                    if ((strEnabled != null) && (strEnabled.length() > 0)) try {
                        log.enabled = Boolean.parseBoolean(strEnabled);
                    } catch (Exception localException) {
                    }
                } else {
                    log.enabled = TRACE_ENABLED;
                }
            }
        }
        return log;
    }

    public static TraceLog getTracer(Class<? extends Object> clz) {
        return getTracer(clz.getCanonicalName());
    }

    private TraceLog(String fName) {
        this.key = fName;
    }

    private synchronized void createPrintStream() {
        this.filePath = makeFilePath();
        try {
            this.out = new PrintStream(new BufferedOutputStream(new FileOutputStream(this.filePath, true)));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.err.println();
        }
    }

    private String makeFilePath() {
        String name = this.key;
        int index = name.lastIndexOf(File.separatorChar);
        if (index >= 0) name = name.substring(index + 1);
        index = name.lastIndexOf(".");
        if (index >= 0) {
            name = name.substring(index + 1);
        }

        String fpathMark = null;
        long lastModified = 0L;

        for (int i = 1; i <= FILE_COUNT; ++i) {
            String fpath = rootPath + name + "-" + i + ".log";
           File f = new File(fpath);
            int flen = (int) (f.length() >>> 20);
            if (flen < MAX_FILE_SIZE) {
                return fpath;
            }
            if ((0L == lastModified) || (f.lastModified() - lastModified < 0L)) {
                lastModified = f.lastModified();
                fpathMark = fpath;
            }
        }
        File f = new File(fpathMark);
        f.delete();
        return fpathMark;
    }

    public void trace(String info) {
        if (this.enabled) write2File(info);
    }

    public void trace(String info, Exception e) {
        if (!(this.enabled)) return;
        synchronized (this) {
            write2File(info);
            if ((this.out != null) && (e != null)) {
                e.printStackTrace(this.out);
                this.out.println();
            }
        }
    }

    public void trace(Object obj) {
        if (!(this.enabled)) return;
        write2File(obj.toString());
    }

    private void checkOutput() {
        if (this.out == null) createPrintStream();
        File f = new File(this.filePath);
        if ((int) (f.length() >>> 20) >= MAX_FILE_SIZE) createPrintStream();
    }

    private synchronized void write2File(String info) {
        checkOutput();
        if (this.out == null) return;
        StringBuilder sb = new StringBuilder(512);
        Calendar ca = Calendar.getInstance();

        sb.append(df.format(Long.valueOf(ca.getTime().getTime()))).append(" ");

        Thread t = Thread.currentThread();
        sb.append("[").append(t.getName()).append("] ");
        StackTraceElement[] ste = t.getStackTrace();

        if ((ste != null) && (ste.length > 4)) {
            sb.append(ste[3].getFileName()).append(":").append(ste[3].getLineNumber());
        }
        sb.append(" - ").append(info);
        this.out.println(sb.toString());
        this.out.flush();
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    static class PropFileMonitor extends Thread {
        public PropFileMonitor() {
            super("TraceLogCfgMonitor");
            setDaemon(true);
            start();
        }

        public void run() {
            try {
                File f;
                do {
                    Thread.sleep(2000L);
                    f = new File(TraceLog.propFilePath);
                } while (f.lastModified() - TraceLog.propFileLastModified <= 0L);
                TraceLog.propFileLastModified = f.lastModified();
                Properties props = new Properties();
                props.load(new FileInputStream(f));
                String strMaxFileSize = props.getProperty("traceLog.maxFileSize");
                if (strMaxFileSize != null) strMaxFileSize = strMaxFileSize.trim();
                if ((strMaxFileSize != null) && (strMaxFileSize.length() > 0)) {
                    if (strMaxFileSize.substring(strMaxFileSize.length() - 1).toUpperCase().equals("M"))
                        strMaxFileSize = strMaxFileSize.substring(0, strMaxFileSize.length() - 1);
                    TraceLog.MAX_FILE_SIZE = Integer.parseInt(strMaxFileSize);
                    if ((TraceLog.MAX_FILE_SIZE <= 0) || (TraceLog.MAX_FILE_SIZE > 50000)) TraceLog.MAX_FILE_SIZE = 50;
                }
                String strFileCount = props.getProperty("traceLog.fileCount");
                if (strFileCount != null) strFileCount = strFileCount.trim();
                if ((strFileCount != null) && (strFileCount.length() > 0)) {
                    TraceLog.FILE_COUNT = Integer.parseInt(strFileCount);
                    if ((TraceLog.FILE_COUNT <= 0) && (TraceLog.FILE_COUNT > 1000)) TraceLog.FILE_COUNT = 1;
                }
                String strEnabled = props.getProperty("traceLog.enabled");
                if ((strEnabled != null) && (strEnabled.length() > 0)) try {
                    TraceLog.TRACE_ENABLED = Boolean.parseBoolean(strEnabled);
                } catch (Exception e1) {
                    try {
                        int iEnabled = Integer.parseInt(strEnabled);
                        if (iEnabled != 0) {
                            TraceLog.TRACE_ENABLED = true;

                        }
                        TraceLog.TRACE_ENABLED = false;
                    } catch (Exception localException1) {
                    }
                }
                Iterator iter = TraceLog.logMap.entrySet().iterator();
                String suffix = ".traceLog.enabled";
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();

                    String propKey = null;
                    Iterator iter2 = props.keySet().iterator();
                    while (iter2.hasNext()) {
                        String pkey = (String) iter2.next();
                        int kindex = pkey.indexOf(suffix);
                        if (kindex > 0) {
                            String prefix = pkey.substring(0, kindex);
                            if ((prefix.equals(((TraceLog) entry.getValue()).key)) || (((TraceLog) entry.getValue()).key.indexOf(prefix) == 0)) {
                                propKey = pkey;
                                break;
                            }
                        }
                    }
                    if (propKey != null) {
                        strEnabled = props.getProperty(propKey);
                        if ((strEnabled != null) && (strEnabled.length() > 0)) try {
                            ((TraceLog) entry.getValue()).enabled = Boolean.parseBoolean(strEnabled);
                        } catch (Exception localException2) {
                        }
                    } else {
                        ((TraceLog) entry.getValue()).enabled = TraceLog.TRACE_ENABLED;
                    }
                    if (!(((TraceLog) entry.getValue()).enabled)) {
                        synchronized ((TraceLog) entry.getValue()) {
                            if (((TraceLog) entry.getValue()).out != null) ((TraceLog) entry.getValue()).out.close();
                            ((TraceLog) entry.getValue()).out = null;
                        }
                    }
                }
                TraceLog.g_props = props;
            } catch (Exception localException3) {
            }
        }
    }
}