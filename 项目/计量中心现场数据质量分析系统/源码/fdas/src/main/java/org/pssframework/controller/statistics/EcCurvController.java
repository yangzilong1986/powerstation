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
 *电流电压
 */
@Controller
@RequestMapping("/statistics/eccurv")
public class EcCurvController extends BaseSpringController {
    private static final String VIEW_EC = "/statistics/ecCurvQuery";
    private static final String VIEW_VT = "/statistics/vtCurvQuery";
    private static final String VIEW_EC_CHART = "/statistics/ecCurvQuery_Chart";
    private static final String VIEW_VT_CHART = "/statistics/vtCurvQuery_Chart";

    @Autowired
    private StatisticsManager statisticsManager;

    // 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = "dataTime asc";

    /** binder用于bean属性的设置 */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 电流曲线
     * @param modelAndView
     * @param request
     * @param response
     * @param statisticsQuery
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/ec")
    public ModelAndView showEc(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            StatisticsQuery statisticsQuery) {
        String showMode = request.getParameter("showMode");
        PageRequest<Map> pageRequest = bindPageRequest(request, statisticsQuery, DEFAULT_SORT_COLUMNS);
        Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.EcCurv);//获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        if("chart".equals(showMode)) {
            Map<String, String> mapSeriesNames = new LinkedHashMap<String, String>();
            initChartSeriesNames(mapSeriesNames, "2");
            String chartType = "1";
            String width = "0";
            String height = "0";
            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("caption", "电流曲线");
            mapParams.put("contextPath", request.getContextPath());
            mapParams.put("timelabel", "HH");
            mapParams.put("chartType", chartType);
            mapParams.put("width", width);
            mapParams.put("height", height);
            mav.addObject("chart", FusionChartsUtils.getChart(page.getResult(), mapSeriesNames, mapParams));
            mav.setViewName(VIEW_EC_CHART);
        }
        else {
            mav.setViewName(VIEW_EC);
        }
        return mav;
    }

    /**
     * 
     * @param modelAndView
     * @param request
     * @param response
     * @param statisticsQuery
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/vt")
    public ModelAndView showVt(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            StatisticsQuery statisticsQuery) {
        String showMode = request.getParameter("showMode");
        PageRequest<Map> pageRequest = bindPageRequest(request, statisticsQuery, DEFAULT_SORT_COLUMNS);
        Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.EcCurv);//获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        if("chart".equals(showMode)) {
            Map<String, String> mapSeriesNames = new LinkedHashMap<String, String>();
            initChartSeriesNames(mapSeriesNames, "1");
            String chartType = "1";
            String width = "0";
            String height = "0";
            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("caption", "电压曲线");
            mapParams.put("timelabel", "HH");
            mapParams.put("contextPath", request.getContextPath());
            mapParams.put("chartType", chartType);
            mapParams.put("width", width);
            mapParams.put("height", height);
            mav.addObject("chart", FusionChartsUtils.getChart(page.getResult(), mapSeriesNames, mapParams));
            mav.setViewName(VIEW_VT_CHART);
        }
        else {
            mav.setViewName(VIEW_VT);
        }
        return mav;
    }

    private void initChartSeriesNames(Map<String, String> mapSeriesNames, String chartCategory) {
        if("1".equals(chartCategory)) { // 电压数据
            mapSeriesNames.put("voltA", "A相电压#FF0000");
            mapSeriesNames.put("voltB", "B相电压#FFFF00");
            mapSeriesNames.put("voltC", "C相电压#0000FF");
        }
        else if("2".equals(chartCategory)) { // 电流数据
            mapSeriesNames.put("ecurA", "A相电流#FF0000");
            mapSeriesNames.put("ecurB", "B相电流#FFFF00");
            mapSeriesNames.put("ecurC", "C相电流#0000FF");
        }
    }
}
