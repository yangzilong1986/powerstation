/**
 * 
 */
package org.pssframework.controller.statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.query.statistics.StatisticsQuery;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.statistics.StatisticsManager;
import org.pssframework.service.statistics.StatisticsType;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 * termEvent
 */
@Controller
@RequestMapping("/statistics/termData")
public class TermDataController extends BaseSpringController {

	private static final String VIEW_NAME = "/statistics/termDataQuery";
	private static final String VIEW_NAME_EVENT = "/statistics/termEventQuery";
	private static final String SDATE = "sdate";
	private static final String EDATE = "edate";
	private static final String ORGLIST = "orglist";
	private static final String TGLIST = "tglist";

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private StatisticsManager statisticsManager;

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "exTime asc";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 
	 * @param mav
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView index(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		mav.setViewName(VIEW_NAME);
		initPageParams(mav);
		return mav;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/event")
	public ModelAndView showEvent(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			StatisticsQuery statisticsQuery) {

		PageRequest<Map> pageRequest = bindPageRequest(request, statisticsQuery, DEFAULT_SORT_COLUMNS);

		Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.TermEvent);//获取数据模型

		modelAndView.setViewName(VIEW_NAME_EVENT);

		modelAndView.addObject("page", page);

		modelAndView.addObject("pageRequest", pageRequest);

		return modelAndView;

	}

	/**
	 * 初始化页面参数
	 * 
	 * @param mav
	 */
	@SuppressWarnings("unchecked")
	private void initPageParams(ModelAndView mav) {
		Map mapRequest = new LinkedHashMap();
		mav.addObject(ORGLIST, this.getOrgOptions(mapRequest));
		mav.addObject(TGLIST, this.getTgOrgOptions(mapRequest));
		mav.addObject(SDATE, DateUtils.getCurrentDate());
		mav.addObject(EDATE, DateUtils.getCurrentDate());
	}

	private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
		return this.orgInfoManager.findByPageRequest(mapRequest);
	}

	private List<TgInfo> getTgOrgOptions(Map<String, ?> mapRequest) {
		return tgInfoManager.findByPageRequest(mapRequest);
	}
}
