/**
 * 
 */
package org.pssframework.service.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.OrgInfoDao;
import org.pssframework.model.system.OrgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class OrgInfoManager extends BaseManager<OrgInfo, Long> {

	@Override
	protected EntityDao getEntityDao() {
		return this.entityDao;
	}

	@Autowired
	private OrgInfoDao entityDao;

	public <X> List<X> getOrgList(Long orgId) {
		List<X> orgList = new ArrayList<X>();
		//orgList = entityDao.findBy(orgId);
		return orgList;
	}

	public <X> List<X> findByPageRequest(Map mapRequest) {
		return entityDao.findByPageRequest(mapRequest);
	}

}
