/**
 * 
 */
package org.pssframework.dao;

import java.io.Serializable;

import org.pssframework.model.Leaf;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Repository
public class TreeInfoDao extends BaseIbatis3Dao<Leaf, Serializable> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return getClass();
	}


	public void saveOrUpdate(final Leaf entity) {
		// TODO Auto-generated method stub

	}

	public Page findByPageRequest(PageRequest pageRequest) {
		this.log.info("findByPageRequest");
		return pageQuery("UserInfo.pageSelect", pageRequest);
	}

}
