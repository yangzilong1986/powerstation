package org.pssframework.core.service.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.pssframework.core.entity.security.Authority;
import org.pssframework.core.entity.security.Role;
import org.pssframework.core.entity.security.User;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SecurityEntityManager securityEntityManager;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		User user = securityEntityManager.findUserByLoginName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + userName + " 不存在");
		}

		GrantedAuthority[] grantedAuths = obtainGrantedAuthorities(user);

		//-- org.pssframework.core示例中无以下属性, 暂时全部设为true. --//
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				user.getLoginName(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantedAuths);

		return userdetail;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private GrantedAuthority[] obtainGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoleList()) {
			for (Authority authority : role.getAuthorityList()) {
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
