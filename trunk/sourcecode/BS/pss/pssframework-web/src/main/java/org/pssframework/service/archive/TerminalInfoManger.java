/**
 * 
 */
package org.pssframework.service.archive;

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

	@Autowired
	private TerminalInfoDao terminalInfo;
	
	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return terminalInfo;
	}

}
