package com.hzjbbis.db.rtu;

import com.hzjbbis.fk.model.*;

import java.util.List;

public abstract interface RtuRefreshDao {
    public abstract BizRtu getRtu(String paramString);

    public abstract BizRtu getRtu(int paramInt);

    public abstract ComRtu getComRtu(int paramInt);

    public abstract List<MeasuredPoint> getMeasurePoints(String paramString);

    public abstract List<MeasuredPoint> getGwMeasurePoints(String paramString);

    public abstract List<RtuTask> getRtuTasks(String paramString);

    public abstract List<RtuTask> getGwRtuTasks(String paramString);

    public abstract TaskTemplate getTaskTemplate(String paramString);

    public abstract List<TaskTemplateItem> getTaskTemplateItems(String paramString);
}