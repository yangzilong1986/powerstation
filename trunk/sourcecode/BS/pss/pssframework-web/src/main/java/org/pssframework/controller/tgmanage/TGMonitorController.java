package org.pssframework.controller.tgmanage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.service.tgmanage.TGMonitorManager;
import org.pssframework.util.FusionChartsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Zhangyu
 *
 */
@Controller
@RequestMapping("/tgmanage/tgmon")
public class TGMonitorController extends BaseSpringController {
    private static final String VIEW_NAME_FRM = "/tgmanage/showTGMonitor";
    private static final String VIEW_NAME_MON = "/tgmanage/tgMonitor";
    private static final String VIEW_NAME_MON_TM = "/tgmanage/mon_TotalMeter";
    private static final String VIEW_NAME_MON_PS = "/tgmanage/mon_PS";
    private static final String VIEW_NAME_MON_AQ = "/tgmanage/mon_Analog";
    private static final String VIEW_NAME_MON_SW = "/tgmanage/mon_Switch";
    //private static final String VIEW_NAME_TRE = "/tgmanage/tgTree";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private TGMonitorManager tgMonitorManager;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/frame")
    public ModelAndView _frame(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_FRM);
        Map mapRequest = new LinkedHashMap();
        getInitOption(mav, mapRequest);
        return mav;
    }

    /**
     * 
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/tgmonitor/{id}")
    public ModelAndView _tgmonitor(ModelAndView mav, @PathVariable Long id) throws Exception {
        mav.setViewName(VIEW_NAME_MON);
        logger.info("tgId : " + id);
        List tmlist = tgMonitorManager.findCombBoxByTg(id, "getTotalMeterCombBoxByTgId");
        List pslist = tgMonitorManager.findCombBoxByTg(id, "getPSCombBoxByTgId");
        List aqlist = tgMonitorManager.findCombBoxByTg(id, "getAnalogCombBoxByTgId");
        logger.info("tmlist : " + tmlist.toString());
        logger.info("pslist : " + pslist.toString());
        logger.info("aqlist : " + aqlist.toString());
        mav.addObject("tmlist", tmlist);
        mav.addObject("pslist", pslist);
        mav.addObject("aqlist", aqlist);
        return mav;
    }

    /**
     * 总表监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tm")
    public ModelAndView _tm(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_TM);
        Map<String, String> mapSeriesNames01 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames01, "01");
        String chartType = "1";
        String width = "0";
        String height = "0";
        Map<String, String> mapParams01 = new HashMap<String, String>();
        mapParams01.put("caption", "电压曲线");
        mapParams01.put("contextPath", request.getContextPath());
        mapParams01.put("timelabel", "HH:mm:ss");
        mapParams01.put("chartType", chartType);
        mapParams01.put("width", width);
        mapParams01.put("height", height);
        mav.addObject("initChart01", FusionChartsUtils.getChart(null, mapSeriesNames01, mapParams01, "chart01", true,
                null, null));
        Map<String, String> mapSeriesNames02 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames02, "02");
        Map<String, String> mapParams02 = new HashMap<String, String>();
        mapParams02.put("caption", "电流曲线");
        mapParams02.put("contextPath", request.getContextPath());
        mapParams02.put("timelabel", "HH:mm:ss");
        mapParams02.put("chartType", chartType);
        mapParams02.put("width", width);
        mapParams02.put("height", height);
        mav.addObject("initChart02", FusionChartsUtils.getChart(null, mapSeriesNames02, mapParams02, "chart02", true,
                null, null));
        Map<String, String> mapSeriesNames03 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames03, "03");
        Map<String, String> mapParams03 = new HashMap<String, String>();
        mapParams03.put("caption", "有功功率曲线");
        mapParams03.put("contextPath", request.getContextPath());
        mapParams03.put("timelabel", "HH:mm:ss");
        mapParams03.put("chartType", chartType);
        mapParams03.put("width", width);
        mapParams03.put("height", height);
        mav.addObject("initChart03", FusionChartsUtils.getChart(null, mapSeriesNames03, mapParams03, "chart03", true,
                null, null));
        Map<String, String> mapSeriesNames04 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames04, "04");
        Map<String, String> mapParams04 = new HashMap<String, String>();
        mapParams04.put("caption", "无功功率曲线");
        mapParams04.put("contextPath", request.getContextPath());
        mapParams04.put("timelabel", "HH:mm:ss");
        mapParams04.put("chartType", chartType);
        mapParams04.put("width", width);
        mapParams04.put("height", height);
        mav.addObject("initChart04", FusionChartsUtils.getChart(null, mapSeriesNames04, mapParams04, "chart04", true,
                null, null));
        Map<String, String> mapSeriesNames05 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames05, "05");
        Map<String, String> mapParams05 = new HashMap<String, String>();
        mapParams05.put("caption", "功率因数曲线");
        mapParams05.put("contextPath", request.getContextPath());
        mapParams05.put("timelabel", "HH:mm:ss");
        mapParams05.put("chartType", chartType);
        mapParams05.put("width", width);
        mapParams05.put("height", height);
        mav.addObject("initChart05", FusionChartsUtils.getChart(null, mapSeriesNames05, mapParams05, "chart05", true,
                null, null));
        return mav;
    }

    /**
     * 漏保开关监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ps")
    public ModelAndView _ps(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_PS);
        Map<String, String> mapSeriesNames01 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames01, "11");
        String chartType = "1";
        String width = "0";
        String height = "0";
        Map<String, String> mapParams01 = new HashMap<String, String>();
        mapParams01.put("caption", "电压曲线");
        mapParams01.put("contextPath", request.getContextPath());
        mapParams01.put("timelabel", "HH:mm:ss");
        mapParams01.put("chartType", chartType);
        mapParams01.put("width", width);
        mapParams01.put("height", height);
        mav.addObject("initChart01", FusionChartsUtils.getChart(null, mapSeriesNames01, mapParams01, "chart01", true,
                null, null));
        Map<String, String> mapSeriesNames02 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames02, "12");
        Map<String, String> mapParams02 = new HashMap<String, String>();
        mapParams02.put("caption", "电流曲线");
        mapParams02.put("contextPath", request.getContextPath());
        mapParams02.put("timelabel", "HH:mm:ss");
        mapParams02.put("chartType", chartType);
        mapParams02.put("width", width);
        mapParams02.put("height", height);
        mav.addObject("initChart02", FusionChartsUtils.getChart(null, mapSeriesNames02, mapParams02, "chart02", true,
                null, null));
        Map<String, String> mapSeriesNames03 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames03, "13");
        Map<String, String> mapParams03 = new HashMap<String, String>();
        mapParams03.put("caption", "剩余电流曲线");
        mapParams03.put("contextPath", request.getContextPath());
        mapParams03.put("timelabel", "HH:mm:ss");
        mapParams03.put("chartType", chartType);
        mapParams03.put("width", width);
        mapParams03.put("height", height);
        mav.addObject("initChart03", FusionChartsUtils.getChart(null, mapSeriesNames03, mapParams03, "chart03", true,
                null, null));
        return mav;
    }

    /**
     * 油温监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/aq")
    public ModelAndView _aq(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_AQ);
        Map<String, String> mapSeriesNames01 = new LinkedHashMap<String, String>();
        initChartSeriesNames(mapSeriesNames01, "21");
        String chartType = "1";
        String width = "0";
        String height = "0";
        Map<String, String> mapParams01 = new HashMap<String, String>();
        mapParams01.put("caption", "油温曲线");
        mapParams01.put("contextPath", request.getContextPath());
        mapParams01.put("timelabel", "HH:mm:ss");
        mapParams01.put("chartType", chartType);
        mapParams01.put("width", width);
        mapParams01.put("height", height);
        mav.addObject("initChart01", FusionChartsUtils.getChart(null, mapSeriesNames01, mapParams01, "chart01", true,
                null, null));
        return mav;
    }

    /**
     * 油温监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sw")
    public ModelAndView _sw(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_SW);
        return mav;
    }

    /**
     * 初始化
     * @param model
     * @param mapRequest
     */
    private void getInitOption(ModelAndView mav, Map<String, ?> mapRequest) {
        mav.addObject("orglist", getOrgOptions(mapRequest));
    }

    /**
     * 
     * @param mapRequest
     * @return
     */
    private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
        return orgInfoManager.findByPageRequest(mapRequest);
    }

    /**
     * 
     * @param mapSeriesNames
     * @param chartCategory
     */
    private void initChartSeriesNames(Map<String, String> mapSeriesNames, String chartCategory) {
        if("01".equals(chartCategory)) {        // 电压曲线
            mapSeriesNames.put("2101", "A相电压#FF0000");
            mapSeriesNames.put("2102", "B相电压#FFFF00");
            mapSeriesNames.put("2103", "C相电压#0000FF");
        }
        else if("02".equals(chartCategory)) {   // 电流曲线
            mapSeriesNames.put("2201", "A相电流#FF0000");
            mapSeriesNames.put("2202", "B相电流#FFFF00");
            mapSeriesNames.put("2203", "C相电流#0000FF");
        }
        else if("03".equals(chartCategory)) {   // 有功功率曲线
            mapSeriesNames.put("2300", "总有功功率#00FF00");
            mapSeriesNames.put("2301", "A相有功功率#FF0000");
            mapSeriesNames.put("2302", "B相有功功率#FFFF00");
            mapSeriesNames.put("2303", "C相有功功率#0000FF");
        }
        else if("04".equals(chartCategory)) {   // 无功功率曲线
            mapSeriesNames.put("2400", "总无功功率#00FF00");
            mapSeriesNames.put("2401", "A相无功功率#FF0000");
            mapSeriesNames.put("2402", "B相无功功率#FFFF00");
            mapSeriesNames.put("2403", "C相无功功率#0000FF");
        }
        else if("05".equals(chartCategory)) { // 功率因数曲线
            mapSeriesNames.put("2600", "总功率因数#00FF00");
            mapSeriesNames.put("2601", "A相功率因数#FF0000");
            mapSeriesNames.put("2602", "B相功率因数#FFFF00");
            mapSeriesNames.put("2603", "C相功率因数#0000FF");
        }
        else if("11".equals(chartCategory)) {   // 电压曲线
            mapSeriesNames.put("B611", "A相电压#FF0000");
            mapSeriesNames.put("B612", "B相电压#FFFF00");
            mapSeriesNames.put("B613", "C相电压#0000FF");
        }
        else if("12".equals(chartCategory)) {   // 电流曲线
            mapSeriesNames.put("B621", "A相电流#FF0000");
            mapSeriesNames.put("B622", "B相电流#FFFF00");
            mapSeriesNames.put("B623", "C相电流#0000FF");
        }
        else if("13".equals(chartCategory)) {   // 剩余电流曲线
            mapSeriesNames.put("B660", "剩余电流#FF0000");
        }
        else if("21".equals(chartCategory)) {   // 油温
            mapSeriesNames.put("BE16", "油温#FF0000");
        }
    }
}
