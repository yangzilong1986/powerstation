/**
 * 
 */
package org.pssframework.service.system;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.ResourceInfoDao;
import org.pssframework.model.system.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
@Service
public class ResourceInfoManager extends BaseManager<ResourceInfo, Long> {

	@Autowired
	private ResourceInfoDao resourceInfoDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return resourceInfoDao;
	}

	//-- ResourceInfo Manager --//
	@Override
	public ResourceInfo getById(Long id) {
		return resourceInfoDao.getById(id);
	}

	@Override
	public void save(ResourceInfo entity) {
		resourceInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(ResourceInfo entity) {
		resourceInfoDao.saveOrUpdate(entity);
	}

	@Override
	public void removeById(Long id) {
		resourceInfoDao.delete(id);
	}

	@SuppressWarnings("rawtypes")
	public List<ResourceInfo> findByPageRequest(Map mapRequest) {

		List<ResourceInfo> list = Lists.newLinkedList();
		list = resourceInfoDao.findByPageRequest(mapRequest);
		if (list == null || list.size() == 0) {
			list = Lists.newLinkedList();
		}
		return list;

	}

	public List<ResourceInfo> findAllValid() {
		return resourceInfoDao.findAllValid();

	}

}
