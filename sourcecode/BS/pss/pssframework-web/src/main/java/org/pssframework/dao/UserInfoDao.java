package org.pssframework.dao;

import org.pssframework.model.UserInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

@Repository
public class UserInfoDao extends BaseIbatis3Dao<UserInfo, java.lang.Long> {


	public void saveOrUpdate(UserInfo entity) {
		this.log.info("saveOrUpdate");
		if (entity.getUserId() == null) {
			this.log.info("save");
			save(entity);
		} else {
			this.log.info("update");
			update(entity);
		}
	}

	public Page findByPageRequest(PageRequest pageRequest) {
		this.log.info("findByPageRequest");
		return pageQuery("pageSelect", pageRequest);
	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return "UserInfo";
	}

}