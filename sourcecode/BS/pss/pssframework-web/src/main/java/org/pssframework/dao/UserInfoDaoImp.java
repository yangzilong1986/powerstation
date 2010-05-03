package org.pssframework.dao;

import java.util.Map;

import org.pssframework.model.UserInfoImp;
import org.springframework.stereotype.Component;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

@Component
public class UserInfoDaoImp extends BaseHibernateDao<UserInfoImp, java.lang.Long> {

	@Override
	public Class<?> getEntityClass() {
		return UserInfoImp.class;
	}

	@Override
	public void saveOrUpdate(UserInfoImp entity) {
		this.log.info("saveOrUpdate");
		if (entity.getUserId() == null) {
			this.log.info("save");
			save(entity);
		} else {
			this.log.info("update");
			update(entity);
		}
	}

	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		String sql = "select t from UserInfoImp t where 1=1 " + "/~ and t.username = '[username]' ~/"
				+ "/~ and t.password = '[password]' ~/" + "/~ and t.sex = '[sex]' ~/" + "/~ and t.age = '[age]' ~/"
				+ "/~ order by [sortColumns] ~/";
		return pageQuery(sql, pageRequest);
	}

	public UserInfoImp getByUserId(String userId) {
		return (UserInfoImp) findByProperty("userId", getEntityClass());
	}

	public UserInfoImp getByUserName(String v) {
		return (UserInfoImp) findByProperty("username", v);
	}

}
