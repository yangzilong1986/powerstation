package org.pssframework.dao;

import org.pssframework.model.UserInfo;
import org.springframework.stereotype.Component;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

@SuppressWarnings("unchecked")
@Component
public class UserInfoDao extends BaseIbatis3Dao<UserInfo,java.lang.Long>{

	@Override
	public Class<?> getEntityClass() {
		return UserInfo.class;
	}
	
	public void saveOrUpdate(UserInfo entity) {
		if(entity.getUserId() == null) {
			save(entity);
		} else {
			update(entity);
		}
	}
	
	public Page findByPageRequest(PageRequest pageRequest) {
		return pageQuery("UserInfo.pageSelect",pageRequest);
	}
	

}
