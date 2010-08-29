package org.pssframework.controller.statistics;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/statistics/psEventQuery")
public class PSEventQueryController extends BaseSpringController {
    private static final String VIEW_NAME = "/statistics/psEventQuery";
    private static final String VIEW_EVENT = "/statistics/eventQuery";
    private static final String QDATE = "qdate";
    private static final String ORGLIST = "orglist";
    private static final String TGLIST = "tglist";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private TgInfoManager tgInfoManager;

    @Autowired
    private StatisticsManager statisticsManager;

    // 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;

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
    @RequestMapping(value = "/event")
    public ModelAndView _event(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            StatisticsQuery sq) throws Exception {
        PageRequest<Map> pageRequest = bindPageRequest(request, sq, DEFAULT_SORT_COLUMNS);
        Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.PsEvent);// 获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        mav.setViewName(VIEW_EVENT);
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
        mav.addObject(ORGLIST, this.getOrgOptions(mapRequest));
        mav.addObject(TGLIST, this.getTgOrgOptions(mapRequest));
        mav.addObject(QDATE, DateUtils.getCurrentDate());
    }

    private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
        return this.orgInfoManager.findByPageRequest(mapRequest);
    }

    private List<TgInfo> getTgOrgOptions(Map<String, ?> mapRequest) {
        return tgInfoManager.findByPageRequest(mapRequest);
    }
}
