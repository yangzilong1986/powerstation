package com.hzjbbis.fk.model;

public class TaskDbConfigItem {
    private String tableName;
    private String fieldName;
    private String tag;
    private String taskPropertyStr;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTaskPropertyStr() {
        return this.taskPropertyStr;
    }

    public void setTaskPropertyStr(String taskPropertyStr) {
        this.taskPropertyStr = taskPropertyStr;
    }

    public boolean taskPropertyContains(String taskProperty) {
        return (this.taskPropertyStr.indexOf(taskProperty) <= -1);
    }
}