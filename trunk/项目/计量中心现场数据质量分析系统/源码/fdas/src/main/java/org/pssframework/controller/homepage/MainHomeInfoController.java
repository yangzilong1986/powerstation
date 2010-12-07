/**
 * 
 */
package org.pssframework.controller.homepage;

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
@RequestMapping("/homepage/main")
public class MainHomeInfoController extends BaseSpringController {
	private static final String VIEW = "/main";

	@RequestMapping
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		return modelAndView;
	}
}
