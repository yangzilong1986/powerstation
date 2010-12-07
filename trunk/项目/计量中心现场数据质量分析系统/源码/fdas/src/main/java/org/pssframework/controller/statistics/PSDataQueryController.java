package org.pssframework.controller.statistics;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.query.statistics.StatisticsQuery;
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
@RequestMapping("/statistics/psDataQuery")
public class PSDataQueryController extends BaseSpringController {
    private static final String VIEW_NAME = "/statistics/psDataQuery";
    private static final String VIEW_EC = "/statistics/psEcCurvQuery";
    private static final String SDATE = "sdate";
    private static final String EDATE = "edate";
    private static final String ORGLIST = "orglist";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private StatisticsManager statisticsManager;

    // 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = "dataTime asc";

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
    @RequestMapping(value = "/ec")
    public ModelAndView _ec(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            StatisticsQuery sq) throws Exception {
        PageRequest<Map> pageRequest = bindPageRequest(request, sq, DEFAULT_SORT_COLUMNS);
        Page page = this.statisticsManager.findByPageRequest(pageRequest, StatisticsType.PsEcCurv);// 获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        initActionType(mav);
        mav.setViewName(VIEW_EC);
        return mav;
    }

    /**
     * 
     * @param mav
     */
    private void initActionType(ModelAndView mav) {
        Map<String, String> actionTypeMap = new HashMap<String, String>();
        actionTypeMap.put("0000", "漏电跳闸");
        actionTypeMap.put("0001", "突变跳闸");
        actionTypeMap.put("0010", "特波跳闸");
        actionTypeMap.put("0011", "过载跳闸");
        actionTypeMap.put("0100", "过压跳闸");
        actionTypeMap.put("0101", "欠压跳闸");
        actionTypeMap.put("0110", "短路跳闸");
        actionTypeMap.put("0111", "手动跳闸");
        actionTypeMap.put("1000", "停电跳闸");
        actionTypeMap.put("1001", "互感器故障跳闸");
        actionTypeMap.put("1010", "远程跳闸");
        actionTypeMap.put("1011", "其它原因跳闸");
        actionTypeMap.put("1100", "合闸过程中");
        actionTypeMap.put("1101", "合闸失败");
        mav.addObject("actionTypeMap", actionTypeMap);
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
        mav.addObject(SDATE, DateUtils.getCurrentDate());
        mav.addObject(EDATE, DateUtils.getCurrentDate());
    }

    private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
        return this.orgInfoManager.findByPageRequest(mapRequest);
    }
}
