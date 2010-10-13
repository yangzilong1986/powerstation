/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.SwitchValueInfo;
import org.pssframework.model.archive.SwitchValueInfoPK;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * @author djs-baocj
 *
 */
@Repository
public class SwitchValueInfoDao extends BaseHibernateDao<SwitchValueInfo, SwitchValueInfoPK> {
	private String hql = "select t from SwitchValueInfo t, TermObjRelaInfo g where 1=1  and t.switchValueId.terminalInfo.termId = g.terminalInfo.termId "
			+ "/~ and g.objType='2' and g.objId = '[tgid]' ~/" + "/~ and g.objType='1' and g.objId = '[custid]' ~/";

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return SwitchValueInfo.class;
	}

	public <X> List<X> findByPageRequest(Map mapRequest) {
		return findAll(hql, mapRequest);
	}

	public <X> List<X> findAllByPK(SwitchValueInfoPK switchValueInfoPK) {
		Map conMap = Maps.newHashMap();
		conMap.put("termId", switchValueInfoPK.getTerminalInfo().getTermId());
		conMap.put("switchNo", switchValueInfoPK.getSwitchNo());
		String sql = "select g from SwitchValueInfo g where 1=1 /~ and g.switchValueId.terminalInfo.termId='[termId]' ~/ /~ and g.switchValueId.switchNo='[switchNo]'~/";
		return findAll(sql, conMap);
	}
}
