package org.pssframework.persist.dao.security;

import org.springframework.stereotype.Repository;
import org.pssframework.persist.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {
}
