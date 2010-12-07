package org.pssframework.util;


import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordEncoderTest {
	private PasswordEncoder encoder = new ShaPasswordEncoder();
	private String ps = "QL@admin123";


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void encoder() {
		//admin = d033e22ae348aeb5660fc2140aec35850c4da997
		//QL@admin123
		String s = "d033e22ae348aeb5660fc2140aec35850c4da997";
		System.out.println(encoder.encodePassword(ps, null));
	}

	@Test
	public void leftPad() {
		String sad = "123";
		sad = StringUtils.leftPad(sad, 12, '0');
		System.out.println(sad);
	}

}
