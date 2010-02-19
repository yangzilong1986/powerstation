package com.hzjbbis.db.rtu;

import com.hzjbbis.fk.model.ComRtu;

import java.util.Collection;

public abstract interface RtuStatusUpdateDao {
    public abstract void update(Collection<ComRtu> paramCollection);
}