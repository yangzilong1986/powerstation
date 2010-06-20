/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.ConsInfo;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Repository
public class ConsInfoDao extends BaseHibernateDao<ConsInfo, java.lang.Long> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
        return ConsInfo.class;
	}

	@Override
    public void saveOrUpdate(ConsInfo entity) {
		this.log.info("saveOrUpdate");

	}

	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		String sql = "select t from UserInfoImp t where 1=1 " + "/~ and t.username = '[username]' ~/"
				+ "/~ and t.password = '[password]' ~/" + "/~ and t.sex = '[sex]' ~/" + "/~ and t.age = '[age]' ~/"
				+ "/~ order by [sortColumns] ~/";
		return pageQuery(sql, pageRequest);
	}

    public ConsInfo getByCust(String cust, Object val) {
        return (ConsInfo) findByProperty(cust, val);
	}

    public ConsInfo getByTgId(Long id) {
		return getById(id);
	}

}