package org.pssframework.functional.security;

import org.junit.Test;
import org.pssframework.functional.BaseSeleniumTestCase;
import org.pssframework.functional.security.RoleManagerTest.RoleColumn;
import org.pssframework.functional.security.UserManagerTest.UserColumn;

/**
 * 系统安全控制的功能测试, 测试主要用户故事.
 * 
 * @author calvin
 */
public class SecurityCheckingTest extends BaseSeleniumTestCase {

	/**
	 * 测试匿名用户访问系统时的行为.
	 */
	@Test
	public void checkAnonymous() {
		//访问退出登录页面,退出之前的登录
		selenium.open("/pssframework-web/j_spring_security_logout");
		assertTrue(selenium.isElementPresent(LOGIN_BUTTON));

		//访问任意页面会跳转到登录界面
		selenium.open("/pssframework-web/security/user.action");
		assertTrue(selenium.isElementPresent(LOGIN_BUTTON));
	}

	/**
	 * 访问只有用户角色的用户访问系统时的受限行为.
	 */
	@Test
	public void checkUserRole() {
		//访问退出登录页面,退出之前的登录
		selenium.open("/pssframework-web/j_spring_security_logout");
		assertTrue(selenium.isElementPresent(LOGIN_BUTTON));

		//登录普通用户
		selenium.type("j_username", "user");
		selenium.type("j_password", "user");
		selenium.click(LOGIN_BUTTON);
		waitPageLoad();

		//校验用户角色的操作单元格为空
		clickLink(USER_MENU);
		assertEquals("查看", getContentTable(1, UserColumn.OPERATIONS));

		clickLink(ROLE_MENU);
		assertEquals("查看", getContentTable(1, RoleColumn.OPERATIONS));
	}
}
