package org.pssframework.service.system;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.UserInfoDao;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

//Spring Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
public class UserInfoManager extends BaseManager<UserInfo, Long> {

	private PasswordEncoder encoder = new ShaPasswordEncoder();

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

		String shaPassword = encoder.encodePassword(entity.getPasswd(), null);
		entity.setPasswd(shaPassword);
		setRole(entity);
		userInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(UserInfo entity) {
		if (isSupervisor(entity.getEmpNo())) {
			logger.warn("操作员{}尝试修改超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能修改超级管理员用户");
		}

		if (!entity.getOldPasswd().equals(entity.getPasswd())) {
			String shaPassword = encoder.encodePassword(entity.getPasswd(), null);
			entity.setPasswd(shaPassword);
		}
		
		setRole(entity);

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
		userInfoDao.deleteById(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == ADMIN_ID;
	}

	@Transactional(readOnly = true)
	public UserInfo findUserByLoginName(String loginName) {
		return userInfoDao.findUniqueByStaffNo(loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userInfoDao.isPropertyUniqueLoginName(newLoginName, oldLoginName);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return userInfoDao;
	}

	@SuppressWarnings("rawtypes")
	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		return userInfoDao.findByPageRequest(pageRequest);
	}

	/**
	 * 设置角色列表
	 * @param entity
	 */
	private void setRole(UserInfo entity) {
		List<Long> roleIds = entity.getRoleIds();

		List<RoleInfo> list = com.google.common.collect.Lists.newArrayList();

		if (roleIds != null && roleIds.size() > 0) {
			entity.getRoleInfoList().clear();

			for (Long roleId : roleIds) {

				list.add(new RoleInfo(roleId));

			}
		}

		entity.getRoleInfoList().addAll(list);

	}

	public void changePassWord(UserInfo userInfo) {

		String shaPassword = encoder.encodePassword(userInfo.getOldPasswd(), null);

		if (!shaPassword.equals(userInfo.getPasswd())) {
			logger.info("操作员{}尝试删除用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("输入的老密码不正确");
		} else {
			userInfo.setPasswd(userInfo.getNewPasswd());
		}

		this.saveOrUpdate(userInfo);
	}

	public String checkUsrRePeat(UserInfo userInfo) {
		String msg = "";
		if (isLoginNameUnique(userInfo.getStaffNo(), userInfo.getOldStaffNo())) {

		} else {
			msg = "账户名[" + userInfo.getStaffNo() + "]已存在";
		}
		return msg;
	}
}
