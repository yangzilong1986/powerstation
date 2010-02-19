package com.hzjbbis.fk.model;

import java.util.ArrayList;

public class TaskDbConfig {
    private String code;
    private String dbConfigStr;
    private ArrayList<TaskDbConfigItem> taskDbConfigItemList = new ArrayList();

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addTaskDbConfigItemList(String[] s) {
        TaskDbConfigItem tsti = new TaskDbConfigItem();
        if (s.length == 4) {
            tsti.setTableName(s[0]);
            tsti.setFieldName(s[1]);
            tsti.setTag(s[2]);
            tsti.setTaskPropertyStr(s[3]);
            this.taskDbConfigItemList.add(tsti);
        }
    }

    public void setTaskDbConfigItemList(String dbConfigStr) {
        String[] s = dbConfigStr.split("/");
        for (int i = 0; i < s.length; ++i) {
            String[] ss = s[i].split(";");
            addTaskDbConfigItemList(ss);
        }
    }

    public String getDbConfigStr() {
        return this.dbConfigStr;
    }

    public void setDbConfigStr(String dbConfigStr) {
        this.dbConfigStr = dbConfigStr;
        setTaskDbConfigItemList(dbConfigStr);
    }

    public ArrayList<TaskDbConfigItem> getTaskDbConfigItemList() {
        return this.taskDbConfigItemList;
    }
}