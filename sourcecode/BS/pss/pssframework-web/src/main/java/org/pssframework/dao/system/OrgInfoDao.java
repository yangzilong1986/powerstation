/**
 * 
 */
package org.pssframework.dao.system;

import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.OrgInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Repository
public class OrgInfoDao extends BaseHibernateDao<OrgInfo, Long> {

	@Override
	public Class<?> getEntityClass() {
		return OrgInfo.class;
	}

	@Override
	public void saveOrUpdate(OrgInfo entity) {
		this.log.info("saveOrUpdate");
		if (entity.getOrgId() == null) {
			this.log.info("save");
			save(entity);
		} else {
			this.log.info("update");
			update(entity);
		}
	}

	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		String sql = "select t from OrgInfo t where 1=1 " + "/~ and t.orgno like '[orgno]' ~/";
		return pageQuery(sql, pageRequest);
	}

	/**
	 * @param orgId
	 * @return OrgInfo
	 */
	public OrgInfo getOrgInfo(String orgId) {
		return (OrgInfo) findByProperty("orgId", getEntityClass());
	}

	public OrgInfo getByUserName(String v) {
		return (OrgInfo) findByProperty("username", v);
	}

}
