/**
 * 
 */
package org.pssframework.service.archive;

import static org.pssframework.support.system.SystemConst.SYSTEM_TG;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.TerminalInfoDao;
import org.pssframework.dao.archive.TgInfoDao;
import org.pssframework.model.archive.TermObjRelaInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.archive.TgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class TerminalInfoManger extends BaseManager<TerminalInfo, Long> {

	@Autowired
	private TerminalInfoDao terminalInfoDao;

	@Autowired
	private TgInfoDao tgInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return terminalInfoDao;
	}

	public List<TerminalInfo> findByPageRequest(Map mapRequest) {

		return terminalInfoDao.findByPageRequest(mapRequest);
	}

	@Override
	public void saveOrUpdate(TerminalInfo entity) throws DataAccessException {

		for (TermObjRelaInfo termObjRelaInfo : entity.getTermObjRelas()) {
			termObjRelaInfo.setTerminalInfo(entity);

			if (SYSTEM_TG.equals(termObjRelaInfo.getObjType())) {
				Long tgId = termObjRelaInfo.getObjId();
				TgInfo tgInfo = tgInfoDao.getById(tgId);
				if (tgInfo != null) {
					entity.setOrgId(tgInfo.getOrgInfo().getOrgId());
				}
			}
		}

		super.saveOrUpdate(entity);
	}
}
