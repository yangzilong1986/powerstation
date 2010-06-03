package org.pssframework.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class AssertTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAssert() {
		String str = "test";
		Assert.notNull(str);
	}
}
