/**
 * 
 */
package org.pssframework.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/permission")
public class PermissionInfoController extends BaseSpringController {

	private static final String VIEW = "/system/permission";

	@RequestMapping
	public ModelAndView showTab(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName(VIEW);
		return modelAndView;
	}

}
