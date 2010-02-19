package com.hzjbbis.fk.monitor;

public class MonitorDataItem {
    public double cpuUsage = 0.0D;
    public long freeDisk = 0L;
    public long totalMemory = 0L;
    public long freeMemory = 0L;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("cpu=").append(this.cpuUsage).append("%;  ");
        sb.append("freeDisk=").append(this.freeDisk).append("M;  ");
        sb.append("totalMemory=").append(this.totalMemory).append("M;  ");
        sb.append("freeMemory=").append(this.freeMemory).append("M");
        return sb.toString();
    }
}