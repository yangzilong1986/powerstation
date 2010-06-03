/**
 * 
 */
package org.pssframework.dao.system;

import javax.persistence.Table;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.CodeInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
@Table(name = "a_code")
public class CodeInfoDao extends BaseHibernateDao<CodeInfo, Long> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return CodeInfo.class;
	}

}
