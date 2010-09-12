/**
 * 
 */
package org.pssframework.controller.system;

import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.base.BaseQuery;
import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.system.UserInfo;
import org.pssframework.query.system.UserQuery;
import org.pssframework.security.OperatorDetails;
import org.pssframework.service.system.CodeInfoManager;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.service.system.RoleInfoManager;
import org.pssframework.service.system.UserInfoManager;
import org.pssframework.support.system.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.google.common.collect.Maps;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/user")
public class UserInfoController extends BaseRestSpringController<UserInfo, Long> {
	private static final String VIEW_MANAGER = "/system/userManagerFrame";
	private static final String VIEW_QUERY = "/system/userList";
	private static final String VIEW_DETAIL = "/system/userDetail";
	private static final String VIEW_EDIT = "/system/editUserPage";

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	@Autowired
	private UserInfoManager userInfoManager;

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@RequestMapping(value = "/manager")
	public ModelAndView manager(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName(VIEW_MANAGER);
		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public ModelAndView userList(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			UserInfo userInfo) {

		UserQuery baseQuery = new UserQuery();
		//是否勾选全部
		if (!userInfo.isShowAllAccount()) {
			Long orgId = null;

			OrgInfo orgInfo = userInfo.getOrgInfo();

			if (orgInfo == null) {

				OperatorDetails user = SpringSecurityUtils.getCurrentUser();

				String staffNo = user.getUsername();

				UserInfo userLogin = this.userInfoManager.findUserByLoginName(staffNo);

				orgInfo = userLogin.getOrgInfo();

				modelAndView.addObject("userInfo", userLogin);

			}

			orgId = orgInfo.getOrgId();

			baseQuery.setOrgId(orgId);
		}

		getUserList(modelAndView, request, response, baseQuery);

		modelAndView.setViewName(VIEW_QUERY);

		return modelAndView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void getUserList(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			BaseQuery baseQuery) {

		PageRequest<Map> pageRequest = bindPageRequest(request, baseQuery, DEFAULT_SORT_COLUMNS);

		Page page = this.userInfoManager.findByPageRequest(pageRequest);//获取数据模型

		modelAndView.addObject("orgInfo", getOrgInfo());

		modelAndView.addObject("page", page);

		modelAndView.addObject("pageRequest", pageRequest);

	}

	/**显示
	 * 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(ModelMap result, @PathVariable Long id) throws Exception {

		UserInfo userInfo = this.userInfoManager.getById(id);

		result.addAttribute("user", userInfo);

		result.addAttribute("orgInfo", getOrgInfo());

		result.addAttribute("roleNames", userInfo.getRoleNames());

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW_DETAIL;
	}

	/** 编辑 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id) throws Exception {

		UserInfo userInfo = this.userInfoManager.getById(id);

		Map codeMap = new HashMap();

		codeMap.put(CodeInfo.CODECATE, SystemConst.CODE_USER_STATUS);

		result.addAttribute("user", userInfo);

		result.addAttribute("orgInfo", getOrgInfo());

		result.addAttribute("userStat", getCodeInfo(codeMap));

		result.addAttribute("roleInfos", getTotalRoleInfos(id));

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW_EDIT;
	}

	/** 新建 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/new")
	public String _new(ModelMap result, UserInfo user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map codeMap = new HashMap();

		codeMap.put(CodeInfo.CODECATE, SystemConst.CODE_USER_STATUS);

		result.addAttribute("orgInfo", getOrgInfo());

		result.addAttribute("userStat", getCodeInfo(codeMap));

		result.addAttribute("roleInfos", getTotalRoleInfos(null));

		result.addAttribute("user", user);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW_EDIT;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap model, @PathVariable Long id) {
		this.logger.debug("user.{},{}", "delete", id);
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.userInfoManager.removeById(id);
			//Flash.current().success(msg);
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();
			logger.error(msg);
			//Flash.current().error(msg);

		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW_QUERY;
	}

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap model, @Valid UserInfo userInfo, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.logger.debug("user.{}", "create");
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long userId = 0L;
		try {

			this.userInfoManager.save(userInfo);

			userId = userInfo.getEmpNo();

		} catch (Exception e) {

			isSucc = false;

			msg = MSG_CREATED_FAIL;

			logger.info(e.getMessage());

		}

		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg)
				.addAttribute("userId", userId);

		return VIEW_DETAIL;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap model, @PathVariable Long id, @Valid UserInfo userinfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		if (errors.hasErrors()) {
			model.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW_DETAIL;
		}
		this.logger.debug("user.{},{}", "update", id);

		try {
			UserInfo userInfo = this.userInfoManager.getById(id);
			bind(request, userInfo);
			this.userInfoManager.saveOrUpdate(userInfo);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			this.logger.info(e.getMessage());
			isSucc = false;
			msg = e.getMessage();
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);

		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);

		return VIEW_DETAIL;

	}

	private List<OrgInfo> getOrgInfo() {
		List<OrgInfo> orgInfoList = orgInfoManager.findByPageRequest(Maps.newHashMap());
		return orgInfoList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<CodeInfo> getCodeInfo(Map mapCode) {
		List<CodeInfo> codeInfo = codeInfoManager.findByPageRequest(mapCode);
		Collections.reverse(codeInfo);
		return codeInfo;
	}

	private List<RoleInfo> getTotalRoleInfos(Long id) {

		List<RoleInfo> roleInfos = this.roleInfoManager.findAllExtAdmin(id);

		return roleInfos;
	}

	@Autowired
	private RoleInfoManager roleInfoManager;

}
