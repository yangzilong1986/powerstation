package org.pssframework.service.system;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.UserInfoDao;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

//Spring Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserInfoManager extends BaseManager<UserInfo, Long> {

	private static final int ADMIN_ID = 0;

	private UserInfoDao userInfoDao;

	@Autowired
	public void setUserDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	//-- UserInfo Manager --//
	@Override
	public UserInfo getById(Long id) {
		return userInfoDao.getById(id);
	}

	@Override
	public void save(UserInfo entity) {
		userInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(UserInfo entity) {
		userInfoDao.saveOrUpdate(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	@Override
	public void removeById(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userInfoDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == ADMIN_ID;
	}

	@Transactional(readOnly = true)
	public UserInfo findUserByLoginName(String loginName) {
		return userInfoDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userInfoDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return userInfoDao;
	}

}
