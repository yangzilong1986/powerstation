/**
 * 
 */
package org.pssframework.service.system;

import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.RoleInfoDao;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.system.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
@Service
public class RoleInfoManager extends BaseManager<RoleInfo, Long> {

	private static final long ADMIN = 0L;

	@Autowired(required = true)
	private RoleInfoDao roleInfoDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return roleInfoDao;
	}

	//-- RoleInfo Manager --//
	@Override
	@Transactional(readOnly = true)
	public RoleInfo getById(Long id) {
		return roleInfoDao.getById(id);
	}

	@Override
	public List<RoleInfo> findAll() {
		return roleInfoDao.findAll();
	}

	public List<RoleInfo> findAllExtAdmin(Long id) {
		List<RoleInfo> roleInfos = com.google.common.collect.Lists.newLinkedList();
		roleInfos = this.findAll();
		if (roleInfos == null) {
			roleInfos = com.google.common.collect.Lists.newLinkedList();
		}
		if (ADMIN != id) {
			roleInfos.remove(0);
		}
		return roleInfos;
	}

	@Override
	public void save(RoleInfo entity) {
		roleInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(RoleInfo entity) {
		List<ResourceInfo> resourceInfoList = Lists.newLinkedList();

		String resources = entity.getResourceIds();

		String[] resouceArray = new String[] {};

		if (resources != null) {
			resouceArray = resources.split(",");
		}

		for (String resouceId : resouceArray) {

			resourceInfoList.add(new ResourceInfo(Long.parseLong(resouceId)));

		}

		entity.getResourceInfoList().clear();

		entity.setResourceInfoList(resourceInfoList);

		roleInfoDao.saveOrUpdate(entity);
	}

	@Override
	public void removeById(Long id) {
		roleInfoDao.delete(id);
	}

	@SuppressWarnings("rawtypes")
	public Page findByPageRequest(PageRequest pageRequest) {
		return roleInfoDao.findByPageRequest(pageRequest);
	}

}
