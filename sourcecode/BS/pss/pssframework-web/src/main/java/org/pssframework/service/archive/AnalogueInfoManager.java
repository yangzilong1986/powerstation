/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.AnalogueInfoDao;
import org.pssframework.model.archive.AnalogueInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author djs-baocj
 *
 */
@Service
public class AnalogueInfoManager extends BaseManager<AnalogueInfo, Long> {
	@Autowired
	private AnalogueInfoDao analogueInfoDao;
	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return analogueInfoDao;
	}

	public List<AnalogueInfo> findByPageRequest(Map mapRequest) {
		return analogueInfoDao.findByPageRequest(mapRequest);
	}
}
