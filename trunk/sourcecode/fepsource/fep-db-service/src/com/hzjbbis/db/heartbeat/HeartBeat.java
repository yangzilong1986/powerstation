package com.hzjbbis.db.heartbeat;

public class HeartBeat {
    private static final long FIRST_BEAT_DAY = -9223372036854775808L;
    private String rtua;
    private String deptCode;
    private int columnIndex;
    private String valueOrigin;
    private long value;
    private int weekTag;
    private int weekOfYear;
    private String valueTime;

    public String getValueTime() {
        return this.valueTime;
    }

    public void setValueTime(String valueTime) {
        this.valueTime = valueTime;
    }

    public String getKey() {
        return this.rtua + this.weekTag;
    }

    public boolean isFirstOfDay() {
        return (this.value != -9223372036854775808L);
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getRtua() {
        return this.rtua;
    }

    public void setRtua(String rtua) {
        this.rtua = rtua;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getWeekTag() {
        return this.weekTag;
    }

    public void setWeekTag(int weekFlag) {
        this.weekTag = weekFlag;
    }

    public int getWeekOfYear() {
        return this.weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public String getValueOrigin() {
        return this.valueOrigin;
    }

    public void setValueOrigin(String valueOrigin) {
        this.valueOrigin = valueOrigin;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}