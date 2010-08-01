/**
 * 
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.RoleInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Repository
public class RoleInfoDao extends BaseHibernateDao<RoleInfo, Long> {

	private static final String pageRoleHql = "from RoleInfo t";

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return RoleInfo.class;
	}

	public Page findByPageRequest(PageRequest pageRequest) {
		this.log.info("findByPageRequest");
		return pageQuery(pageRoleHql, pageRequest);
	}

}
