/**
 * 
 */
package org.pssframework.dao.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.dao.system.OrgInfoDao;
import org.pssframework.model.archive.TgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 * 
 */
@Repository
public class TgInfoDao extends BaseHibernateDao<TgInfo, java.lang.Long> {
    public static final String TG_ID = "tgId";

    private static final String hql = "select distinct t from TgInfo t, OrgInfo o where 1 = 1 and t.orgInfo.orgNo like o.orgNo || '%'"
            + "/~ and t.tgId = '[tgId]' ~/" + "/~ and o.orgId =  '[orgId]' ~/" + "order by t.tgName";

    @SuppressWarnings("rawtypes")
    @Autowired
    private OrgInfoDao orgInfoDao;

    @Override
    public Class<?> getEntityClass() {
        return TgInfo.class;
    }

    @Override
    public void saveOrUpdate(TgInfo entity) {
        this.log.info("saveOrUpdate");
        if(entity.getTgId() == null) {
            this.log.info("save");
            save(entity);
        }
        else {
            this.log.info("update");
            update(entity);
        }
    }

    @SuppressWarnings("rawtypes")
    public Page findByPageRequest(PageRequest<Map> pageRequest) {
        String sql = "select t from UserInfoImp t where 1 = 1 " + "/~ and t.username = '[username]' ~/"
                + "/~ and t.password = '[password]' ~/" + "/~ and t.sex = '[sex]' ~/" + "/~ and t.age = '[age]' ~/"
                + "/~ order by [sortColumns] ~/";
        return pageQuery(sql, pageRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <X> List<X> findByPageRequest(Map pageRequest) {
        if(!pageRequest.containsKey(OrgInfoDao.ORG_ID)) {
            pageRequest.put(OrgInfoDao.ORG_ID, orgInfoDao.getCurUsrOrgId());
        }
        return findAll(hql, pageRequest);
    }

    public TgInfo getByCust(String cust, Object val) {
        return (TgInfo) findByProperty(cust, val);
    }

    public TgInfo getByTgId(Long id) {
        return getById(id);
    }
}