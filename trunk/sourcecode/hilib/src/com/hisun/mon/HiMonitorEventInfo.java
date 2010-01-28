package com.hisun.mon;


public class HiMonitorEventInfo {
    private String id = "";

    private String type = "1";

    private String subType = "";

    private String level = "INFO";

    private String msg = "";

    private String extMsg = "";

    private String origin = "";
    private long time;
    private String sip = "";


    public HiMonitorEventInfo() {
        this.time = System.currentTimeMillis();

    }


    public HiMonitorEventInfo(String id, String msg) {

        id = this.id;

        msg = this.msg;

        this.time = System.currentTimeMillis();

    }


    public String getId() {

        return this.id;

    }


    public void setId(String id) {

        this.id = id;

    }


    public String getType() {

        return this.type;

    }


    public void setType(String type) {

        this.type = type;

    }


    public String getSubType() {

        return this.subType;

    }


    public void setSubType(String subType) {

        this.subType = subType;

    }


    public String getLevel() {

        return this.level;

    }


    public void setLevel(String level) {

        this.level = level;

    }


    public String getMsg() {

        return this.msg;

    }


    public void setMsg(String msg) {

        this.msg = msg;

    }


    public String getExtMsg() {

        return this.extMsg;

    }


    public void setExtMsg(String extMsg) {

        this.extMsg = extMsg;

    }


    public String getOrigin() {

        return this.origin;

    }


    public void setOrigin(String origin) {

        this.origin = origin;

    }


    public long getTime() {

        return this.time;

    }


    public void setTime(long time) {

        this.time = time;

    }


    public String getSip() {

        return this.sip;

    }


    public void setSip(String sip) {

        this.sip = sip;

    }


    public String toString() {

        StringBuffer sb = new StringBuffer();


        sb.append("EventInfo=>ID[");

        sb.append(getId());

        sb.append("]");

        sb.append("TYPE[");

        sb.append(getType());

        sb.append("]");

        sb.append("LEVEL[");

        sb.append(getLevel());

        sb.append("]");

        sb.append("MSG[");

        sb.append(getMsg());

        sb.append("]");

        sb.append("Time[");

        sb.append(getTime());

        sb.append("]");


        return sb.toString();
    }


    public void send() {

        HiMonitorEventInfoPool monitorEventInfoPool = HiMonitorEventInfoPool.getInstance();

        monitorEventInfoPool.addEvent(this);

    }

}