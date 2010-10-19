package org.pssframework.util;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

public class SpringAssert {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void assertNotNull() {
		String s = "12";
		Assert.notNull(s, "s bunrng wei null");

	}

	@Test
	public void assertNotEmpty() {
		String s = "12";
		List lst = Lists.newArrayList();
		//lst.add(s);
		Assert.notEmpty(lst, "lst is not empty");

	}
}
