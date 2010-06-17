package org.pssframework.service.eparam;

import org.pssframework.base.BaseManager;
import org.pssframework.dao.eparam.TermParamInfoDao;
import org.pssframework.model.eparam.TermParamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author Zhangyu
 * 
 */
@Service
public class TermParamManager extends BaseManager<TermParamInfo, Long> {
    @Autowired
    private TermParamInfoDao termParamInfoDao;

    @Override
    protected TermParamInfoDao getEntityDao() {
        // TODO Auto-generated method stub
        return this.termParamInfoDao;
    }

    @Override
    public TermParamInfo getById(Long id) throws DataAccessException {
        return termParamInfoDao.getById(id);
    }

}
