/**
 * 
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.UserInfo;

/**
 * @author Administrator
 *
 */
public class UserInfoDao extends BaseHibernateDao<UserInfo, Long> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
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

}
