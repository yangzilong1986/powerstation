/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.PsInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository
public class PsInfoDao extends BaseHibernateDao<PsInfo, Long> {

    private String hql = "select t from PsInfo t,GpInfo g where 1=1  and g.gpId = t.gpInfo.gpId and g.gpType=2 /~ and g.objectId = '[tgid]' ~/";

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
        return PsInfo.class;
	}

	public <X> List<X> findByPageRequest(Map mapRequest) {
		return findAll(hql, mapRequest);
	}

}
