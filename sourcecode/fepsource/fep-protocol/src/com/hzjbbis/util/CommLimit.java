package com.hzjbbis.util;

class CommLimit {
    private long maxSmsCount;
    private long maxThroughput;

    public long getMaxSmsCount() {
        return this.maxSmsCount;
    }

    public void setMaxSmsCount(long maxSmsCount) {
        this.maxSmsCount = maxSmsCount;
    }

    public long getMaxThroughput() {
        return this.maxThroughput;
    }

    public void setMaxThroughput(long maxThroughput) {
        this.maxThroughput = maxThroughput;
    }
}