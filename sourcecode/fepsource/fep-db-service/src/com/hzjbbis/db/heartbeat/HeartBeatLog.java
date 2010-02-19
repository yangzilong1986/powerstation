package com.hzjbbis.db.heartbeat;

public class HeartBeatLog {
    private int id;
    private int weekno;
    private boolean issuccess;
    private long startime;
    private long endtime;

    public long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIssuccess() {
        return this.issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public long getStartime() {
        return this.startime;
    }

    public void setStartime(long startime) {
        this.startime = startime;
    }

    public int getWeekno() {
        return this.weekno;
    }

    public void setWeekno(int weekno) {
        this.weekno = weekno;
    }
}