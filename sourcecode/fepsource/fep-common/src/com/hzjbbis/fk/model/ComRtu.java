package com.hzjbbis.fk.model;

import com.hzjbbis.fk.utils.CalendarUtil;

import java.util.Date;

public class ComRtu {
    private String rtuId;
    private String deptCode = "";
    private String rtuProtocol;
    private int rtua;
    private String logicAddress;
    private String manufacturer;
    private String simNum;
    private String commType;
    private String commAddress;
    private String b1CommType;
    private String b1CommAddress;
    private String b2CommType;
    private String b2CommAddress;
    private String activeGprs;
    private String activeUms;
    private int upGprsFlowmeter;
    private int upSmsCount;
    private int downGprsFlowmeter;
    private int downSmsCount;
    private String upMobile;
    private long lastGprsTime;
    private long lastSmsTime;
    private int taskCount;
    private int upGprsCount;
    private int downGprsCount;
    private String misSmsAddress;
    private String misGprsAddress;
    private String rtuIpAddr;
    private String activeSubAppId;
    private int heartbeatCount = 0;
    private long lastHeartbeat = 0L;

    private int heartSavePosition = -1;
    private int flowSavePosition = -1;
    private int rtuSavePosition = -1;
    private long lastIoTime;
    private long lastReqTime = 0L;

    public String getActiveGprs() {
        return this.activeGprs;
    }

    public void setActiveGprs(String activeGprs) {
        this.activeGprs = activeGprs;
    }

    public String getActiveUms() {
        return this.activeUms;
    }

    public void setActiveUms(String activeUms) {
        this.activeUms = activeUms;
    }

    public String getB1CommAddress() {
        return this.b1CommAddress;
    }

    public void setB1CommAddress(String commAddress) {
        this.b1CommAddress = commAddress;
    }

    public String getB1CommType() {
        return this.b1CommType;
    }

    public void setB1CommType(String commType) {
        this.b1CommType = commType;
    }

    public String getB2CommAddress() {
        return this.b2CommAddress;
    }

    public void setB2CommAddress(String commAddress) {
        this.b2CommAddress = commAddress;
    }

    public String getB2CommType() {
        return this.b2CommType;
    }

    public void setB2CommType(String commType) {
        this.b2CommType = commType;
    }

    public String getCommAddress() {
        return this.commAddress;
    }

    public void setCommAddress(String commAddress) {
        this.commAddress = commAddress;
    }

