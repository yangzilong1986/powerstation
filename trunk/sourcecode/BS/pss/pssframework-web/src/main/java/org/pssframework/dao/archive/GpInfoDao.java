/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.GpInfo;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * @author Administrator
 *
 */
@Repository
public class GpInfoDao extends BaseHibernateDao<GpInfo, java.lang.Long> {

	private static final String gpListByTerm = "from GpInfo t where 1=1  /~and  t.terminalInfo.termId = [termId]~/";

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pssframework.dao.BaseHibernateDao#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return GpInfo.class;
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> findByPageRequest(Map mapRequest) {
		return findAll((String) mapRequest.get("HQL"), mapRequest);
	}

	@SuppressWarnings("unchecked")
	public List<GpInfo> findByTermId(Long termId) {
		Map mapRequest = Maps.newHashMap();
		mapRequest.put("termId", termId);
		return findAll(gpListByTerm, mapRequest);
	}
}
