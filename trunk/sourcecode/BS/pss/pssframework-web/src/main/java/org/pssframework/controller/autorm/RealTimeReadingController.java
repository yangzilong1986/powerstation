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
import org.pssframework.service.atuorm.RealTimeReadingManager;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.RealTimeProxy376;
import pep.bp.realinterface.mto.MessageTranObject;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/autorm/realTimeReading")
public class RealTimeReadingController extends BaseRestSpringController<RealTimeReadingInfo, Long> {

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private static final String VIEW = "/autorm/realTimeReading";

	private static final String ORG_ID = "orgId";

	private static final String TERM_ADDR = "termAddr";

	private static final String OBJ_ID = "objId";

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private RealTimeReadingManager realTimeReadingManager;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, RealTimeReadingInfo model) {

		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);

		Map mapRequest = new LinkedHashMap();

		Page page = this.realTimeReadingManager.findByPageRequest(pageRequest);

		ModelAndView modelAndView = toModelAndView(page, pageRequest);

		modelAndView.setViewName(VIEW);

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

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/down")
	public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dtoJSONString = request.getParameter("dto");
		MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(dtoJSONString);
		ICollectInterface ci = new RealTimeProxy376();
		long collectId = ci.readData(mto);
		logger.debug("collectId : " + collectId);
	}

}
