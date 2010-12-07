package org.pssframework.service.system;

import java.util.List;

import org.pssframework.dao.system.AuthorityInfoDao;
import org.pssframework.dao.system.ResourceInfoDao;
import org.pssframework.dao.system.RoleInfoDao;
import org.pssframework.dao.system.UserInfoDao;
import org.pssframework.model.system.AuthorityInfo;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 */
//Spring Bean的标识.
//默认将类中的所有函数纳入事务管理.
public class AccountManager {

	private static final Logger logger = LoggerFactory.getLogger(AccountManager.class);

	private static final int ADMIN_ID = 0;

	private UserInfoDao userInfoDao;
	private RoleInfoDao roleInfoDao;
	private AuthorityInfoDao authorityInfoDao;
	private ResourceInfoDao resourceInfoDao;

	@Autowired
	public void setUserDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	/**
	 * @param resourceInfoDao the resourceInfoDao to set
	 */
	@Autowired
	public void setResourceInfoDao(ResourceInfoDao resourceInfoDao) {
		this.resourceInfoDao = resourceInfoDao;
	}

	@Autowired
	public void setRoleDao(RoleInfoDao roleInfoDao) {
		this.roleInfoDao = roleInfoDao;
	}

	@Autowired
	public void setAuthorityDao(AuthorityInfoDao authorityInfoDao) {
		this.authorityInfoDao = authorityInfoDao;
	}

	//-- UserInfo Manager --//
	@Transactional(readOnly = true)
	public UserInfo getUser(Long id) {
		return userInfoDao.getById(id);
	}

	public void saveUser(UserInfo entity) {
		userInfoDao.save(entity);
	}

	public void saveOrUpdateUser(UserInfo entity) {
		userInfoDao.saveOrUpdate(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
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


	//-- AuthorityInfo Manager --//
	@Transactional(readOnly = true)
	public List<AuthorityInfo> getAllAuthority() {
		return authorityInfoDao.getAll();
	}

	//-- ResourceInfoDao Manager --//
	@Transactional(readOnly = true)
	public List<ResourceInfo> getAllResource() {
		return resourceInfoDao.getAll();
	}

	public ResourceInfo getResource(Long id) {
		return resourceInfoDao.getById(id);
	}

}
