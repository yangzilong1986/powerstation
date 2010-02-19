package com.hzjbbis.fas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class FaalRequest implements Serializable {
    private static final long serialVersionUID = 2937756926363569712L;
    public static final int TYPE_READ_FORWARD_DATA = 0;
    public static final int TYPE_READ_CURRENT_DATA = 1;
    public static final int TYPE_READ_TASK_DATA = 2;
    public static final int TYPE_READ_PROGRAM_LOG = 4;
    public static final int TYPE_REALTIME_WRITE_PARAMS = 7;
    public static final int TYPE_WRITE_PARAMS = 8;
    public static final int TYPE_READ_ALERT = 9;
    public static final int TYPE_CONFIRM_ALERT = 10;
    public static final int TYPE_SEND_SMS = 40;
    public static final int TYPE_REFRESH_CACHE = 254;
    public static final int TYPE_OTHER = 255;
    protected String protocol;
    protected int type;
    private List<FaalRequestParam> params;
    private List<String> rtuIds;
    private List<Long> cmdIds;
    private List<FaalRequestRtuParam> rtuParams;
    private String operator;
    private boolean scheduled;
    private Date scheduledTime;
    private boolean taskflag;
    private long timetag;

    public FaalRequest() {
        this.scheduled = false;

        this.taskflag = false;
    }

    public void addParam(FaalRequestParam param) {
        if (this.params == null) {
            this.params = new ArrayList();
        }
        this.params.add(param);
    }

    public void addRtuParam(FaalRequestRtuParam rtuParam) {
        if (this.rtuParams == null) {
            this.rtuParams = new ArrayList();
        }
        this.rtuParams.add(rtuParam);
    }

    public List<FaalRequestRtuParam> getRtuParams() {
        return this.rtuParams;
    }

    public void setRtuParams(List<FaalRequestRtuParam> rtuParams) {
        this.rtuParams = rtuParams;
    }

    public void addParam(String name, String value) {
        addParam(new FaalRequestParam(name, value));
    }

    public void setRtuIds(String[] ids) {
        this.rtuIds = Arrays.asList(ids);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(", type=").append(this.type).append(", rtuCount=").append((this.rtuIds == null) ? 0 : this.rtuIds.size()).append(", rtuIds=").append(this.rtuIds).append(", cmdIds=").append(this.cmdIds).append("]");

        return sb.toString();
    }

    public int getType() {
        return this.type;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<FaalRequestParam> getParams() {
        return this.params;
    }

    public void setParams(List<FaalRequestParam> params) {
        this.params = params;
    }

    public List<String> getRtuIds() {
        return this.rtuIds;
    }

    public void setRtuIds(List<String> rtuIds) {
        this.rtuIds = rtuIds;
    }

    public List<Long> getCmdIds() {
        return this.cmdIds;
    }

    public void setCmdIds(List<Long> cmdIds) {
        this.cmdIds = cmdIds;
    }

    public boolean isScheduled() {
        return this.scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public Date getScheduledTime() {
        return this.scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public boolean isTaskflag() {
        return this.taskflag;
    }

    public void setTaskflag(boolean taskflag) {
        this.taskflag = taskflag;
    }

    public long getTimetag() {
        return this.timetag;
    }

    public void setTimetag(long timetag) {
        this.timetag = timetag;
    }

    public void setType(int type) {
        this.type = type;
    }
}