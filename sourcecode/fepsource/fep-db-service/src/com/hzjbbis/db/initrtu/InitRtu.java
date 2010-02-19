package com.hzjbbis.db.initrtu;

import com.hzjbbis.db.initrtu.dao.ComRtuDao;
import com.hzjbbis.fk.model.ComRtu;
import org.apache.log4j.Logger;

import java.util.List;

public class InitRtu {
    private static final Logger log = Logger.getLogger(InitRtu.class);
    private ComRtuDao comRtuDao;

    public List<ComRtu> loadComRtu() {
        try {
            return this.comRtuDao.loadComRtu();
        } catch (Exception exp) {
            log.error("初始化终端通信参数异常：" + exp.getLocalizedMessage(), exp);
        }
        return null;
    }

    public void setComRtuDao(ComRtuDao comRtuDao) {
        this.comRtuDao = comRtuDao;
    }
}