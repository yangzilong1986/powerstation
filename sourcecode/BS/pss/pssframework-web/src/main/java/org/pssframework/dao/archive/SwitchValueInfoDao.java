/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.SwitchValueInfo;
import org.springframework.stereotype.Repository;

/**
 * @author djs-baocj
 *
 */
@Repository
public class SwitchValueInfoDao extends BaseHibernateDao<SwitchValueInfo, Long> {
	private String hql = "select t from SwitchValueInfo t, TermObjRelaInfo g where 1=1  and t.switchValueId.termId = g.terminalInfo.termId "
			+ "/~ and g.objType='2' and g.objId = '[tgid]' ~/" + "/~ and g.objType='1' and g.objId = '[custid]' ~/";

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return SwitchValueInfo.class;
	}

	public <X> List<X> findByPageRequest(Map mapRequest) {
		return findAll(hql, mapRequest);
	}


}
