/**
 * 
 */
package org.pssframework.controller.autorm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jcreate.e3.table.DataModel;
import net.jcreate.e3.table.NavRequest;
import net.jcreate.e3.table.html.HTMLTableHelper;

import org.pssframework.controller.BaseSpringController;
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
import org.springframework.web.util.WebUtils;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.MessageTranObject;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/autorm/realTimeReading")
public class RealTimeReadingController extends BaseSpringController {

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private static final String VIEW = "/autorm/realTimeReading";

	private static final String ORG_ID = "orgId";

	private static final String TERM_ADDR = "termAddr";

	private static final String OBJ_ID = "objId";

	@Autowired
	private ICollectInterface realTimeProxy376;

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private RealTimeReadingManager realTimeReadingManager;

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, RealTimeReadingInfo model) {

		NavRequest navRequest = HTMLTableHelper.getNavRequest("userTable", request);

		Map params = WebUtils.getParametersStartingWith(request, "");

		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		pageRequest.setSortColumns(navRequest.getSortCode());
		pageRequest.setPageSize(navRequest.getPageSize());
		pageRequest.setPageNumber(navRequest.getStart());
		pageRequest.getFilters().putAll(params);

		Map mapRequest = new LinkedHashMap();

		// this.realTimeReadingManager.findByPageRequest(navRequest);//获取数据模型
		DataModel dataModel = this.realTimeReadingManager.findByPageRequest(pageRequest);//获取数据模型

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName(VIEW);

		getInitOption(modelAndView, mapRequest);

		modelAndView.addObject("userTable", dataModel);//保存翻页结果
		modelAndView.addObject("pageInfo", dataModel.getNavInfo());//保存翻页结果
		modelAndView.addObject("model", model);//保存翻页结果

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
	public ModelAndView down(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dtoJSONString = request.getParameter("dto");
		MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(dtoJSONString);
		long collectId = realTimeProxy376.readData(mto);
		logger.debug("collectId : " + collectId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("collectId", collectId);
		return modelAndView;
	}

}
