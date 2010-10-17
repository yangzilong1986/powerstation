/**
 * 
 */
package org.pssframework.util;

import org.apache.commons.lang.xwork.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author djs-baocj
 *
 */
public class StringUtilsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStripStart() {

		String at = "A001";
		Assert.assertEquals("001", StringUtils.stripStart(at, "A"));

	}

	@Test
	public void testleftPad() {
		String te = "1";
		System.out.println(StringUtils.leftPad(String.valueOf(te), 3, '0'));

		Assert.assertEquals("001", StringUtils.leftPad(String.valueOf(te), 3, '0'));

		String sNo = StringUtils.stripStart("A099", "A");
		Integer iNo = Integer.parseInt(sNo) + 1;
		String tSNo = "A" + StringUtils.leftPad(String.valueOf(iNo), 3, '0');
		Assert.assertEquals("A100", tSNo);

	}


}
