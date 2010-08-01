/**
 * 
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.RoleInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class RoleInfoDao extends BaseHibernateDao<RoleInfo, Long> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return RoleInfo.class;
	}

}