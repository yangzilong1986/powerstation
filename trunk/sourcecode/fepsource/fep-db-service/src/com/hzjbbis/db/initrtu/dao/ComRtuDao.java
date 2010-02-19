package com.hzjbbis.db.initrtu.dao;

import com.hzjbbis.fk.model.ComRtu;

import java.util.List;

public abstract interface ComRtuDao {
    public abstract List<ComRtu> loadComRtu();

    public abstract List<ComRtu> loadComGwRtu();
}