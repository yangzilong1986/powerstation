/**
 * 
 */
package org.pssframework.service.system;

import java.util.ArrayList;
import java.util.LinkedList;
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

    @SuppressWarnings("unchecked")
    @Override
	protected EntityDao getEntityDao() {
		return this.entityDao;
	}

	@Autowired
	private OrgInfoDao<?> entityDao;

	public List<OrgInfo> getOrgList(Long orgId) {
		List<OrgInfo> orgList = new ArrayList<OrgInfo>();
		// orgList = entityDao.findBy(orgId);
		return orgList;
	}

    @SuppressWarnings("unchecked")
    public List<OrgInfo> findByPageRequest(Map mapRequest) {

		List<OrgInfo> list = new LinkedList<OrgInfo>();
		list = entityDao.findByPageRequest(mapRequest);
		if (list == null || list.size() == 0) {
			list = new LinkedList<OrgInfo>();
		}
		return list;

	}

}
