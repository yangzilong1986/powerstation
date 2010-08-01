/**
 * 
 */
package org.pssframework.service.system;

import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.AuthorityInfoDao;
import org.pssframework.model.system.AuthorityInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 *
 */
public class AuthorityInfoManager extends BaseManager<AuthorityInfo, Long> {

	@Autowired
	private AuthorityInfoDao authorityInfoDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return authorityInfoDao;
	}

	//-- AuthorityInfo Manager --//
	@Override
	public AuthorityInfo getById(Long id) {
		return authorityInfoDao.getById(id);
	}

	@Override
	public List<AuthorityInfo> findAll() {
		return authorityInfoDao.getAll("authorityId", true);
	}

	@Override
	public void save(AuthorityInfo entity) {
		authorityInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(AuthorityInfo entity) {
		authorityInfoDao.saveOrUpdate(entity);
	}

	@Override
	public void removeById(Long id) {
		authorityInfoDao.delete(id);
	}
}
