/**
 * 
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.ResourceInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class ResourceInfoDao extends BaseHibernateDao<ResourceInfo, Long> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return ResourceInfo.class;
	}

}
