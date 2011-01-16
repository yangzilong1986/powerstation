/**
 * 
 */
package org.pssframework.dao.system;

import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.UserInfo;
import org.pssframework.query.system.UserQuery;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Repository
public class UserInfoDao extends BaseHibernateDao<UserInfo, Long> {

	private static final String STAFFNO = "staffNo";

	private static final String PAGE_USER_INFO = "select t from UserInfo t where 1=1 "
			+ "/~ and (('[showAllAccount]'= 'true' and t.orgInfo.parentOrgInfo.orgId='[orgId]') or ('[showAllAccount]'= 'false' and t.orgInfo.orgId = '[orgId]' ))~/"
			+ "/~ and t.empNo not in ([empNos]) ~/" + "/~ order by [sortColumns]~/";//" order by t.orgInfo.orgType,t.orgInfo.sortNo";

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return UserInfo.class;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUniqueLoginName(final Object newValue, final Object oldValue) {
		return isPropertyUnique(STAFFNO, newValue, oldValue);
	}

	@SuppressWarnings("rawtypes")
	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		UserQuery userQ = new UserQuery();
		if (pageRequest instanceof UserQuery) {
			userQ = (UserQuery) pageRequest;
		}
		;

		String all = "select t from UserInfo t, OrgInfo  p where 1=1 and t.orgInfo.orgNo like p.orgNo ||'%' /~ and p.orgId = [orgId] ~/"
				+ "/~ and t.empNo not in ([empNos]) ~/" + "/~ order by [sortColumns] ~/";
		//" order by t.orgInfo.orgType,t.orgInfo.sortNo";

		String not_all = "select t from UserInfo t where 1=1 /~ and t.orgInfo.orgId = [orgId] ~/"
				+ "/~ and t.empNo not in ([empNos]) ~/" + "/~ order by [sortColumns] ~/";
		//" order by t.orgInfo.orgType,t.orgInfo.sortNo";
		String QUERY_SQL = "from UserInfo";
		if (!userQ.isShowAllAccount()) {
			QUERY_SQL = not_all;
		} else {
			QUERY_SQL = all;
		}

		if (pageRequest.getSortColumns() == null) {
			pageRequest.setSortColumns("t.orgInfo.orgType,t.orgInfo.sortNo");
		}
		return pageQuery(QUERY_SQL, pageRequest);
	}

	public UserInfo findUniqueByStaffNo(String value) {
		return findUniqueBy(STAFFNO, value);
	}

}
