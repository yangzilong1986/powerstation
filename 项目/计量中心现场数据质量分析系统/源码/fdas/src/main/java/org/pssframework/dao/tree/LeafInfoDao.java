/**
 * 
 */
package org.pssframework.dao.tree;

import java.io.Serializable;

import org.pssframework.dao.BaseIbatis3Dao;
import org.pssframework.model.tree.LeafInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Repository
public class LeafInfoDao extends BaseIbatis3Dao<LeafInfo, Serializable> {

	public void saveOrUpdate(final LeafInfo entity) {
		// TODO Auto-generated method stub

	}

	public Page findByPageRequest(PageRequest pageRequest) {
		this.log.info("findByPageRequest");
		return pageQuery("infos", pageRequest);
	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return "LeafInfo";
	}
}
