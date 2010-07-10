/**
 * 
 */
package org.pssframework.controller.autorm;

import java.util.LinkedList;
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

	@SuppressWarnings("unchecked")
	private List<OrgInfo> getOrgOptions(Map mapRequest) {
		return this.orgInfoManager.findByPageRequest(mapRequest);
	}

	@SuppressWarnings("unchecked")
	private List<TgInfo> getTgOrgOptions(Map mapRequest) {
		List<TgInfo> tglist = this.tgInfoManager.findByPageRequest(mapRequest);
		if (tglist == null || tglist.size() <= 0) {
			tglist = new LinkedList<TgInfo>();
		}
		return tglist;
	}
}
