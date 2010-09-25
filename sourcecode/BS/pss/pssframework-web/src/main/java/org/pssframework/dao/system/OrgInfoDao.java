/**
 * 
 */
package org.pssframework.dao.system;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.security.OperatorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * @author Administrator
 *
 */
@Repository
public class OrgInfoDao<X> extends BaseHibernateDao<OrgInfo, Long> {

	public static final String ORG_ID = "orgId";

    private static final String OrgList = "select t FROM OrgInfo t,OrgInfo a WHERE 1=1   and t.orgNo LIKE  a.orgNo || '%' /~ AND a.orgId = [orgId] ~/ ORDER BY t.orgType, t.orgNo";

    private static final String AdminOrgList = "FROM OrgInfo ORDER BY orgType, orgNo";

	private OperatorDetails user;

	private UserInfo userInfo;

	@Autowired
	private UserInfoDao userInfoDao;

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
    public List<OrgInfo> findByPageRequest(Map mapRequest) {
		if (!mapRequest.containsKey(ORG_ID)) {
			if (getCurUsrOrgId() == null)
				return findAll(AdminOrgList, mapRequest);
			mapRequest.put(ORG_ID, getCurUsrOrgId());
		}
		return findAll(OrgList, mapRequest);
	}

	/**
	 * @param orgId
	 * @return OrgInfo
	 */
	public OrgInfo getOrgInfo(String orgId) {
		return (OrgInfo) findByProperty(ORG_ID, getEntityClass());
	}

	/**
	 * 获取登陆者orgId
	 * @return
	 */
	public Long getCurUsrOrgId() {
		Long orgId = null;

		user = SpringSecurityUtils.getCurrentUser();

		userInfo = userInfoDao.findUniqueByStaffNo(user.getUsername());

		OrgInfo orginfo = userInfo.getOrgInfo();

		if (userInfo.getEmpNo() == 0L)
			return null;
		if (orginfo != null) {
			orgId = orginfo.getOrgId();
		}

		return orgId;

	}

}
