package com.hisun.term.tcp.nio;

public class HiChannelContextData {
    int maxFrameSequence;
    String termLogicAddr;
    String termIP;
    int channelStatus;
    int channelCloseReason;
    long recentCreateTime;
    int currentFrameSeq;
    long recentCloseTime;
    long recentCommTime;
    long totalRecvBytes;
    long totalSendBytes;

    public HiChannelContextData() {
        this.maxFrameSequence = 16;

        this.recentCreateTime = System.currentTimeMillis();

        this.recentCloseTime = -1L;

        this.recentCommTime = -1L;
    }

    public int getChannelCloseReason() {
        return this.channelCloseReason;
    }

    public void setChannelCloseReason(int channelCloseReason) {
        this.channelCloseReason = channelCloseReason;
    }

    public int getChannelStatus() {
        return this.channelStatus;
    }

    public void setChannelStatus(int channelStatus) {
        this.channelStatus = channelStatus;
    }

    public synchronized int getCurrentFrameSeq() {
        int seq = this.currentFrameSeq;
        this.currentFrameSeq = ((this.currentFrameSeq + 1) % this.maxFrameSequence);
        return seq;
    }

    public void setCurrentFrameSeq(int currentFrameSeq) {
        this.currentFrameSeq = currentFrameSeq;
    }

    public long getRecentCloseTime() {
        return this.recentCloseTime;
    }

    public void setRecentCloseTime(long recentCloseTime) {
        this.recentCloseTime = recentCloseTime;
    }

    public long getRecentCommTime() {
        return this.recentCommTime;
    }

    public void setRecentCommTime(long recentCommTime) {
        this.recentCommTime = recentCommTime;
    }

    public long getRecentCreateTime() {
        return this.recentCreateTime;
    }

    public void setRecentCreateTime(long recentCreateTime) {
        this.recentCreateTime = recentCreateTime;
    }

    public String getTermIP() {
        return this.termIP;
    }

    public void setTermIP(String termIP) {
        this.termIP = termIP;
    }

    public String getTermLogicAddr() {
        return this.termLogicAddr;
    }

    public void setTermLogicAddr(String termLogicAddr) {
        this.termLogicAddr = termLogicAddr;
    }

    public long getTotalRecvBytes() {
        return this.totalRecvBytes;
    }

    public void setTotalRecvBytes(long totalRecvBytes) {
        this.totalRecvBytes = totalRecvBytes;
    }

    public long getTotalSendBytes() {
        return this.totalSendBytes;
    }

    public void setTotalSendBytes(long totalSendBytes) {
        this.totalSendBytes = totalSendBytes;
    }

    public int getMaxFrameSequence() {
        return this.maxFrameSequence;
    }

    public void setMaxFrameSequence(int maxFrameSequence) {
        this.maxFrameSequence = maxFrameSequence;
    }
}