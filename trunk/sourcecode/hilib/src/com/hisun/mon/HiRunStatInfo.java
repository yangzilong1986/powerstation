package com.hisun.mon;


import java.util.HashMap;
import java.util.Iterator;


public class HiRunStatInfo {
    HashMap typeStats;
    int maxTime;
    double avgTime;
    int minTime;
    long lastSysTm;


    public HiRunStatInfo() {

        this.typeStats = new HashMap();

    }


    public HiRunStatInfo cloneObject() {

        HiRunStatInfo info = new HiRunStatInfo();

        info.maxTime = this.maxTime;

        info.minTime = this.minTime;

        info.lastSysTm = this.lastSysTm;

        info.typeStats.putAll(this.typeStats);

        return info;

    }


    public void clear() {

        this.maxTime = 0;

        this.minTime = 0;

        this.avgTime = 0.0D;

        this.typeStats.clear();

    }


    public void once(int elapseTm, long sysTm, String status) {

        if (elapseTm > this.maxTime) {

            this.maxTime = elapseTm;

        }


        if (this.minTime == 0) {

            this.minTime = elapseTm;

        }


        if (elapseTm < this.minTime) {

            this.minTime = elapseTm;

        }

        if (this.typeStats.containsKey(status)) {

            long t = ((Long) this.typeStats.get(status)).longValue();

            t += 1L;

            this.typeStats.put(status, Long.valueOf(t));

        } else {

            this.typeStats.put(status, Long.valueOf(1L));

        }

        Iterator iter = this.typeStats.values().iterator();

        long sum = 0L;

        while (iter.hasNext()) {

            sum += ((Long) iter.next()).longValue();

        }

        this.avgTime = (((sum - 1L) * this.avgTime + elapseTm) * 1.0D / sum);

        this.lastSysTm = sysTm;

    }


    public HashMap getProcStatMap() {

        return this.typeStats;

    }


    public void setProcStatMap(HashMap procStatMap) {

        this.typeStats = procStatMap;

    }


    public int getMaxTime() {

        return this.maxTime;

    }


    public void setMaxTime(int maxTime) {

        this.maxTime = maxTime;

    }


    public double getAvgTime() {

        return this.avgTime;

    }


    public void setAvgTime(double avgTime) {

        this.avgTime = avgTime;

    }


    public int getMinTime() {

        return this.minTime;

    }


    public void setMinTime(int minTime) {

        this.minTime = minTime;

    }


    public long getLastSysTm() {

        return this.lastSysTm;

    }


    public void setLastSysTm(long lastSysTm) {

        this.lastSysTm = lastSysTm;

    }

}