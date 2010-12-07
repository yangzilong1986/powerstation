/**
 * 
 */
package org.pssframework.service.archive;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.model.archive.TgInfo;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class TgOpInfoManager extends BaseManager<TgInfo, Long> {

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
