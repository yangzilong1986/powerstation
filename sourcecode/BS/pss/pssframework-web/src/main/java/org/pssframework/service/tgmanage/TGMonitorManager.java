package org.pssframework.service.tgmanage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.tgmanage.TGMonitorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Nick
 *
 */
@Service
public class TGMonitorManager extends BaseManager<HashMap<String, String>, Serializable> {
    @Autowired
    private TGMonitorDao tgMonitorDao;

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    protected EntityDao getEntityDao() {
        return null;
    }

    /**
     * 
     * @param tgId
     * @param statementName
     * @return
     */
    @SuppressWarnings("unchecked")
    public List findCombBoxByTg(Long tgId, String statementName) {
        logger.info("---findCombBoxByTg---");
        logger.info("tgId : " + tgId);
        logger.info("statementName : " + statementName);
        List list = tgMonitorDao.findCombBoxByTg(tgId, statementName);
        logger.info("list size : " + list.size());
        return list;
    }
}
