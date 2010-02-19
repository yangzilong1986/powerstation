package com.hzjbbis.fas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HostCommand implements Serializable {
    private static final long serialVersionUID = -4703256789407620876L;
    public static final String STATUS_RUNNING = "0";
    public static final String STATUS_SUCCESS = "1";
    public static final String STATUS_RTU_FAILED = "2";
    public static final String STATUS_COMM_FAILED = "3";
    public static final String STATUS_TIMEOUT = "4";
    public static final String STATUS_FWD_CMD_NO_RESPONSE = "5";
    public static final String STATUS_PARA_INVALID = "6";
    public static final String STATUS_PERMISSION_DENIDE = "7";
    public static final String STATUS_ITEM_INVALID = "8";
    public static final String STATUS_TIME_OVER = "9";
    public static final String STATUS_TARGET_UNREACHABLE = "10";
    public static final String STATUS_SEND_FAILURE = "11";
    public static final String STATUS_SMS_OVERFLOW = "12";
    public static final String STATUS_PRAR_ERROR = "13";
    public static final String STATUS_PARSE_ERROR = "14";
    public static final String STATUS_TODB_ERROR = "15";
    public static final String STATUS_RTUAMTCOM_ERROR = "16";
    private Long id;
    private Long taskId;
    private String rtuId;
    private int paramCount;
    private int messageCount;
    private Date requestTime;
    private Date responseTime;
    private String status;
    private boolean doUpdate;
    private boolean writeParams;
    private FaalRequest request;
    private List<HostCommandResult> results;
    private List measurePoints;
    private HashMap mpLines;
    private String errcode;

    public HostCommand() {
        this.doUpdate = false;

        this.writeParams = false;
    }

    public void addResult(HostCommandResult result) {
        if (this.results == null) {
            this.results = new ArrayList();
        }
        this.results.add(result);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public int getParamCount() {
        return this.paramCount;
    }

    public void setParamCount(int paramCount) {
        this.paramCount = paramCount;
    }

    public int getMessageCount() {
        return this.messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public Date getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.errcode = status;
    }

    public boolean isDoUpdate() {
        return this.doUpdate;
    }

    public void setDoUpdate(boolean doUpdate) {
        this.doUpdate = doUpdate;
    }

    public boolean isWriteParams() {
        return this.writeParams;
    }

    public void setWriteParams(boolean writeParams) {
        this.writeParams = writeParams;
    }

    public FaalRequest getRequest() {
        return this.request;
    }

    public void setRequest(FaalRequest request) {
        this.request = request;
    }

    public List<HostCommandResult> getResults() {
        return this.results;
    }

    public void setResults(List<HostCommandResult> results) {
        this.results = results;
    }

    public List getMeasurePoints() {
        return this.measurePoints;
    }

    public void setMeasurePoints(List measurePoints) {
        this.measurePoints = measurePoints;
    }

    public HashMap getMpLines() {
        return this.mpLines;
    }

    public void setMpLines(HashMap mpLines) {
        this.mpLines = mpLines;
    }

    public void addLine(String mp, String line) {
        if (this.mpLines == null) {
            this.mpLines = new HashMap();
        }
        this.mpLines.put(line, mp);
    }

    public String getMpByLine(String line) {
        if ((this.mpLines != null) && (this.mpLines.containsKey(line))) {
            return ((String) this.mpLines.get(line));
        }

        return null;
    }

    public String getErrcode() {
        return this.errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
        this.status = this.errcode;
    }
}