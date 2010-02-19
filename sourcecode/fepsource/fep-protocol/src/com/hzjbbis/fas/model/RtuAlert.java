package com.hzjbbis.fas.model;

import com.hzjbbis.util.HexDump;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RtuAlert {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final String FLAG_UNPROCESSED = "00";
    public static final String FLAG_PROCESSED = "01";
    private String dataSaveID;
    private String corpNo;
    private String customerNo;
    private String rtuId;
    private String tn;
    private String stationNo;
    private int alertCode;
    private String alertCodeHex;
    private Date alertTime;
    private Date receiveTime;
    private String processFlag;
    private List args;
    private String sbcs;
    private String txfs;

    public RtuAlert() {
        this.processFlag = "00";
    }

    public void addAlertArg(RtuAlertArg arg) {
        if (this.args == null) {
            this.args = new ArrayList();
        }
        this.args.add(arg);
    }

    public String getAlertCodeHex() {
        if (this.alertCodeHex == null) {
            this.alertCodeHex = HexDump.toHex((short) this.alertCode);
        }
        return this.alertCodeHex;
    }

    public String getDataSaveID() {
        return this.dataSaveID;
    }

    public void setDataSaveID(String dataSaveID) {
        this.dataSaveID = dataSaveID;
    }

    public String getCorpNo() {
        return this.corpNo;
    }

    public void setCorpNo(String corpNo) {
        this.corpNo = corpNo;
    }

    public String getCustomerNo() {
        return this.customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getStationNo() {
        return this.stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public int getAlertCode() {
        return this.alertCode;
    }

    public void setAlertCode(int alertCode) {
        this.alertCode = alertCode;
    }

    public Date getAlertTime() {
        return this.alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public void setAlertTime(String alertTime) {
        try {
            this.alertTime = df.parse(alertTime);
        } catch (Exception e) {
        }
    }

    public Date getReceiveTime() {
        return this.receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getProcessFlag() {
        return this.processFlag;
    }

    public void setProcessFlag(String processFlag) {
        this.processFlag = processFlag;
    }

    public List getArgs() {
        return this.args;
    }

    public void setArgs(List args) {
        this.args = args;
    }

    public String getSbcs() {
        return this.sbcs;
    }

    public void setSbcs(String sbcs) {
        this.sbcs = sbcs;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }
}