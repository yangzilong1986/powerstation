/**
 * 
 */
package org.pssframework.dao.system;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.security.OperatorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import com.google.common.collect.Maps;

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

		//admin的场合
		if (userInfo.getEmpNo() == 0L)
			return null;
		if (orginfo != null) {
			orgId = orginfo.getOrgId();
		}

		return orgId;

	}

	public String findPurposeOrgNo(OrgInfo pOrgInfo) {
		String sql = "select  max(orgNo) from OrgInfo t where t.parentOrgInfo.orgId=:orgId";
		Map mapIn = Maps.newHashMap();
		mapIn.put("orgId", pOrgInfo.getOrgId());
		Query query = createQuery(sql, mapIn);
		return (String) query.uniqueResult();
	}

	public List<CodeInfo> findOrgTypes(Map mapRequest) {
		String hql = "from CodeInfo t where 1 =1   /~ and t.codeCate = '[codecate]' ~/ /~ and t.code > '[code]' ~/ ";
		return findAll(hql, mapRequest);
	}

	public List<OrgInfo> findSortNoRePeat(Long orgId, Long sortNo) {
		String hql = "from OrgInfo t where 1 =1   /~ and t.parentOrgInfo.orgId = '[orgId]' ~/ /~ and t.sortNo = '[sortNo]' ~/ ";
		Map mapIn = Maps.newHashMap();
		mapIn.put("orgId", orgId);
		mapIn.put("sortNo", sortNo);
		return findAll(hql, mapIn);
	}

}
