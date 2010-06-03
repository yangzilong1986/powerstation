/**
 * 
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.CodeInfo;

/**
 * @author Administrator
 *
 */
public class CodeInfoDao extends BaseHibernateDao<CodeInfo, Long> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return CodeInfo.class;
	}

}
