package org.pssframework.model.integration.dao.security;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.pssframework.model.dao.security.ResourceDao;
import org.pssframework.model.entity.security.Resource;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * ResourceDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
public class ResourceDaoTest extends SpringTxTestCase {
	@Autowired
	private ResourceDao entityDao;

	@Test
	public void getUrlResourceWithAuthorities() {
		List<Resource> resourceList = entityDao.getUrlResourceWithAuthorities();
		//校验资源的总数、排序及其授权已初始化
		assertEquals(countRowsInTable("SS_RESOURCE"), resourceList.size());
		Resource resource = resourceList.get(0);
		assertEquals(1.0, resource.getPosition());
		evict(resource);
		assertTrue(resource.getAuthorityList().size() > 0);
	}
}