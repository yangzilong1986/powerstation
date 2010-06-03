/**
 * 
 */
package org.pssframework.service.tree;

import java.io.Serializable;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.tree.LeafInfoDao;
import org.pssframework.model.tree.LeafInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Service
public class LeafInfoManager extends BaseManager<LeafInfo, Serializable> {
	@Autowired
	private LeafInfoDao leafInfoDao;

	@SuppressWarnings("unchecked")
	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		return leafInfoDao.findByPageRequest(pageRequest);
	}

	public LeafInfo getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(LeafInfo treeInfo) {
		// TODO Auto-generated method stub

	}

	public void removeById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(LeafInfo leafInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return this.leafInfoDao;
	}

}
