/**
 * 
 */
package org.pssframework.controller.statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.query.statistics.StatisticsQuery;
import org.pssframework.service.statistics.StatisticsManager;
import org.pssframework.service.statistics.StatisticsType;
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
 *功率
 */
@Controller
@RequestMapping("/statistics/powercruv")
public class PowerCruvController extends BaseSpringController {

	private static final String VIEW_NAME = "/statistics/powerCurvQuery";
	@Autowired
	private StatisticsManager statisticsManager;

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping
	public ModelAndView showPowerCrur(ModelAndView modelAndView, HttpServletRequest request,
			HttpServletResponse response, StatisticsQuery statisticsQuery) {

		PageRequest<Map> pageRequest = bindPageRequest(request, statisticsQuery, DEFAULT_SORT_COLUMNS);

		Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.PowerCruv);//获取数据模型

		modelAndView.setViewName(VIEW_NAME);

		modelAndView.addObject("page", page);

		modelAndView.addObject("pageRequest", pageRequest);

		return modelAndView;

	}
}