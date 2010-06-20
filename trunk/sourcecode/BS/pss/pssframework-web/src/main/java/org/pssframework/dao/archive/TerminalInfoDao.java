/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.TerminalInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Repository
public class TerminalInfoDao extends BaseHibernateDao<TerminalInfo, Long> {

    private String hql = "select t from  TerminalInfo t,GpInfo g where g.terminalInfo.termId = t.termId "
            + "/~ and g.objectId = '[tgid]' and g.gpType=2 ~/" + "/~ and g.objectId = '[biguserid]' and g.gpType=1 ~/"
            + "/~ and g.objectId = '[userid]' and g.gpType=3 ~/" + "/~ and g.objectId = '[subsd]' and g.gpType=4 ~/";

    @SuppressWarnings("unchecked")
    @Override
    public Class getEntityClass() {
        // TODO Auto-generated method stub
        return TerminalInfo.class;
    }

    @SuppressWarnings("unchecked")
    public <X> List<X> findByPageRequest(Map mapRequest) {
        return findAll(hql, mapRequest);
    }

}
