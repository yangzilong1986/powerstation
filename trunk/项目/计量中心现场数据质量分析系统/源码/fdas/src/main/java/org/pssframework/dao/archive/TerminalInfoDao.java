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

	private String hql = "select t from  TerminalInfo t,TermObjRelaInfo g where g.terminalInfo.termId = t.termId "
			+ "/~ and g.objId = '[tgid]' and g.objType=2 ~/" + "/~ and g.objId = '[biguserid]' and g.objType=1 ~/"
			+ "/~ and g.objId = '[userid]' and g.objType=3 ~/" + "/~ and g.objId = '[subsd]' and g.objType=4 ~/";


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
