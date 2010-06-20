/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.MeterInfoDao;
import org.pssframework.model.archive.MeterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class MeterInfoManger extends BaseManager<MeterInfo, Long> {
    @Autowired
    private MeterInfoDao meterInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
        return meterInfoDao;
	}

	public List<MeterInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
        return meterInfoDao.findByPageRequest(mapRequest);
	}

}
