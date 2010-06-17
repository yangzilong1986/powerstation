/**
 * 
 */
package org.pssframework.service.archive;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.GpInfoDao;
import org.pssframework.model.archive.GpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class GpInfoManger extends BaseManager<GpInfo, Long> {

	@Autowired
	GpInfoDao gpInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
