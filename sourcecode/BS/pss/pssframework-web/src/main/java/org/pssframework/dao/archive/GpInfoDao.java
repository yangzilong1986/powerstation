/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.GpInfo;

/**
 * @author Administrator
 *
 */
public class GpInfoDao extends BaseHibernateDao<GpInfo, java.lang.Long> {

	private static final String hql = "";

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
		return findAll(hql, mapRequest);
	}
}
