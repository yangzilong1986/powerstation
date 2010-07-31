package org.pssframework.service.system;

import java.util.Set;

import org.pssframework.model.system.AuthorityInfo;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
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
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	private AccountManager accountManager;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		UserInfo user = accountManager.findUserByLoginName(username);
		if (user == null)
			throw new UsernameNotFoundException("用户" + username + " 不存在");

		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		boolean enabled = (user.getEnable() == 1);
		boolean accountNonExpired = (user.getAccountNonExpired() == 1);
		boolean credentialsNonExpired = (user.getCredentialsNonExpired() == 1);
		boolean accountNonLocked = (user.getAccountNonLocked() == 1);

		UserDetails userdetails = new org.springframework.security.core.userdetails.User(user.getStaffNo(),
				user.getPasswd(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);

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

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
