package com.hzjbbis.ws.logic;

public class ModuleSimpleProfile {
    private String moduleType;
    private String name;
    private boolean running;
    private long totalReceive;
    private long totalSend;
    private int perMinuteReceive;
    private int perMinuteSend;
    private long lastReceiveTime;

    public final String getModuleType() {
        return this.moduleType;
    }

    public final void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final boolean isRunning() {
        return this.running;
    }

    public final void setRunning(boolean running) {
        this.running = running;
    }

    public final long getTotalReceive() {
        return this.totalReceive;
    }

    public final void setTotalReceive(long totalReceive) {
        this.totalReceive = totalReceive;
    }

    public final long getTotalSend() {
        return this.totalSend;
    }

    public final void setTotalSend(long totalSend) {
        this.totalSend = totalSend;
    }

    public final int getPerMinuteReceive() {
        return this.perMinuteReceive;
    }

    public final void setPerMinuteReceive(int perMinuteReceive) {
        this.perMinuteReceive = perMinuteReceive;
    }

    public final int getPerMinuteSend() {
        return this.perMinuteSend;
    }

    public final void setPerMinuteSend(int perMinuteSend) {
        this.perMinuteSend = perMinuteSend;
    }

    public final long getLastReceiveTime() {
        return this.lastReceiveTime;
    }

    public final void setLastReceiveTime(long lastReceiveTime) {
        this.lastReceiveTime = lastReceiveTime;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(512);
        sb.append("\r\ntype=").append(this.moduleType);
        sb.append("; name=").append(this.name);
        sb.append("; running=").append(this.running);
        sb.append("; totalReceive=").append(this.totalReceive);
        sb.append("; totalSend=").append(this.totalSend);
        sb.append("; perMinuteReceive=").append(this.perMinuteReceive);
        sb.append("; perMinuteSend=").append(this.perMinuteSend);
        sb.append("; lastReceiveTime=").append(this.lastReceiveTime);
        return sb.toString();
    }
}