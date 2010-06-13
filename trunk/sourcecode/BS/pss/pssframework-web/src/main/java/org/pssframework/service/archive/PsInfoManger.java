/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.model.archive.PsInfo;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class PsInfoManger extends BaseManager<PsInfo, Long> {

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PsInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
