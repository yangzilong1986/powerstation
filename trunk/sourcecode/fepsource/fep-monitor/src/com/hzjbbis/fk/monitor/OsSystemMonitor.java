package com.hzjbbis.fk.monitor;

import com.hzjbbis.fk.FasSystem;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class OsSystemMonitor {
    private static final Logger log = Logger.getLogger(OsSystemMonitor.class);

    private long lastUsed = 0L;
    private long lastTotal = 0L;

    private int sampleCount = 600;
    private final Object lock = new Object();
    private long maxMemory = 0L;
    private boolean autoMonitor = true;
    private Vector<MonitorDataItem> queue = new Vector();

    private boolean isWindows = false;

    private Timer timer = null;

    private static final OsSystemMonitor cpuMonitor = new OsSystemMonitor();

    private OsSystemMonitor() {
        this.maxMemory = (Runtime.getRuntime().maxMemory() >>> 20);
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") > -1) this.isWindows = true;
    }

    public static final OsSystemMonitor getInstance() {
        return cpuMonitor;
    }

    public MonitorDataItem[] getAllItems() {
        return ((MonitorDataItem[]) this.queue.toArray(new MonitorDataItem[this.queue.size()]));
    }

    public MonitorDataItem getCurrentData() {
        MonitorDataItem item = new MonitorDataItem();
        item.cpuUsage = getCPUUsage();
        item.totalMemory = (Runtime.getRuntime().totalMemory() >>> 20);
        item.freeMemory = (Runtime.getRuntime().freeMemory() >>> 20);
        item.freeDisk = getFreeDisk();
        return item;
    }

    public void initialize() {
        if (this.autoMonitor) {
            this.timer = new Timer(true);
            this.timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    synchronized (OsSystemMonitor.this.lock) {
                        MonitorDataItem item = null;
                        if (OsSystemMonitor.this.queue.size() < OsSystemMonitor.this.sampleCount) {
                            item = new MonitorDataItem();
                        } else {
                            item = (MonitorDataItem) OsSystemMonitor.this.queue.remove(0);
                        }
                        OsSystemMonitor.this.queue.add(item);
                        item.cpuUsage = OsSystemMonitor.this.getCPUUsage();
                        item.totalMemory = (Runtime.getRuntime().totalMemory() >>> 20);
                        item.freeMemory = (Runtime.getRuntime().freeMemory() >>> 20);
                        item.freeDisk = OsSystemMonitor.this.getFreeDisk();
                        OsSystemMonitor.this.updateOsProfile(item);
                        if (OsSystemMonitor.log.isDebugEnabled()) OsSystemMonitor.log.debug(item);
                    }
                }
            }, 1000L, 60000L);
        }
    }

    private void updateOsProfile(MonitorDataItem item) {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("<os-profile>\r\n");
        sb.append("      <cpu>").append(item.cpuUsage).append("</cpu>\r\n");
        sb.append("      <totalMemory>").append(item.totalMemory).append("</totalMemory>\r\n");
        sb.append("      <freeMemory>").append(item.freeMemory).append("</freeMemory>\r\n");
        sb.append("      <freeDisk>").append(item.freeDisk).append("</freeDisk>\r\n");
        sb.append("    </os-profile>\r\n");
        FasSystem.getFasSystem().setOsProfile(sb.toString());
    }

    private String[] execute(String[] commands) {
        String[] strs = (String[]) null;
        File scriptFile = null;
        try {
            List cmdList = new ArrayList();
            if (this.isWindows) {
                scriptFile = new File("monitor.vbs");
                cmdList.add("CMD.EXE");
                cmdList.add("/C");
                cmdList.add("CSCRIPT.EXE");
                cmdList.add("//NoLogo");
            } else {
                scriptFile = new File("monitor.sh");
                cmdList.add("/bin/bash");
            }
            if ((!(scriptFile.exists())) || (scriptFile.length() == 0L)) {
                PrintWriter writer = new PrintWriter(scriptFile);
                for (int i = 0; i < commands.length; ++i) {
                    writer.println(commands[i]);
                }
                writer.flush();
                writer.close();
            }
            String fileName = scriptFile.getCanonicalPath();
            cmdList.add(fileName);

            ProcessBuilder pb = new ProcessBuilder(cmdList);
            Process p = pb.start();
            p.waitFor();

            String line = null;
            BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            List stdoutList = new ArrayList();
            while ((line = stdout.readLine()) != null) {
                stdoutList.add(line);
            }
            if (log.isDebugEnabled()) {
                log.debug("CPUMonitor stdout:" + stdoutList);
            }

            BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            List stderrList = new ArrayList();
            while ((line = stderr.readLine()) != null) {
                stderrList.add(line);
            }
            if (stderrList.size() > 0) {
                log.warn("CPUMonitor stderr=" + stderrList);
            }
            strs = (String[]) stdoutList.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strs;
    }

    private double parseResult(String[] strs) {
        String strValue;
        double value = 0.0D;
        if (this.isWindows) {
            strValue = strs[0];
            try {
                value = Double.parseDouble(strValue);
            } catch (Exception e) {
                log.debug("parse double error.", e);
            }
        } else {
            strValue = strs[0];
            String[] values = strValue.split(" ");
            Vector vv = new Vector(10);
            for (String v : values) {
                if (v.length() != 0) vv.add(v);
            }
            values = (String[]) vv.toArray(new String[vv.size()]);
            if (values.length == 2) {
                long used = Long.parseLong(values[0]);
                long total = Long.parseLong(values[1]);

                if ((this.lastUsed > 0L) && (this.lastTotal > 0L)) {
                    long deltaUsed = used - this.lastUsed;
                    long deltaTotal = total - this.lastTotal;
                    if (deltaTotal > 0L) {
                        value = deltaUsed * 100L / deltaTotal * 10L / 10L;
                    }
                }
                this.lastUsed = used;
                this.lastTotal = total;
            } else if (values.length >= 8) {
                int index = 1;
                long _user = Long.parseLong(values[(index++)]);
                long _nice = Long.parseLong(values[(index++)]);
                long _system = Long.parseLong(values[(index++)]);
                long _idle = Long.parseLong(values[(index++)]);
                long _iowait = Long.parseLong(values[(index++)]);
                long _irq = Long.parseLong(values[(index++)]);
                long _softirq = Long.parseLong(values[(index++)]);
                long used = _user + _nice + _system + _iowait + _irq + _softirq;
                long total = used + _idle;
                if ((this.lastUsed > 0L) && (this.lastTotal > 0L)) {
                    long deltaUsed = used - this.lastUsed;
                    long deltaTotal = total - this.lastTotal;
                    if (deltaTotal > 0L) {
                        value = deltaUsed * 100L / deltaTotal * 10L / 10L;
                    }
                }
                this.lastUsed = used;
                this.lastTotal = total;
            }
        }
        return value;
    }

    private double getCPUUsage() {
        String[] scriptCmds = (String[]) null;
        if (this.isWindows)
            scriptCmds = new String[]{"strComputer = \".\"", "Set objWMIService = GetObject(\"winmgmts:\" _", " & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\")", "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor \",,48)", "load = 0", "n = 0", "For Each objItem in colItems", " load = load + objItem.LoadPercentage", " n = n + 1", "Next", "Wscript.Echo (load/n)"};
        else {
            scriptCmds = new String[]{"cat /proc/stat | head -n 1"};
        }

        return parseResult(execute(scriptCmds));
    }

    private long getFreeDisk() {
        if (this.isWindows) ;
        try {
            char c;
            List cmdList = new ArrayList();
            cmdList.add("CMD.EXE");
            cmdList.add("/C");
            cmdList.add("dir");
            cmdList.add(System.getProperty("user.dir"));

            ProcessBuilder pb = new ProcessBuilder(cmdList);
            Process p = pb.start();

            BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String lastLine = null;
            String str = null;
            while ((str = stdout.readLine()) != null) {
                lastLine = str;
            }
            if (lastLine == null) break label279;
            lastLine = lastLine.trim();
            int index = lastLine.lastIndexOf(",");
            if (index <= 0) return 0L;
            int pos0 = 0;
            while (--index >= 0) {
                char c = lastLine.charAt(index);
                if (Character.isDigit(c)) continue;
                if (c == ',') continue;
                pos0 = index + 1;
                break;
            }
            StringBuffer sb = new StringBuffer();

            while ((c == ',') && (pos0 < lastLine.length())) {
                c = lastLine.charAt(pos0++);
                if (Character.isDigit(c)) {
                    sb.append(c);
                }

            }

            long freeBytes = Long.parseLong(sb.toString());
            return (freeBytes >>> 20);
        } catch (Exception e) {
            e.printStackTrace();
            break label279:

            return 0L;
        }
        label279:
        return 0L;
    }

    public final void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public final long getMaxMemory() {
        return this.maxMemory;
    }

    public final void setAutoMonitor(boolean autoMonitor) {
        this.autoMonitor = autoMonitor;
    }
}