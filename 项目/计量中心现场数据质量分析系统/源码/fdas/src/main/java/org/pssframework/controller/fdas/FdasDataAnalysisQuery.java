package org.pssframework.controller.fdas;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.query.statistics.StatisticsQuery;
import org.pssframework.service.statistics.StatisticsManager;
import org.pssframework.service.statistics.StatisticsType;
import org.pssframework.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

@Controller
@RequestMapping("/fdas/dataAnalysisQuery")
public class FdasDataAnalysisQuery extends BaseSpringController {
    private static final String VIEW_NAME = "/fdas/dataAnalysisQueryF";
    private static final String VIEW_QUERY = "/fdas/dataAnalysisQuery";
    private static final String SDATE = "sdate";
    private static final String EDATE = "edate";

    @Autowired
    private StatisticsManager statisticsManager;

    // 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = "jyrq desc";

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

    /**
     * 
     * @param modelAndView
     * @param request
     * @param response
     * @param statisticsQuery
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/query")
    public ModelAndView _query(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            StatisticsQuery sq) throws Exception {
        PageRequest<Map> pageRequest = bindPageRequest(request, sq, DEFAULT_SORT_COLUMNS);
        Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.FdasDataAnalysis);// 获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        mav.setViewName(VIEW_QUERY);
        return mav;
    }

    /**
     * 初始化页面参数
     * 
     * @param mav
     */
    @SuppressWarnings("unchecked")
    private void initPageParams(ModelAndView mav) {
        Map mapRequest = new LinkedHashMap();
        mav.addObject(SDATE, DateUtils.getCurrentDate());
        mav.addObject(EDATE, DateUtils.getCurrentDate());
    }
}
