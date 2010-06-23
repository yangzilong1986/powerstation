/**
 * 
 */
package org.pssframework.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * @author Baocj
 *
 */
@ContextConfiguration(locations = { "classpath*:/spring/*-resource.xml", "classpath*:/spring/*-dao.xml" })
public class UserInfoDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void save() {

		System.out.println("1");
		// EntityManagerFactory factory =
		// Persistence.createEntityManagerFactory("itcast");
		// EntityManager em = factory.createEntityManager();
		// em.getTransaction().begin();
		//
		// MpInfo mpInfo = new MpInfo();
		// GpInfo gpInfo1 = new GpInfo();
		//
		// GpInfo gpInfo2 = new GpInfo();
		// mpInfo.getGpInfos().add(gpInfo1);
		// mpInfo.getGpInfos().add(gpInfo2);
		//
		// em.persist(mpInfo);
		// em.getTransaction().commit();
		// em.close();
		// factory.close();
	}

}
