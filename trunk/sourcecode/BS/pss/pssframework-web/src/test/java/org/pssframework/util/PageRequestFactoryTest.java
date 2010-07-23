/**
 * 
 */
package org.pssframework.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class PageRequestFactoryTest {

	static BeanUtilsBean beanUtils = new BeanUtilsBean();
	org.pssframework.query.tree.LeafQuery pageRequest = new org.pssframework.query.tree.LeafQuery();
	static Map<String, Object> params = new TreeMap<String, Object>();
	static {
		params.put("parentId", "1");
		params.put("parentType", "asdasd");
	}

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
	public void testCopy() throws IllegalAccessException, InvocationTargetException {

		beanUtils.copyProperties(pageRequest, params);

		assertEquals(pageRequest.getParentId(), "1");
	}
}
