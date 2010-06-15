/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.TerminalInfoDao;
import org.pssframework.model.archive.TerminalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class TerminalInfoManger extends BaseManager<TerminalInfo, Long> {

	private static final String Term = "select t from  TerminalInfo t,MpInfo m,GpInfo g where g.mpId = m.mpId and t.termId=g.termId"
			+ "/~ m.tgId = '[tgid]' ~/ " + "/~ m.lineId = '[lineid]' ~/" + "/~ m.objectId = '[objectid]' ~/";

	@Autowired
	private TerminalInfoDao terminalInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return terminalInfoDao;
	}

	@SuppressWarnings("unchecked")
	public List<TerminalInfo> findByPageRequest(Map mapRequest) {

		return terminalInfoDao.findAll(Term, mapRequest);
	}

}
