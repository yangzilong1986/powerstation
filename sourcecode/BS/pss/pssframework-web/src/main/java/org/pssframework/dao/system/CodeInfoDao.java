/**
 * 
 */
package org.pssframework.dao.system;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.CodeInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class CodeInfoDao extends BaseHibernateDao<CodeInfo, Long> {

	String hql = "from CodeInfo t where 1 =1   /~ and t.codeCate = '[codecate]' ~/";

	@Override
	public Class<CodeInfo> getEntityClass() {
		return CodeInfo.class;
	}

	public List<CodeInfo> findAll(Map<String, ?> filters) {

		return super.findAll(hql, filters);
	}
}
