/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.MpInfoDao;
import org.pssframework.model.archive.MpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * 
 */
@Service
public class MpInfoManger extends BaseManager<MpInfo, Long> {

    @Autowired
    private MpInfoDao mpInfoDao;

    @Override
    protected EntityDao getEntityDao() {
        // TODO Auto-generated method stub
        return mpInfoDao;
    }

    public List<MpInfo> findByPageRequest(Map mapRequest) {
        // TODO Auto-generated method stub
        return mpInfoDao.findByPageRequest(mapRequest);
    }
}
