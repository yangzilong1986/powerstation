/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.MeterInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Repository
public class MeterInfoDao extends BaseHibernateDao<MeterInfo, Long> {

	private String hql = "select m from MeterInfo m,MpInfo p where 1=1 and p.meterInfo.meterId = m.meterId /~ and p.tgId = '[tgid]' ~/ "
            + "/~ and p.tgId = '[tgid]' ~/ " + "/~ and p.lineId = '[lineid]' ~/ " + "/~ and p.orgId = '[orgid]' ~/ "

    ;

    @Override
    public Class getEntityClass() {
        // TODO Auto-generated method stub
        return MeterInfo.class;
    }

    @SuppressWarnings("unchecked")
    public <X> List<X> findByPageRequest(Map mapRequest) {
        return findAll(hql, mapRequest);
    }

}
