/**
 * 
 */
package org.pssframework.dao.system;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.OrgInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class OrgInfoDao extends BaseHibernateDao<OrgInfo, Long> {

	private static final String OrgList = " FROM OrgInfo t WHERE 1=1  /~ and t.orgNo LIKE (SELECT a.orgNo FROM OrgInfo a WHERE a.orgId='[orgid]') ~/ ORDER BY t.orgNo";

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

	public <X> List<X> findByPageRequest(Map<String, ?> mapRequest) {
		return findAll(OrgList, mapRequest);
	}

	/**
	 * @param orgId
	 * @return OrgInfo
	 */
	public OrgInfo getOrgInfo(String orgId) {
		return (OrgInfo) findByProperty("orgId", getEntityClass());
	}

}
