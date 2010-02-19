package com.hzjbbis.fas.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RtuData {
    private static final SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM");
    private String taskNum;
    private String logicAddress;
    private String tn;
    private Date time;
    private List<RtuDataItem> dataList;

    public RtuData() {
        this.dataList = new ArrayList();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[logicAddress=").append(getLogicAddress()).append(", taskNum=").append(getTaskNum()).append(", time=").append(df1.format(getTime())).append("]");

        return sb.toString();
    }

    public void addDataList(RtuDataItem rtuDataItem) {
        this.dataList.add(rtuDataItem);
    }

    public String getLogicAddress() {
        return this.logicAddress;
    }

    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    public String getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(String time) {
        try {
            if (time.trim().length() == 16) this.time = df1.parse(time);
            else if (time.trim().length() == 10) this.time = df2.parse(time);
            else if (time.trim().length() == 7) this.time = df3.parse(time);
        } catch (Exception e) {
        }
    }

    public List<RtuDataItem> getDataList() {
        return this.dataList;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}