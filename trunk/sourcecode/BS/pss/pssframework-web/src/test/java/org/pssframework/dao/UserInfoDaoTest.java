/**
 * 
 */
package org.pssframework.dao;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * @author Baocj
 *
 */
@ContextConfiguration(locations = { "classpath:/spring/*-resource.xml", "classpath:/spring/*-dao.xml" })
public class UserInfoDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void link() {
		// TODO Auto-generated method stub

		System.out.println("1");
	}
}
