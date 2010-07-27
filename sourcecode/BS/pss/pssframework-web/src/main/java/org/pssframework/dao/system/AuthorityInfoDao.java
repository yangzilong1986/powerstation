/**
 * 
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.AuthorityInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class AuthorityInfoDao extends BaseHibernateDao<AuthorityInfo, Long> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
