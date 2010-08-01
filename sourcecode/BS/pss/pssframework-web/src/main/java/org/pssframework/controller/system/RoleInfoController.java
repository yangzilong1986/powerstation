/**
 * 
 */
package org.pssframework.controller.system;

import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.service.system.RoleInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/role")
public class RoleInfoController extends BaseRestSpringController<RoleInfo, Long> {
	private static final String VIEW_MANAGER = "/system/roleManagerFrame";
	private static final String VIEW_QUERY = "/system/roleList";
	private static final String VIEW_DETAIL = "/system/roleDetail";
	private static final String VIEW_EDIT = "/system/roleEdit";

	@Autowired
	private RoleInfoManager roleInfoManager;

	@RequestMapping("/manager")
	public ModelAndView manager(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName(VIEW_MANAGER);
		return modelAndView;
	}

	@RequestMapping("list")
	public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName(VIEW_QUERY);
		return modelAndView;
	}

	@RequestMapping("detail")
	public ModelAndView detail(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName(VIEW_DETAIL);
		return modelAndView;
	}

	/** 编辑 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id) throws Exception {

		RoleInfo roleInfo = this.roleInfoManager.getById(id);

		result.addAttribute("role", roleInfo);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW_EDIT;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap modelMap, @Valid RoleInfo roleInfo, BindingResult errors, @PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		try {
			logger.debug("bind roleInfo {} from request", roleInfo);

			RoleInfo roleInfoDb = this.roleInfoManager.getById(id);

			bind(request, roleInfoDb);

			this.roleInfoManager.saveOrUpdate(roleInfoDb);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW_EDIT;
	}

}
