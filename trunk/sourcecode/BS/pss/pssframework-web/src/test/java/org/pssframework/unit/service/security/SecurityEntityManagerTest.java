package org.pssframework.unit.service.security;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pssframework.dao.security.UserDao;
import org.pssframework.service.ServiceException;
import org.pssframework.service.security.SecurityEntityManager;
import org.springside.modules.utils.ReflectionUtils;

/**
 * SecurityEntityManager的集成测试用例,测试Service层的业务逻辑.
 * 
 * 调用实际的DAO类进行操作,亦可使用MockDAO对象将本用例改为单元测试.
 * 
 * @author calvin
 */
public class SecurityEntityManagerTest extends Assert {

	private SecurityEntityManager securityEntityManager;
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		securityEntityManager = new SecurityEntityManager();
		mockUserDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(securityEntityManager, "userDao", mockUserDao);
	}

	@After
	public void tearDown() {
		EasyMock.verify(mockUserDao);
	}

	@Test
	public void deleteUser() {
		mockUserDao.delete(2L);
		EasyMock.replay(mockUserDao);
		//正常删除用户.
		securityEntityManager.deleteUser(2L);
		//删除超级管理用户抛出异常.
		try {
			securityEntityManager.deleteUser(1L);
			fail("expected ServicExcepton not be thrown");
		} catch (ServiceException e) {
			//expected exception
		}
	}
}
