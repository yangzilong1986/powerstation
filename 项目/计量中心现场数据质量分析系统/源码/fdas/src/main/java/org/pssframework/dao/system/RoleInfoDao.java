/**
 * 
 */
package org.pssframework.dao.system;

import java.util.List;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.RoleInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.google.common.collect.Lists;

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

	@Override
	public List<RoleInfo> findAll() {
		List<RoleInfo> roleInfos = Lists.newLinkedList();
		roleInfos = getAll("roleId", true);
		return roleInfos;
	}

}
