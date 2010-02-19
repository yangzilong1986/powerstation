package com.hzjbbis.db.batch.dao;

public abstract interface IBatchDao {
    public abstract void setSql(String paramString);

    public abstract void setSqlAlt(String paramString);

    public abstract void setAdditiveSql(String paramString);

    public abstract void setAdditiveParameter(Object paramObject);

    public abstract boolean add(Object paramObject);

    public abstract boolean add(Object[] paramArrayOfObject);

    public abstract int getKey();

    public abstract void batchUpdate();

    public abstract int size();

    public abstract void setBatchSize(int paramInt);

    public abstract long getLastIoTime();

    public abstract void setDelaySecond(int paramInt);

    public abstract long getDelayMilliSeconds();

    public abstract boolean hasDelayData();
}