package org.pssframework.service;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.UserInfoDaoImp;
import org.pssframework.model.UserInfoImp;
import org.springframework.stereotype.Component;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author PSS email:PPT(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Component
public class UserInfoManagerImp extends BaseManager<UserInfoImp, java.lang.Long> {

	private UserInfoDaoImp userInfoDaoImp;

	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性*/
	public void setUserInfoDaoImp(UserInfoDaoImp dao) {
		this.userInfoDaoImp = dao;
	}

	@Override
	public EntityDao getEntityDao() {
		return this.userInfoDaoImp;
	}

	public Page findByPageRequest(PageRequest pr) {
		return userInfoDaoImp.findByPageRequest(pr);
	}

}
