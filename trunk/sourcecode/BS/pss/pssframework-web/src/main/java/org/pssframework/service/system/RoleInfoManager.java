/**
 * 
 */
package org.pssframework.service.system;

import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.RoleInfoDao;
import org.pssframework.model.system.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 *
 */
@Service
public class RoleInfoManager extends BaseManager<RoleInfo, Long> {

	@Autowired(required = true)
	private RoleInfoDao roleInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
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
		return roleInfoDao.getAll("roleId", true);
	}

	@Override
	public void save(RoleInfo entity) {
		roleInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(RoleInfo entity) {
		roleInfoDao.saveOrUpdate(entity);
	}

	@Override
	public void removeById(Long id) {
		roleInfoDao.delete(id);
	}

}