package org.pssframework.core.functional.security;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.pssframework.core.data.SecurityEntityData;
import org.pssframework.core.entity.security.Role;
import org.pssframework.core.entity.security.User;
import org.pssframework.core.functional.BaseSeleniumTestCase;
import org.springside.modules.test.DataUtils;
import org.springside.modules.test.groups.Groups;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseSeleniumTestCase {

	private static User testUser = null;

	public enum UserColumn {
		LOGIN_NAME, NAME, EMAIL, ROLES, OPERATIONS
	}

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		clickLink(USER_MENU);

		assertEquals("admin", getContentTable(1, UserColumn.LOGIN_NAME));
		assertEquals("管理员, 用户", getContentTable(1, UserColumn.ROLES));
	}

	/**
	 * 创建公共测试用户.
	 */
	@Test
	public void createUser() {
		//打开新增用户页面
		clickLink(USER_MENU);
		clickLink("增加新用户");

		//生成待输入的测试用户数据
		User user = SecurityEntityData.getRandomUserWithRole();

		//输入数据
		selenium.type("loginName", user.getLoginName());
		selenium.type("name", user.getName());
		selenium.type("password", user.getPassword());
		selenium.type("passwordConfirm", user.getPassword());
		for (Role role : user.getRoleList()) {
			selenium.check("checkedRoleIds-" + role.getId());
		}
		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		verifyUser(user);

		//设置公共测试用户
		testUser = user;
	}

	/**
	 * 修改公共测试用户.
	 */
	@Test
	public void editUser() {
		//确保已创建公共测试用户.
		ensureTestUserExist();

		//打开公共测试用户修改页面.
		clickLink(USER_MENU);
		searchUser(testUser.getLoginName());
		clickLink("修改");

		//更改用户名
		testUser.setName(DataUtils.randomName("User"));
		selenium.type("name", testUser.getName());

		//取消所有角色
		for (Role role : testUser.getRoleList()) {
			selenium.uncheck("checkedRoleIds-" + role.getId());
		}
		testUser.getRoleList().clear();

		//增加一个角色
		Role role = SecurityEntityData.getRandomDefaultRole();
		selenium.check("checkedRoleIds-" + role.getId());
		testUser.getRoleList().add(role);

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		searchUser(testUser.getLoginName());
		verifyUser(testUser);
	}

	/**
	 * 删除公共用户.
	 */
	@Test
	public void deleteUser() {
		//确保已创建公共测试用户.
		ensureTestUserExist();

		//查找公共测试用户
		clickLink(USER_MENU);
		searchUser(testUser.getLoginName());

		//删除用户
		clickLink("删除");

		//校验结果
		assertTrue(selenium.isTextPresent("删除用户成功"));
		searchUser(testUser.getLoginName());
		assertFalse(selenium.isTextPresent(testUser.getLoginName()));

		//清空公共测试用户
		testUser = null;
	}

	/**
	 * 创建用户时的输入校验测试. 
	 */
	@Test
	@Groups("extension")
	public void validateUser() {
		clickLink(USER_MENU);
		clickLink("增加新用户");

		selenium.type("loginName", "admin");
		selenium.type("name", "");
		selenium.type("password", "a");
		selenium.type("passwordConfirm", "abc");
		selenium.type("email", "abc.com");
		selenium.click(SUBMIT_BUTTON);

		selenium.waitForCondition("selenium.isTextPresent('用户登录名已存在')", "5000");
		assertTrue(selenium.isTextPresent("用户登录名已存在"));
		assertEquals("必选字段", selenium.getTable("//form[@id='inputForm']/table.1.1"));
		assertEquals("请输入一个长度最少是 3 的字符串", selenium.getTable("//form[@id='inputForm']/table.2.1"));
		assertEquals("输入与上面相同的密码", selenium.getTable("//form[@id='inputForm']/table.3.1"));
		assertEquals("请输入正确格式的电子邮件", selenium.getTable("//form[@id='inputForm']/table.4.1"));
	}

	/**
	 * 根据用户名查找用户的工具函数. 
	 */
	private void searchUser(String loginName) {
		selenium.type("filter_EQS_loginName", loginName);
		selenium.click(SEARCH_BUTTON);
		waitPageLoad();
	}

	/**
	 * 校验结果的工具函数.
	 */
	private void verifyUser(User user) {
		searchUser(user.getLoginName());
		clickLink("修改");

		assertEquals(user.getLoginName(), selenium.getValue("loginName"));
		assertEquals(user.getName(), selenium.getValue("name"));
		for (Role role : user.getRoleList()) {
			assertTrue(selenium.isChecked("checkedRoleIds-" + role.getId()));
		}

		List<Role> uncheckRoleList = ListUtils.subtract(SecurityEntityData.getDefaultRoleList(), user.getRoleList());
		for (Role role : uncheckRoleList) {
			assertFalse(selenium.isChecked("checkedRoleIds-" + role.getId()));
		}
	}

	/**
	 * 确保公共测试用户已初始化的工具函数.
	 */
	private void ensureTestUserExist() {
		if (testUser == null) {
			createUser();
		}
	}
}
