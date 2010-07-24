/**
 *
 */
package org.pssframework.dao.system;

import org.pssframework.dao.BaseHibernateDao;
import org.pssframework.model.system.CodeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Repository
public class CodeInfoDao extends BaseHibernateDao<CodeInfo, Long> {

    public static final String CODECATE = "codecate";
    public static final String CODE = "code";
    String hql = "from CodeInfo t where 1 =1   /~ and t.codeCate = '[codecate]' ~/ /~ and t.code = '[code]' ~/";

    @Override
    public Class<CodeInfo> getEntityClass() {
        return CodeInfo.class;
    }

    public List<CodeInfo> findAll(Map<String, ?> filters) {

        return super.findAll(hql, filters);
    }
}
