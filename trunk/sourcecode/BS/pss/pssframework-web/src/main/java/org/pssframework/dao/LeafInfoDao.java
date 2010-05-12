/**
 * 
 */
package org.pssframework.dao;

import java.io.Serializable;

import org.pssframework.model.LeafInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Repository
public class LeafInfoDao extends BaseIbatis3Dao<LeafInfo, Serializable> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return getClass();
	}


	public void saveOrUpdate(final LeafInfo entity) {
		// TODO Auto-generated method stub

	}

	public Page findByPageRequest(PageRequest pageRequest) {
		this.log.info("findByPageRequest");
		return pageQuery("Leaf.infos", pageRequest);
	}
}
