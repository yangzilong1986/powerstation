package com.hzjbbis.db.initrtu.dao;

import com.hzjbbis.fk.model.*;

import java.util.List;

public abstract interface BizRtuDao {
    public abstract List<BizRtu> loadBizRtu();

    public abstract List<BizRtu> loadBizGwRtu();

    public abstract List<RtuTask> loadRtuTasks();

    public abstract List<RtuTask> loadGwRtuTasks();

    public abstract List<MeasuredPoint> loadMeasuredPoints();

    public abstract List<MeasuredPoint> loadGwMeasuredPoints();

    public abstract List<RtuAlertCode> loadRtuAlertCodes();

    public abstract List<TaskDbConfig> loadTaskDbConfig();

    public abstract List<TaskTemplate> loadTaskTemplate();

    public abstract List<SysConfig> loadSysConfig();
}