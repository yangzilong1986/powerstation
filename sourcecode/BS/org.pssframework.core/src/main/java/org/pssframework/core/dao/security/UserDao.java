package org.pssframework.core.dao.security;

import org.springframework.stereotype.Repository;
import org.pssframework.core.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {
}
