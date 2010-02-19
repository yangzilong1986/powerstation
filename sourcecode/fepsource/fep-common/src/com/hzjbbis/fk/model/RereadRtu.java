package com.hzjbbis.fk.model;

import java.util.HashMap;
import java.util.Map;

public class RereadRtu {
    private String rtuId;
    private Map<Integer, RereadTask> rereadTasksMap = new HashMap();

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public Map<Integer, RereadTask> getRereadTasksMap() {
        return this.rereadTasksMap;
    }

    public void setRereadTasksMap(Map<Integer, RereadTask> rereadTasksMap) {
        this.rereadTasksMap = rereadTasksMap;
    }
}