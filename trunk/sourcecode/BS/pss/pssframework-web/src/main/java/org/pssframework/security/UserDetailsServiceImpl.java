package org.pssframework.security;

import java.util.Set;

import org.pssframework.model.system.AuthorityInfo;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.system.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserInfoManager userInfoManager;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		UserInfo user = userInfoManager.findUserByLoginName(username);
		if (user == null)
			throw new UsernameNotFoundException("用户" + username + " 不存在");

		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		boolean enabled = (user.getEnable() == 1);

		//简单实现
		boolean accountNonExpired = true;//(user.getAccountNonExpired() == 1);
		boolean credentialsNonExpired = true;//(user.getCredentialsNonExpired() == 1);
		boolean accountNonLocked = true;//(user.getAccountNonLocked() == 1);

		OperatorDetails userdetails = new OperatorDetails(user.getStaffNo(), user.getPasswd(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);

		return userdetails;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(UserInfo user) {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
		for (RoleInfo role : user.getRoleInfoList()) {
			for (AuthorityInfo authority : role.getAuthorityInfoList()) {
				authSet.add(new GrantedAuthorityImpl(authority.getPrefixedId()));
			}

			for (ResourceInfo resource : role.getResourceInfoList()) {
				authSet.add(new GrantedAuthorityImpl(resource.getPrefixedId()));
			}
		}

		return authSet;
	}

}
