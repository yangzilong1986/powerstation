package com.hzjbbis.db.heartbeat;

import java.sql.SQLException;
import java.util.List;

public abstract interface HeartBeatDao {
    public abstract boolean doInit(int paramInt1, int paramInt2) throws SQLException;

    public abstract List<HeartBeatLog> getLogResult(int paramInt) throws SQLException;

    public abstract void updateLogResult(boolean paramBoolean, long paramLong, int paramInt1, int paramInt2) throws SQLException;

    public abstract void insertLogResult(int paramInt, long paramLong) throws SQLException;

    public abstract int[] batchUpdate(List<HeartBeat> paramList, int paramInt);

    public abstract void batchInsert(List<HeartBeat> paramList, int paramInt);
}