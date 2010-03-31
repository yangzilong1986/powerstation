package org.pssframework.persist.functional.security;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.pssframework.persist.data.SecurityEntityData;
import org.pssframework.persist.entity.security.Authority;
import org.pssframework.persist.entity.security.Role;
import org.pssframework.persist.functional.BaseSeleniumTestCase;
import org.springside.modules.test.DataUtils;

/**
 * 角色管理的功能测试,测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class RoleManagerTest extends BaseSeleniumTestCase {

	private static Role testRole = null;

	public enum RoleColumn {
		NAME, AUTHORITIES, OPERATIONS
	}

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		clickLink(ROLE_MENU);

		assertEquals("管理员", getContentTable(1, RoleColumn.NAME));
		assertEquals("浏览用户, 修改用户, 浏览角色, 修改角色", selenium.getTable("contentTable.1." + RoleColumn.AUTHORITIES.ordinal()));
	}

	/**
	 * 创建公共测试角色.
	 */
	@Test
	public void createRole() {
		clickLink(ROLE_MENU);
		clickLink("增加新角色");

		//生成测试数据
		Role role = SecurityEntityData.getRandomRoleWithAuthority();

		//输入数据
		selenium.type("name", role.getName());
		for (Authority authority : role.getAuthorityList()) {
			selenium.check("checkedAuthIds-" + authority.getId());
		}
		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存角色成功"));
		verifyRole(role);

		//设置公共测试角色
		testRole = role;
	}

	/**
	 * 修改公共测试角色.
	 */
	@Test
	public void editRole() {
		ensureTestRoleExist();
		clickLink(ROLE_MENU);

		selenium.click("editLink-" + testRole.getName());
		waitPageLoad();

		testRole.setName(DataUtils.randomName("Role"));
		selenium.type("name", testRole.getName());

		for (Authority authority : testRole.getAuthorityList()) {
			selenium.uncheck("checkedAuthIds-" + authority.getId());
		}
		testRole.getAuthorityList().clear();

		List<Authority> authorityList = SecurityEntityData.getRandomDefaultAuthorityList();
		for (Authority authority : authorityList) {
			selenium.check("checkedAuthIds-" + authority.getId());
		}
		testRole.getAuthorityList().addAll(authorityList);

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		verifyRole(testRole);
	}

	/**
	 * 根据测试角色.
	 */
	@Test
	public void deleteRole() {
		ensureTestRoleExist();
		clickLink(ROLE_MENU);

		selenium.click("deleteLink-" + testRole.getName());
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除角色成功"));
		assertFalse(selenium.isTextPresent(testRole.getName()));

		testRole = null;
	}

	private void verifyRole(Role role) {
		selenium.click("editLink-" + role.getName());
		waitPageLoad();

		assertEquals(role.getName(), selenium.getValue("name"));

		for (Authority authority : role.getAuthorityList()) {
			assertTrue(selenium.isChecked("checkedAuthIds-" + authority.getId()));
		}

		List<Authority> uncheckAuthList = ListUtils.subtract(SecurityEntityData.getDefaultAuthorityList(), role
				.getAuthorityList());
		for (Authority authority : uncheckAuthList) {
			assertFalse(selenium.isChecked("checkedAuthIds-" + authority.getId()));
		}
	}

	/**
	 * 确保公共测试角色已初始化的工具函数.
	 */
	private void ensureTestRoleExist() {
		if (testRole == null) {
			createRole();
		}
	}
}
