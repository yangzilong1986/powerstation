/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.archive.TgInfo;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
public class TgInfoDao extends BaseHibernateDao<TgInfo, java.lang.Long> {

	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return TgInfo.class;
	}

	@Override
	public void saveOrUpdate(TgInfo entity) {
		this.log.info("saveOrUpdate");
		if (entity.getTgId() == null) {
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

	public TgInfo getByCust(String cust, Object val) {
		return (TgInfo) findByProperty(cust, val);
	}

	public TgInfo getByTgId(Long id) {
		return getById(id);
	}

}