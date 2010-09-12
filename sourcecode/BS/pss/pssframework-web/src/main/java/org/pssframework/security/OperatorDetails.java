package org.pssframework.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.pssframework.model.system.OrgInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.system.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 扩展SpringSecurity的WebAuthenticationDetails类, 增加登录时间属性和角色属性.
 * 
 */
public class OperatorDetails extends User {

	@Autowired
	private static UserInfoManager userInfoManager;

	private static final long serialVersionUID = 1919464185097508773L;

	private Date loginTime;

	private List<RoleInfo> roleList;

	public OperatorDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities)
			throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public List<RoleInfo> getRoleInfoList() {
		return roleList;
	}

	public void setRoleInfoList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public static OrgInfo getUsrOrgInfo() {

		OperatorDetails user = SpringSecurityUtils.getCurrentUser();

		String staffNo = user.getUsername();

		UserInfo userLogin = userInfoManager.findUserByLoginName(staffNo);

		return userLogin.getOrgInfo();

	}
}
