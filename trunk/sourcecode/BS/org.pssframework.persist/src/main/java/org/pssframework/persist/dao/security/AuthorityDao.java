package org.pssframework.persist.dao.security;

import org.springframework.stereotype.Repository;
import org.pssframework.persist.entity.security.Authority;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