    public String getCommType() {
        return this.commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public int getUpGprsFlowmeter() {
        return this.upGprsFlowmeter;
    }

    public void setUpGprsFlowmeter(int curGprsFlowmeter) {
        this.upGprsFlowmeter = curGprsFlowmeter;
    }

    public void addUpGprsFlowmeter(int flow) {
        this.upGprsFlowmeter += flow;
    }

    public void incUpSmsCount() {
        this.upSmsCount += 1;
    }

    public int getUpSmsCount() {
        return this.upSmsCount;
    }

    public void setUpSmsCount(int count) {
        this.upSmsCount = count;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public long getLastIoTime() {
        return this.lastIoTime;
    }

    public void setLastIoTime(long lastIoTime) {
        this.lastIoTime = lastIoTime;
    }

    public String getLogicAddress() {
        return this.logicAddress;
    }

    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getRtua() {
        return this.rtua;
    }

    public void setRtua(int rtua) {
        this.rtua = rtua;
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public String getRtuProtocol() {
        return this.rtuProtocol;
    }

    public void setRtuProtocol(String rtuProtocol) {
        this.rtuProtocol = rtuProtocol;
    }

    public String getSimNum() {
        if ((this.upMobile != null) && (this.upMobile.length() >= 11)) return this.upMobile;
        return this.simNum;
    }

    public void setSimNum(String simNum) {
        this.simNum = simNum;
    }

    public int getDownGprsFlowmeter() {
        return this.downGprsFlowmeter;
    }

    public void setDownGprsFlowmeter(int downGprsFlowmeter) {
        this.downGprsFlowmeter = downGprsFlowmeter;
    }

    public void addDownGprsFlowmeter(int flow) {
        this.downGprsFlowmeter += flow;
    }

    public int getDownSmsCount() {
        return this.downSmsCount;
    }

    public void setDownSmsCount(int downSmsCounter) {
        this.downSmsCount = downSmsCounter;
    }

    public void incDownSmsCount() {
        this.downSmsCount += 1;
    }

    public String getUpMobile() {
        if ((this.upMobile != null) && (this.upMobile.length() > 0)) {
            return this.upMobile;
        }
        return this.simNum;
    }

    public void setUpMobile(String upMobile) {
        this.upMobile = upMobile;
    }

    public long getLastGprsTime() {
        return this.lastGprsTime;
    }

    public void setLastGprsTime(long lastGprsTime) {
        this.lastGprsTime = lastGprsTime;
    }

    public long getLastSmsTime() {
        return this.lastSmsTime;
    }

    public void setLastSmsTime(long lastSmsTime) {
        this.lastSmsTime = lastSmsTime;
    }

    public int getTaskCount() {
        return this.taskCount;
    }

    public int getHasTask() {
        return ((this.taskCount != 0) ? 1 : 0);
    }

    public String getTaskCountString() {
        return String.valueOf(this.taskCount);
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public void incTaskCount() {
        this.taskCount += 1;
    }

    public int getUpGprsCount() {
        return this.upGprsCount;
    }

    public void setUpGprsCount(int upGprsCount) {
        this.upGprsCount = upGprsCount;
    }

    public void incUpGprsCount() {
        this.upGprsCount += 1;
    }

    public int getDownGprsCount() {
        return this.downGprsCount;
    }

    public void setDownGprsCount(int downGprsCount) {
        this.downGprsCount = downGprsCount;
    }

    public void incDownGprsCount() {
        this.downGprsCount += 1;
    }

    public String getMisSmsAddress() {
        return this.misSmsAddress;
    }

    public void setMisSmsAddress(String misSmsAddress) {
        this.misSmsAddress = misSmsAddress;
    }

    public String getMisGprsAddress() {
        return this.misGprsAddress;
    }

    public void setMisGprsAddress(String misGprsAddress) {
        this.misGprsAddress = misGprsAddress;
    }

    public void clearStatus() {
        this.upGprsFlowmeter = 0;
        this.upSmsCount = 0;
        this.downGprsFlowmeter = 0;
        this.downSmsCount = 0;
        this.upMobile = null;
        this.lastGprsTime = 0L;
        this.lastSmsTime = 0L;
        this.taskCount = 0;
        this.upGprsCount = 0;
        this.downGprsCount = 0;

        this.misSmsAddress = null;
        this.misGprsAddress = null;
        this.heartbeatCount = 0;
        this.lastHeartbeat = 0L;
    }

    public void setLastReqTime(long lastReqTime) {
        this.lastReqTime = lastReqTime;
    }

    public long getLastReqTime() {
        return this.lastReqTime;
    }

    public Date getLastGprsRecvTime() {
        if (0L != this.lastGprsTime) {
            return new Date(this.lastGprsTime);
        }
        return null;
    }

    public Date getLastSmsRecvTime() {
        if (0L != this.lastSmsTime) {
            return new Date(this.lastSmsTime);
        }
        return null;
    }

    public String getDateString() {
        return CalendarUtil.getDateString(System.currentTimeMillis());
    }

    public String getRtuIpAddr() {
        return this.rtuIpAddr;
    }

    public void setRtuIpAddr(String rtuIpAddr) {
        this.rtuIpAddr = rtuIpAddr;
    }

    public String getActiveSubAppId() {
        return this.activeSubAppId;
    }

    public void setActiveSubAppId(String activeSubAppId) {
        this.activeSubAppId = activeSubAppId;
    }

    public final int getHeartbeatCount() {
        return this.heartbeatCount;
    }

    public final void setHeartbeatCount(int heartbeatCount) {
        this.heartbeatCount = heartbeatCount;
    }

    public final void incHeartbeat() {
        this.heartbeatCount += 1;
    }

    public final long getLastHeartbeat() {
        return this.lastHeartbeat;
    }

    public Date getLastHeartbeatTime() {
        if (0L != this.lastHeartbeat) {
            return new Date(this.lastHeartbeat);
        }
        return null;
    }

    public final void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public final int getHeartSavePosition() {
        return this.heartSavePosition;
    }

    public final void setHeartSavePosition(int heartSavePosition) {
        this.heartSavePosition = heartSavePosition;
    }

    public final int getFlowSavePosition() {
        return this.flowSavePosition;
    }

    public final void setFlowSavePosition(int flowSavePosition) {
        this.flowSavePosition = flowSavePosition;
    }

    public final int getRtuSavePosition() {
        return this.rtuSavePosition;
    }

    public final void setRtuSavePosition(int rtuSavePosition) {
        this.rtuSavePosition = rtuSavePosition;
    }
}