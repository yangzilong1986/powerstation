/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.dao.system.OrgInfoDao;
import org.pssframework.model.archive.PsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Repository
public class PsInfoDao extends BaseHibernateDao<PsInfo, Long> {
    private String hql = "select distinct t from PsInfo t, GpInfo g, TgInfo tt, OrgInfo o where 1 = 1 and g.gpId = t.gpInfo.gpId and g.objectId = tt.tgId and g.gpType = 2 and tt.orgInfo.orgNo like o.orgNo || '%'"
            + "/~ and tt.tgId = '[tgid]' ~/" + "/~ and o.orgId =  '[orgId]' ~/" + "order by t.psName";

    @SuppressWarnings("rawtypes")
    @Autowired
    private OrgInfoDao orgInfoDao;

    @SuppressWarnings("rawtypes")
    @Override
    public Class getEntityClass() {
        return PsInfo.class;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <X> List<X> findByPageRequest(Map mapRequest) {
        if(!mapRequest.containsKey(OrgInfoDao.ORG_ID)) {
            mapRequest.put(OrgInfoDao.ORG_ID, orgInfoDao.getCurUsrOrgId());
        }
        return findAll(hql, mapRequest);
    }
}
