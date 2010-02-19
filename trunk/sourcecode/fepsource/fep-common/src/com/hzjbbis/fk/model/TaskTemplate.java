package com.hzjbbis.fk.model;

import com.hzjbbis.fk.utils.HexDump;

import java.util.ArrayList;
import java.util.List;

public class TaskTemplate {
    public static final String TASK_TYPE_NORMAL = "01";
    public static final String TASK_TYPE_FORWARD = "02";
    private static final String DELIM = ",";
    private String taskTemplateID;
    private String taskType;
    private int sampleStartTime;
    private String sampleStartTimeUnit;
    private int sampleInterval;
    private String sampleIntervalUnit;
    private int uploadStartTime;
    private String uploadStartTimeUnit;
    private int uploadInterval;
    private String uploadIntervalUnit;
    private int frequence;
    private int savepts;
    private int donums;
    private String dataCodesStr;
    private List<String> dataCodes = new ArrayList();
    private String tn;

    public static TaskTemplate parse(String s) {
        if (s == null) {
            throw new IllegalArgumentException("The task setting could not be null");
        }

        String[] values = s.split(",");
        String tt = values[0];
        if ((!(tt.equals("01"))) && (!(tt.equals("02")))) {
            throw new IllegalArgumentException("Invalid task type: " + tt);
        }

        TaskTemplate task = new TaskTemplate();
        task.setTaskType(tt);
        if (tt.equals("01")) {
            setTaskSchedule(task, values);
            task.setTn(values[10]);
            int diCount = Integer.parseInt(values[13]);
            for (int i = 0; i < diCount; ++i) {
                task.addDataCode(values[(14 + i)]);
            }
        } else if (tt.equals("02")) {
            setTaskSchedule(task, values);
        }
        return task;
    }

    private static void setTaskSchedule(TaskTemplate task, String[] values) {
        task.setSampleStartTime(Integer.parseInt(values[1]));
        task.setSampleStartTimeUnit(values[2]);
        task.setSampleInterval(Integer.parseInt(values[3]));
        task.setSampleIntervalUnit(values[4]);
        task.setUploadStartTime(Integer.parseInt(values[5]));
        task.setUploadStartTimeUnit(values[6]);
        task.setUploadInterval(Integer.parseInt(values[7]));
        task.setUploadIntervalUnit(values[8]);
        task.setFrequence(Integer.parseInt(values[9]));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public void addDataCode(String code) {
        this.dataCodes.add(code);
    }

    public String getDataCodesAsString() {
        if ((this.dataCodesStr == null) && (this.dataCodes != null)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.dataCodes.size(); ++i) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append((String) this.dataCodes.get(i));
            }
        }
        return this.dataCodesStr;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public int getSampleStartTime() {
        return this.sampleStartTime;
    }

    public void setSampleStartTime(int sampleStartTime) {
        this.sampleStartTime = sampleStartTime;
    }

    public String getSampleStartTimeUnit() {
        return this.sampleStartTimeUnit;
    }

    public void setSampleStartTimeUnit(String sampleStartTimeUnit) {
        this.sampleStartTimeUnit = sampleStartTimeUnit;
    }

    public int getSampleInterval() {
        return this.sampleInterval;
    }

    public void setSampleInterval(int sampleInterval) {
        this.sampleInterval = sampleInterval;
    }

    public String getSampleIntervalUnit() {
        return this.sampleIntervalUnit;
    }

    public void setSampleIntervalUnit(String sampleIntervalUnit) {
        this.sampleIntervalUnit = sampleIntervalUnit;
    }

    public int getUploadStartTime() {
        return this.uploadStartTime;
    }

    public void setUploadStartTime(int uploadStartTime) {
        this.uploadStartTime = uploadStartTime;
    }

    public String getUploadStartTimeUnit() {
        return this.uploadStartTimeUnit;
    }

    public void setUploadStartTimeUnit(String uploadStartTimeUnit) {
        this.uploadStartTimeUnit = uploadStartTimeUnit;
    }

    public int getUploadInterval() {
        return this.uploadInterval;
    }

    public void setUploadInterval(int uploadInterval) {
        this.uploadInterval = uploadInterval;
    }

    public String getUploadIntervalUnit() {
        return this.uploadIntervalUnit;
    }

    public void setUploadIntervalUnit(String uploadIntervalUnit) {
        this.uploadIntervalUnit = uploadIntervalUnit;
    }

    public int getFrequence() {
        return this.frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    public List<String> getDataCodes() {
        return this.dataCodes;
    }

    public void setDataCodes(List<String> dataCodes) {
        this.dataCodes = dataCodes;
        this.dataCodesStr = null;
    }

    public int getDonums() {
        return this.donums;
    }

    public int getSavepts() {
        return this.savepts;
    }

    public void setDonums(int donums) {
        this.donums = donums;
    }

    public void setSavepts(int savepts) {
        this.savepts = savepts;
    }

    public String toCodeValue() {
        String desc = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (this.taskType.equalsIgnoreCase("01")) {
                sb.append(this.taskType);
                sb.append(",");
                sb.append(this.sampleStartTimeUnit);
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.sampleStartTime));
                sb.append(",");
                sb.append(this.sampleIntervalUnit);
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.sampleInterval));
                sb.append(",");
                sb.append(this.uploadStartTimeUnit);
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.uploadStartTime));
                sb.append(",");
                sb.append(this.uploadIntervalUnit);
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.uploadInterval));
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.frequence));
                sb.append(",");
                sb.append(this.tn);
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.savepts));
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.donums));
                sb.append(",");
                sb.append(HexDump.toHex((byte) this.dataCodes.size()));
                sb.append(",");
                sb.append(getDataCodesAsString());
            }
            desc = sb.toString();
        } catch (Exception localException) {
        }
        return desc;
    }

    public String getDataCodesStr() {
        return this.dataCodesStr;
    }

    public void setDataCodesStr(String dataCodesStr) {
        this.dataCodesStr = dataCodesStr;
    }

    public String getTaskTemplateID() {
        return this.taskTemplateID;
    }

    public void setTaskTemplateID(String taskTemplateID) {
        this.taskTemplateID = taskTemplateID;
    }
}