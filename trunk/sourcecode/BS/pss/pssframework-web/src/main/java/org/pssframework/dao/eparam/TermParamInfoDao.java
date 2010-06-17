package org.pssframework.dao.eparam;

import org.pssframework.dao.BaseIbatis3Dao;
import org.pssframework.model.eparam.TermParamInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * @author Zhangyu
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class TermParamInfoDao extends BaseIbatis3Dao<TermParamInfo, Long> {

    @Override
    public String getPrefix() {
        // TODO Auto-generated method stub
        return "TermParamInfo";
    }

    @Override
    public void saveOrUpdate(TermParamInfo paramE) throws DataAccessException {
        // TODO Auto-generated method stub

    }

    @Override
    public TermParamInfo getById(Long id) {
        return null;
    }

}
