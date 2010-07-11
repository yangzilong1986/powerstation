/**
 * 
 */
package org.pssframework.controller.autorm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.autorm.RealTimeReadingInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.system.OrgInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
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

	private static final String VIEW = "/autorm/realTimeReading";

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private TgInfoManager tgInfoManager;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, RealTimeReadingInfo model) {
		ModelAndView modelAndView = new ModelAndView(VIEW);

		Map<String, ?> mapRequest = new LinkedHashMap();

        

		getInitOption(modelAndView, mapRequest);

		return modelAndView;
	}

	/**
	 * 下拉框
	 * 
	 * @param model
	 * @param mapRequest
	 */
	private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {

		model.addObject("orglist", this.getOrgOptions(mapRequest));

		model.addObject("tglist", this.getTgOrgOptions(mapRequest));

	}

	private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
		return this.orgInfoManager.findByPageRequest(mapRequest);
	}

	private List<TgInfo> getTgOrgOptions(Map<String, ?> mapRequest) {
		return tgInfoManager.findByPageRequest(mapRequest);
	}
}
