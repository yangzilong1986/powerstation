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

	private static final String OrgList = "SELECT t.orgid,t.orgname FROM orgInfo t WHERE t.orgno LIKE (SELECT a.orgno FROM orgInfo a WHERE a.orgid =:orgid) || '%' ORDER BY t.orgno";

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

	@SuppressWarnings("unchecked")
	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		return pageQuery(OrgList, pageRequest);
	}

	/**
	 * @param orgId
	 * @return OrgInfo
	 */
	public OrgInfo getOrgInfo(String orgId) {
		return (OrgInfo) findByProperty("orgId", getEntityClass());
	}

}
