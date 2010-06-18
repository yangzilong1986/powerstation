/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.TranInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class TranInfoDao extends BaseHibernateDao<TranInfo, Long> {

	private String hql = "from TranInfo t where 1=1 /~  and t.tgInfo.tgId = '[tgid]' ~/ ";

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return TranInfo.class;
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> findByPageRequest(Map mapRequest) {
		return findAll(hql, mapRequest);
	}

}
