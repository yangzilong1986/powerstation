/**
 * 
 */
package org.pssframework.service.system;

import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.ResourceInfoDao;
import org.pssframework.model.system.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<ResourceInfo> findAll() {
		return resourceInfoDao.getAll("resourceId", true);
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

}
