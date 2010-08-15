/**
 * 
 */
package org.pssframework.dao.system;

import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.UserInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Repository
public class UserInfoDao extends BaseHibernateDao<UserInfo, Long> {

	private static final String PAGE_USER_INFO = "from UserInfo t where 1=1 "
			+ "/~ and ('[showAllAccount]'= 'true' or ('[showAllAccount]'= 'false' and t.orgInfo.orgId = '[orgId]' ))~/";

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

	@SuppressWarnings("rawtypes")
	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		return pageQuery(PAGE_USER_INFO, pageRequest);
	}

}
