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

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Service
public class OrgInfoManager extends BaseManager<OrgInfo, Long> {

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return this.entityDao;
	}

	@Autowired
	private OrgInfoDao entityDao;

	/**
	 * @param entityDao the entityDao to set
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 */
	public void setEntityDao(OrgInfoDao entityDao) {
		this.entityDao = entityDao;
	}

	public List getOrgList(Long orgId) {
		List orgList  = new ArrayList();
		//orgList = entityDao.findBy(orgId);
		return orgList;
	}

	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
