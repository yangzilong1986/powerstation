/**
 * 
 */
package org.pssframework.service;

import java.io.Serializable;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.TreeInfoDao;
import org.pssframework.model.Leaf;
import org.pssframework.model.TreeInfo;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Service
public class TreeInfoManager extends BaseManager<Leaf, Serializable> {

	private TreeInfoDao treeInfoDao;

	/**
	 * @param treeInfoDao the treeInfoDao to set
	 */
	public void setTreeInfoDao(TreeInfoDao treeInfoDao) {
		this.treeInfoDao = treeInfoDao;
	}

	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeInfo getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(TreeInfo userInfo) {
		// TODO Auto-generated method stub

	}

	public void removeById(Long id) {
		// TODO Auto-generated method stub

	}

	public void save(TreeInfo treeInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return this.treeInfoDao;
	}

}
