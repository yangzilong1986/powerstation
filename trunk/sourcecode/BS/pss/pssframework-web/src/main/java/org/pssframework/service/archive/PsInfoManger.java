/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.PsInfoDao;
import org.pssframework.model.archive.PsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class PsInfoManger extends BaseManager<PsInfo, Long> {

	@Autowired
	private PsInfoDao psInfoDao;
	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return psInfoDao;
	}

	public List<PsInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
