/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.TranInfoDao;
import org.pssframework.model.archive.TranInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * 
 */
@Service
public class TranInfoManger extends BaseManager<TranInfo, Long> {

    @Autowired
    private TranInfoDao tranInfoDao;

    @Override
    protected EntityDao getEntityDao() {
        return tranInfoDao;
    }

    public <X> List<X> findByPageRequest(Map mapRequest) {
        return tranInfoDao.findByPageRequest(mapRequest);
    }

}
