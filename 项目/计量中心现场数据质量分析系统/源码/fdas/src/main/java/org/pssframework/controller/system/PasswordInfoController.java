/**
 * 
 */
package org.pssframework.controller.system;

import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.UserInfo;
import org.pssframework.security.OperatorDetails;
import org.pssframework.service.ServiceException;
import org.pssframework.service.system.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/password")
public class PasswordInfoController extends BaseRestSpringController<UserInfo, Long> {
	private static final String VIEW_MANAGER = "/system/passwordManage";

	@Autowired
	private UserInfoManager userInfoManager;

	@RequestMapping(value = "/manager")
	public ModelAndView changePassword(ModelAndView modelAndView, HttpServletRequest request,
			HttpServletResponse response) {
		modelAndView.addObject("user", getCurUserInfo());

		modelAndView.setViewName(VIEW_MANAGER);
		return modelAndView;
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
			return "";
		}
		this.logger.debug("user.{},{}", "update", id);

		try {
			UserInfo userInfoDb = this.userInfoManager.getById(id);

			bind(request, userInfoDb);

			this.userInfoManager.changePassWord(userInfoDb);

			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);

		} catch (ServiceException e) {
			this.logger.info(e.getMessage());
			isSucc = false;
			msg = e.getMessage();
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			isSucc = false;
			msg = e.getMessage();
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);

		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);

		return VIEW_MANAGER;
	}

	private UserInfo getCurUserInfo() {

		OperatorDetails user = SpringSecurityUtils.getCurrentUser();

		return userInfoManager.findUserByLoginName(user.getUsername());

	}
}
