/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.TermObjRelaInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Repository
public class TermObjRelaInfoDao extends BaseHibernateDao<TermObjRelaInfo, Long> {

	private String hql = "select t from  TermObjRelaInfo t,TermObjRelaInfo g where g.terminalInfo.termId = t.termId "
			+ "/~ and g.objId = '[tgid]' and g.objType=2 ~/" + "/~ and g.objId = '[biguserid]' and g.objType=1 ~/"
			+ "/~ and g.objId = '[userid]' and g.objType=3 ~/" + "/~ and g.objId = '[subsd]' and g.objType=4 ~/";


    @SuppressWarnings("unchecked")
    @Override
    public Class getEntityClass() {
        // TODO Auto-generated method stub
		return TermObjRelaInfo.class;
    }

    @SuppressWarnings("unchecked")
	public <X> List<X> findTgInfo(Map mapRequest) {
		String tg = "select g from  TermObjRelaInfo t,TgInfo g where 1=1 and g.tgId = t.objId /~and t.terminalInfo.termId = '[termid]'~/ /~and g.tgId = '[tgid]'~/ and t.objType=2 ";
		return findAll(tg, mapRequest);
    }

	public List<TermObjRelaInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
