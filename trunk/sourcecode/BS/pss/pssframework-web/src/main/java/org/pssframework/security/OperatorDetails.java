package org.pssframework.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.pssframework.model.system.RoleInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 扩展SpringSecurity的WebAuthenticationDetails类, 增加登录时间属性和角色属性.
 * 
 * @author calvin
 */
public class OperatorDetails extends User {
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
}
