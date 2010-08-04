/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.UserInfo;
import org.pssframework.service.system.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Administrator
 *
 */
public class TgManagerInfoController extends BaseRestSpringController<UserInfo, Long> {

	@Autowired
	private UserInfoManager userInfoManager;

	/**
	 * binder用于bean属性的设置
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	private static final String VIEW_NEW = "/system/editUserPage";

	/** 进入新增 */
	@RequestMapping(value = "/new")
	public String _new(ModelMap model, HttpServletRequest request, HttpServletResponse response, UserInfo userInfo)
			throws Exception {

		Map mapRequest = new HashMap();

		model.addAttribute("userinfo", userInfo);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		model.addAttribute("tgId", request.getParameter("tgId"));

		return VIEW_NEW;
	}

	/**
	 * 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@Valid UserInfo model, BindingResult errors) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW_NEW;
		}
		try {
			this.userInfoManager.saveOrUpdate(model);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE,msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(msg);

		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW_NEW;
	}

}
