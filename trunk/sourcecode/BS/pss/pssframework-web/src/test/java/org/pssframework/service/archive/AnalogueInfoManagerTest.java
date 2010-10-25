/**
 * 
 */
package org.pssframework.service.archive;


import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pssframework.service.archive.AnalogueInfoManager.Port;

import com.google.common.collect.Lists;

/**
 * @author djs-baocj
 *
 */
public class AnalogueInfoManagerTest {

	private static AnalogueInfoManager analogueInfoManager;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		analogueInfoManager = new AnalogueInfoManager();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1To8Ports() {
		List<Port> lst = Lists.newArrayList();
		lst = analogueInfoManager.get1To8Ports();
	}
}
