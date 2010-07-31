package org.pssframework.service.system;

import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.AuthorityInfoDao;
import org.pssframework.dao.system.RoleInfoDao;
import org.pssframework.dao.system.UserInfoDao;
import org.pssframework.model.system.AuthorityInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 */
//Spring Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager extends BaseManager<UserInfo, Long> {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	private UserInfoDao userDao;
	private RoleInfoDao roleInfoDao;
	private AuthorityInfoDao authorityDao;

	@Autowired
	public void setUserDao(UserInfoDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleInfoDao roleInfoDao) {
		this.roleInfoDao = roleInfoDao;
	}

	@Autowired
	public void setAuthorityDao(AuthorityInfoDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	//-- UserInfo Manager --//
	@Transactional(readOnly = true)
	public UserInfo getUser(Long id) {
		return userDao.getById(id);
	}

	public void saveUser(UserInfo entity) {
		userDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	@Transactional(readOnly = true)
	public UserInfo findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	//-- RoleInfo Manager --//
	@Transactional(readOnly = true)
	public RoleInfo getRole(Long id) {
		return roleInfoDao.getById(id);
	}

	@Transactional(readOnly = true)
	public List<RoleInfo> getAllRole() {
		return roleInfoDao.getAll("id", true);
	}

	public void saveRole(RoleInfo entity) {
		roleInfoDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleInfoDao.delete(id);
	}

	//-- AuthorityInfo Manager --//
	@Transactional(readOnly = true)
	public List<AuthorityInfo> getAllAuthority() {
		return authorityDao.getAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EntityDao getEntityDao() {
		return userDao;
	}
}
