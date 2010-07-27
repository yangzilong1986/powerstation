/**
 * 
 */
package org.pssframework.service.archive;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.TgInfoDao;
import org.pssframework.model.archive.TgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * 
 */
@Service
public class TgInfoManager extends BaseManager<TgInfo, Long> {

	@Autowired
	private TgInfoDao tgInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		return this.tgInfoDao;
	}

	@Override
	public TgInfo getById(Long id) {
		return tgInfoDao.getById(id);
	}

	public List<TgInfo> findByPageRequest(Map mapRequest) {
		List<TgInfo> list = new LinkedList<TgInfo>();
		list = tgInfoDao.findByPageRequest(mapRequest);
		if (list == null || list.size() == 0) {
			list = new LinkedList<TgInfo>();
		}
		return list;
	}

}