package com.hzjbbis.db.batch;

import com.hzjbbis.db.batch.dao.IBatchDao;

import java.util.ArrayList;
import java.util.List;

public class BatchDaoParameterUtils {
    private static BatchDaoParameterUtils instance;
    private List<IBatchDao> batchDaoList = new ArrayList();
    private Object additiveParameter;

    private BatchDaoParameterUtils() {
        instance = this;
    }

    public static BatchDaoParameterUtils getInstance() {
        if (instance == null) instance = new BatchDaoParameterUtils();
        return instance;
    }

    public void setBatchDaoList(List<IBatchDao> batchDaoList) {
        this.batchDaoList = batchDaoList;
        if (this.additiveParameter != null) for (IBatchDao dao : batchDaoList)
            dao.setAdditiveParameter(this.additiveParameter);
    }

    public void setAdditiveParameter(Object additiveParameter) {
        this.additiveParameter = additiveParameter;
        for (IBatchDao dao : this.batchDaoList)
            dao.setAdditiveParameter(additiveParameter);
    }
}