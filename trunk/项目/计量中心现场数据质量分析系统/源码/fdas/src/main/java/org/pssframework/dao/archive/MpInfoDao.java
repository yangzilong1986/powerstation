/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.MpInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class MpInfoDao extends BaseHibernateDao<MpInfo, Long> {

	private String hql = "from MpInfo t where 1=1  /~ and t.tgId = '[tgid]' ~/ ";

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
        return MpInfo.class;
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> findByPageRequest(Map mapRequest) {
		return findAll(hql, mapRequest);
	}

}
