package org.pssframework.controller.mobile;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.AuthorityInfo;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.security.OperatorDetails;
import org.pssframework.service.system.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import com.google.common.collect.Sets;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/mobile")
public class MobileLoginController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/login";
    private static final String VIEW_MAIN_NAME = "/mobile/main";
    private static final String MESSAGE = "message";

    @Autowired
    private UserInfoManager userInfoManager;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping
    public ModelAndView index(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME);
        return mav;
    }

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        UserInfo user = userInfoManager.findUserByLoginName(username);
        if(user == null) {
            mav.setViewName(VIEW_NAME);
            mav.addObject(MESSAGE, "用户名[" + username + "]不存在!");
            return mav;
        }

        PasswordEncoder encoder = new ShaPasswordEncoder();
        String shaPassword = encoder.encodePassword(password, null);
        if(shaPassword == null || !shaPassword.equals(user.getPasswd())) {
            mav.setViewName(VIEW_NAME);
            mav.addObject(MESSAGE, "密码错误!");
            return mav;
        }

        Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

        boolean enabled = (user.getEnable() == 1);

        // 简单实现
        boolean accountNonExpired = true;// (user.getAccountNonExpired() == 1);
        boolean credentialsNonExpired = true;// (user.getCredentialsNonExpired() == 1);
        boolean accountNonLocked = true;// (user.getAccountNonLocked() == 1);

        OperatorDetails userDetails = new OperatorDetails(user.getStaffNo(), user.getPasswd(), enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
        // 加入登录时间信息和用户角色
        userDetails.setLoginTime(new Date());
        userDetails.setRoleInfoList(user.getRoleInfoList());

        SpringSecurityUtils.saveUserDetailsToContext(userDetails, (HttpServletRequest) request);

        mav.setViewName(VIEW_MAIN_NAME);
        return mav;
    }

    /**
     * 获得用户所有角色的权限集合.
     */
    private Set<GrantedAuthority> obtainGrantedAuthorities(UserInfo user) {
        Set<GrantedAuthority> authSet = Sets.newHashSet();
        for(RoleInfo role : user.getRoleInfoList()) {
            for(AuthorityInfo authority : role.getAuthorityInfoList()) {
                authSet.add(new GrantedAuthorityImpl(authority.getPrefixedId()));
            }

            for(ResourceInfo resource : role.getResourceInfoList()) {
                authSet.add(new GrantedAuthorityImpl(resource.getPrefixedId()));
            }
        }

        return authSet;
    }
}
