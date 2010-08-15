package org.pssframework.controller.statistics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/statistics/sumMeterDataQuery")
public class SumMeterDataQueryController extends BaseSpringController {
	private static final String VIEW_NAME = "/statistics/sumMeterDataQuery";

	@RequestMapping
	public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName(VIEW_NAME);

		return modelAndView;
	}


}