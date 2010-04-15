
package org.pssframework.service;


import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.UserInfoDao;
import org.pssframework.model.UserInfo;
import org.springframework.stereotype.Component;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;



/**
 * @author PSS email:PPT(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Component
public class UserInfoManager extends BaseManager<UserInfo,java.lang.Long>{

	private UserInfoDao userInfoDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性*/
	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	@Override
	public EntityDao getEntityDao() {
		return this.userInfoDao;
	}
	public Page findByPageRequest(PageRequest pr) {
		return userInfoDao.findByPageRequest(pr);
	}
	
}
