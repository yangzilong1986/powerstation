package org.pssframework.controller.statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.query.statistics.StatisticsQuery;
import org.pssframework.service.statistics.StatisticsManager;
import org.pssframework.service.statistics.StatisticsType;
import org.pssframework.util.FusionChartsUtils;
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
    private static final String VIEW_NAME_CHART = "/statistics/powerCurvQuery_Chart";

	@Autowired
	private StatisticsManager statisticsManager;

	// 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = "dataTime asc";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView showPowerCrur(ModelAndView mav, HttpServletRequest request,
			HttpServletResponse response, StatisticsQuery statisticsQuery) {
        String showMode = request.getParameter("showMode");
		PageRequest<Map> pageRequest = bindPageRequest(request, statisticsQuery, DEFAULT_SORT_COLUMNS);
		Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.PowerCruv);//获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        if("chart".equals(showMode)) {
            Map<String, String> mapSeriesNames = new LinkedHashMap<String, String>();
            initChartSeriesNames(mapSeriesNames, "2");
            String chartType = "1";
            String width = "0";
            String height = "0";
            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("caption", "功率曲线");
            mapParams.put("contextPath", request.getContextPath());
            mapParams.put("timelabel", "HH");
            mapParams.put("chartType", chartType);
            mapParams.put("width", width);
            mapParams.put("height", height);
            mav.addObject("chart", FusionChartsUtils.getChart(page.getResult(), mapSeriesNames, mapParams));
            mav.setViewName(VIEW_NAME_CHART);
        }
        else {
            mav.setViewName(VIEW_NAME);
        }
        return mav;
	}

    /**
     * 
     * @param mapSeriesNames
     * @param chartCategory
     */
    private void initChartSeriesNames(Map<String, String> mapSeriesNames, String chartCategory) {
        mapSeriesNames.put("actPower", "有功功率#00FF00");
        mapSeriesNames.put("reactPower", "无功功率#2F5D20");
        mapSeriesNames.put("actPowerA", "A相有功功率#FF0000");
        mapSeriesNames.put("actPowerB", "B相有功功率#FFFF00");
        mapSeriesNames.put("actPowerC", "C相有功功率#0000FF");
        mapSeriesNames.put("reactPowerA", "A相无功功率#DDCC00");
        mapSeriesNames.put("reactPowerB", "B相无功功率#2F4ED9");
        mapSeriesNames.put("reactPowerC", "C相无功功率#0E2FF0");
    }
}
