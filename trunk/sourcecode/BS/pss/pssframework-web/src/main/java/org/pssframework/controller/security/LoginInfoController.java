/**
 * 
 */
package org.pssframework.controller.security;

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
@RequestMapping("/security/login")
public class LoginInfoController extends BaseSpringController {

	private static final String VIEW = "/security/login";

	private static final String ERROR_ID = "error";
	private static final String SUCC_ID = "succ";

	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView(VIEW);

		String errorId = request.getParameter(ERROR_ID);
		String succId = request.getParameter(SUCC_ID);

		modelAndView.addObject(ERROR_ID, errorId);
		modelAndView.addObject(SUCC_ID, succId);

		return modelAndView;
	}
}
