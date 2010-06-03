/**
 * 
 */
package org.pssframework.service.archive;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.TgInfoDao;
import org.pssframework.model.archive.TgInfo;
import org.springframework.dao.DataAccessException;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
public class TgInfoManager extends BaseManager<TgInfo, Long> {

	private TgInfoDao tgInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return this.tgInfoDao;
	}

	@Override
	public TgInfo getById(Long id) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.getById(id);
	}

	public Page findByPageRequest(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
