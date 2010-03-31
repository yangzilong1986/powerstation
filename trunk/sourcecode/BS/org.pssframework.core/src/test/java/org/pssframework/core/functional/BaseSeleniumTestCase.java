package org.pssframework.core.functional;

import org.junit.BeforeClass;
import org.springside.modules.test.selenium.SeleniumTestCase;

/**
 * 项目相关的SeleniumTestCase基类,定义服务器地址,浏览器类型及登陆函数. 
 * 
 * @author calvin
 */
public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	public static final String LOGIN_BUTTON = "//input[@value='登录']";
	public static final String SUBMIT_BUTTON = "//input[@value='提交']";
	public static final String SEARCH_BUTTON = "//input[@value='搜索']";

	public static final String USER_MENU = "帐号列表";
	public static final String ROLE_MENU = "角色列表";

	/**
	 * 登录管理员角色.
	 */
	@BeforeClass
	public static void loginAsAdmin() {
		selenium.open("/org.pssframework.core/login.action");
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		selenium.click(LOGIN_BUTTON);
		waitPageLoad();
		assertTrue(selenium.isTextPresent("你好, admin."));
	}

	/**
	 * 点击链接.
	 */
	protected void clickLink(String linkText) {
		selenium.click("link=" + linkText);
		waitPageLoad();
	}

	/**
	 * 从Overview页面的contentTable中取出单元格内容.
	 * 
	 * @column 以Enum定义列名.
	 */
	@SuppressWarnings("unchecked")
	protected static String getContentTable(int rowIndex, Enum column) {
		return selenium.getTable("contentTable." + rowIndex + "." + column.ordinal());
	}

}
