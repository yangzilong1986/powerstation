/**
 * 
 */
package org.pssframework.service.archive;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.TermObjRelaInfoDao;
import org.pssframework.model.archive.TermObjRelaInfo;
import org.pssframework.model.archive.TgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Administrator
 * 
 */
@Service
public class TermObjRelaInfoManager extends BaseManager<TermObjRelaInfo, Long> {

	@Autowired
	private TermObjRelaInfoDao termObjRelaInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		return this.termObjRelaInfoDao;
	}

	@Override
	public TermObjRelaInfo getById(Long id) {
		Assert.notNull(id, "Id can't null");
		return termObjRelaInfoDao.getById(id);
	}

	public List<TgInfo> findTgInfo(Map mapRequest) {
		List<TgInfo> list = new LinkedList<TgInfo>();
		list = termObjRelaInfoDao.findTgInfo(mapRequest);
		if (list == null || list.size() == 0) {
			list = new LinkedList<TgInfo>();
		}
		return list;
	}

}
