/**
 * 
 */
package org.pssframework.controller.autorm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.autorm.RealTimeReadingInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/autorm/realTimeReading")
public class RealTimeReadingController extends BaseRestSpringController<RealTimeReadingInfo, Long> {

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, RealTimeReadingInfo model) {
		// TODO Auto-generated method stub
		return super.index(request, response, model);
	}
}
