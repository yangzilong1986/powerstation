/**
 * 
 */
package org.pssframework.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.ResourceInfo;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
@Repository
public class ResourceInfoDao extends BaseHibernateDao<ResourceInfo, Long> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return ResourceInfo.class;
	}

	@SuppressWarnings("rawtypes")
	public List<ResourceInfo> findByPageRequest(Map mapRequest) {
		List<ResourceInfo> list = Lists.newLinkedList();

		return list;
	}
	
	private static final String validfunHql = "from ResourceInfo t where t.valid = 1";

	public List<ResourceInfo> findAllValid() {
		return this.findAll(validfunHql, new HashMap());
	}

}
