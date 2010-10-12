package org.pssframework.dao.tgmanage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.pssframework.dao.BaseIbatis3Dao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Nick
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class TGMonitorDao extends BaseIbatis3Dao<HashMap<String, String>, Serializable> {

    @Override
    public String getPrefix() {
        return "TGMonitor";
    }

    @Override
    public void saveOrUpdate(HashMap<String, String> entity) throws DataAccessException {
    }

    /**
     * 
     * @param tgId
     * @param statementName
     * @return
     */
    public List findCombBoxByTg(Long tgId, String statementName) {
        List list = getSqlSessionTemplate().selectList(getQuery(statementName), tgId, 0, Integer.MAX_VALUE);
        return list;
    }
}
