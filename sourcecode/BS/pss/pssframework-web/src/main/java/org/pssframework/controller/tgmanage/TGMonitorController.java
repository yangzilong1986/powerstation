package org.pssframework.controller.tgmanage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.service.tgmanage.TGMonitorManager;
import org.pssframework.util.ConverterUtils;
import org.pssframework.util.FusionChartsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.MessageTranObject;

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

    @Autowired
    private ICollectInterface realTimeProxy376;

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
        mapParams01.put("caption", "温度曲线");
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
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/down")
    public ModelAndView _down(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String dtoJSONString = request.getParameter("dto");
        String mtoType = request.getParameter("mtoType");
        String monType = request.getParameter("monType");
        logger.info("dtoJSONString : " + dtoJSONString);
        logger.info("mtoType       : " + mtoType);
        logger.info("monType       : " + monType);
        ModelAndView mav = new ModelAndView();
        if("TotalMeter".equals(monType)) {
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.readData(mto);
            logger.info("collectId : " + collectId);
            mav.addObject("collectId", collectId);
        }
        else if("PS".equals(monType)) {
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.transmitMsg(mto);
            logger.info("collectId : " + collectId);
            mav.addObject("collectId", collectId);
        }
        else if("Analog".equals(monType)) {
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.readData(mto);
            logger.info("collectId : " + collectId);
            mav.addObject("collectId", collectId);
        }
        mav.addObject("fetchCount", 10);
        return mav;
    }

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/up")
    public ModelAndView _up(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long collectId = Long.parseLong(request.getParameter("collectId"));
        String monType = request.getParameter("monType");
        String logicalAddr = request.getParameter("logicalAddr");
        String gpSn = request.getParameter("gpSn");
        String commandItem = request.getParameter("commandItem");
        logger.info("collectId : " + collectId);
        logger.info("monType : " + monType);
        logger.info("logicalAddr : " + logicalAddr);
        logger.info("gpSn : " + gpSn);
        logger.info("commandItem : " + commandItem);
        ModelAndView mav = new ModelAndView();
        if("TotalMeter".equals(monType)) {
            String chartHistoryValues01 = request.getParameter("chartHistoryValues01");
            String chartHistoryValues02 = request.getParameter("chartHistoryValues02");
            String chartHistoryValues03 = request.getParameter("chartHistoryValues03");
            String chartHistoryValues04 = request.getParameter("chartHistoryValues04");
            String chartHistoryValues05 = request.getParameter("chartHistoryValues05");
            Map<String, Map<String, String>> resultMap = realTimeProxy376.getReturnByReadData(collectId);
            logger.info("resultMap[" + collectId + "] : " + resultMap.toString());
            if(!resultMap.isEmpty()) {
                String chartValues01 = joinChartValues(chartHistoryValues01, resultMap, logicalAddr, gpSn, commandItem, "01");
                String chartValues02 = joinChartValues(chartHistoryValues02, resultMap, logicalAddr, gpSn, commandItem, "02");
                String chartValues03 = joinChartValues(chartHistoryValues03, resultMap, logicalAddr, gpSn, commandItem, "03");
                String chartValues04 = joinChartValues(chartHistoryValues04, resultMap, logicalAddr, gpSn, commandItem, "04");
                String chartValues05 = joinChartValues(chartHistoryValues05, resultMap, logicalAddr, gpSn, commandItem, "05");
                mav.addObject("chartValues01", chartValues01);
                mav.addObject("chartValues02", chartValues02);
                mav.addObject("chartValues03", chartValues03);
                mav.addObject("chartValues04", chartValues04);
                mav.addObject("chartValues05", chartValues05);
                mav.addObject("chartXML01", getChartXML(chartValues01, "01"));
                mav.addObject("chartXML02", getChartXML(chartValues02, "02"));
                mav.addObject("chartXML03", getChartXML(chartValues03, "03"));
                mav.addObject("chartXML04", getChartXML(chartValues04, "04"));
                mav.addObject("chartXML05", getChartXML(chartValues05, "05"));
                logger.info("chartValues01 : " + chartValues01);
                logger.info("chartValues02 : " + chartValues02);
                logger.info("chartValues03 : " + chartValues03);
                logger.info("chartValues04 : " + chartValues04);
                logger.info("chartValues05 : " + chartValues05);
                //logger.info("chartXML01 : " + getChartXML(chartValues01, "01"));
                //logger.info("chartXML02 : " + getChartXML(chartValues02, "02"));
                //logger.info("chartXML03 : " + getChartXML(chartValues03, "03"));
                //logger.info("chartXML04 : " + getChartXML(chartValues04, "04"));
                //logger.info("chartXML05 : " + getChartXML(chartValues05, "05"));
                mav.addObject("returnFlag", true);
                logger.info("resultMap : " + resultMap.toString());
            }
            else {
                mav.addObject("returnFlag", false);
            }
        }
        else if("PS".equals(monType)) {
            String chartHistoryValues02 = request.getParameter("chartHistoryValues02");
            String chartHistoryValues03 = request.getParameter("chartHistoryValues03");
            Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitPara(collectId);
            logger.info("resultMap[" + collectId + "] : " + resultMap.toString());
            if(!resultMap.isEmpty()) {
                String chartValues02 = joinChartValues(chartHistoryValues02, resultMap, logicalAddr, gpSn, commandItem, "12");
                String chartValues03 = joinChartValues(chartHistoryValues03, resultMap, logicalAddr, gpSn, commandItem, "13");
                mav.addObject("chartValues02", chartValues02);
                mav.addObject("chartValues03", chartValues03);
                mav.addObject("chartXML02", getChartXML(chartValues02, "12"));
                mav.addObject("chartXML03", getChartXML(chartValues03, "13"));
                logger.info("chartValues02 : " + chartValues02);
                logger.info("chartValues03 : " + chartValues03);
                //logger.info("chartXML02 : " + getChartXML(chartValues02, "12"));
                //logger.info("chartXML03 : " + getChartXML(chartValues03, "13"));
                mav.addObject("returnFlag", true);
                logger.info("resultMap : " + resultMap.toString());
            }
            else {
                mav.addObject("returnFlag", false);
            }
        }
        else if("Analog".equals(monType)) {
            String chartHistoryValues01 = request.getParameter("chartHistoryValues01");
            Map<String, Map<String, String>> resultMap = realTimeProxy376.getReturnByReadData(collectId);
            logger.info("resultMap[" + collectId + "] : " + resultMap.toString());
            if(!resultMap.isEmpty()) {
                String chartValues01 = joinChartValues(chartHistoryValues01, resultMap, logicalAddr, gpSn, commandItem, "21");
                mav.addObject("chartValues01", chartValues01);
                mav.addObject("chartXML01", getChartXML(chartValues01, "21"));
                logger.info("chartValues01 : " + chartValues01);
                //logger.info("chartXML01 : " + getChartXML(chartValues01, "21"));
                mav.addObject("returnFlag", true);
                logger.info("resultMap : " + resultMap.toString());
            }
            else {
                mav.addObject("returnFlag", false);
            }
        }
        return mav;
    }
    
    /**
     * 
     * @param values
     * @param maps
     * @return
     */
    private String joinChartValues(String values, Map<String, Map<String, String>> maps, String logicalAddr, String gp,
            String commandItem, String chartCategory) {
        String keyPrefix = logicalAddr + "#" + gp + "#" + commandItem;
        StringBuffer sbvalue = new StringBuffer();
        if("01".equals(chartCategory)) {        // 电压曲线
            // 2101 A相电压
            Map<String, String> map1 = maps.get(keyPrefix + "#2101");
            // 2102 B相电压
            Map<String, String> map2 = maps.get(keyPrefix + "#2102");
            // 2103 C相电压
            Map<String, String> map3 = maps.get(keyPrefix + "#2103");
            
            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            sbvalue.append("," + getMapValue(map1));
            sbvalue.append("," + getMapValue(map2));
            sbvalue.append("," + getMapValue(map3));
            sbvalue.append(",");
        }
        else if("02".equals(chartCategory)) {   // 电流曲线
            // 2201 A相电流
            Map<String, String> map1 = maps.get(keyPrefix + "#2201");
            // 2202 B相电流
            Map<String, String> map2 = maps.get(keyPrefix + "#2202");
            // 2203 C相电流
            Map<String, String> map3 = maps.get(keyPrefix + "#2203");

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            sbvalue.append("," + getMapValue(map1));
            sbvalue.append("," + getMapValue(map2));
            sbvalue.append("," + getMapValue(map3));
            sbvalue.append(",");
        }
        else if("03".equals(chartCategory)) {   // 有功功率曲线
            // 2300 总有功功率
            Map<String, String> map0 = maps.get(keyPrefix + "#2300");
            // 2301 A相有功功率
            Map<String, String> map1 = maps.get(keyPrefix + "#2301");
            // 2302 B相有功功率
            Map<String, String> map2 = maps.get(keyPrefix + "#2302");
            // 2303 C相有功功率
            Map<String, String> map3 = maps.get(keyPrefix + "#2303");

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            sbvalue.append("," + getMapValue(map0));
            sbvalue.append("," + getMapValue(map1));
            sbvalue.append("," + getMapValue(map2));
            sbvalue.append("," + getMapValue(map3));
            sbvalue.append(",");
        }
        else if("04".equals(chartCategory)) {   // 无功功率曲线
            // 2400 总无功功率
            Map<String, String> map0 = maps.get(keyPrefix + "#2400");
            // 2401 A相无功功率
            Map<String, String> map1 = maps.get(keyPrefix + "#2401");
            // 2402 B相无功功率
            Map<String, String> map2 = maps.get(keyPrefix + "#2402");
            // 2403 C相无功功率
            Map<String, String> map3 = maps.get(keyPrefix + "#2403");

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            sbvalue.append("," + getMapValue(map0));
            sbvalue.append("," + getMapValue(map1));
            sbvalue.append("," + getMapValue(map2));
            sbvalue.append("," + getMapValue(map3));
            sbvalue.append(",");
        }
        else if("05".equals(chartCategory)) {   // 功率因数曲线
            // 2600 总功率因数
            Map<String, String> map0 = maps.get(keyPrefix + "#2600");
            // 2601 A相功率因数
            Map<String, String> map1 = maps.get(keyPrefix + "#2601");
            // 2602 B相功率因数
            Map<String, String> map2 = maps.get(keyPrefix + "#2602");
            // 2603 C相功率因数
            Map<String, String> map3 = maps.get(keyPrefix + "#2603");

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            sbvalue.append("," + getMapValue(map0));
            sbvalue.append("," + getMapValue(map1));
            sbvalue.append("," + getMapValue(map2));
            sbvalue.append("," + getMapValue(map3));
            sbvalue.append(",");
        }
        else if("11".equals(chartCategory)) {   // 电压曲线
            Map<String, String> map = maps.get(keyPrefix);
            // B611 A相电压
            //Map<String, String> map1 = maps.get(keyPrefix + "#B611");
            String s1 = (map != null ? map.get("B611") : null);
            // B612 B相电压
            //Map<String, String> map2 = maps.get(keyPrefix + "#B612");
            String s2 = (map != null ? map.get("B612") : null);
            // B613 C相电压
            //Map<String, String> map3 = maps.get(keyPrefix + "#B613");
            String s3 = (map != null ? map.get("B613") : null);

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            //sbvalue.append("," + getMapValue(map1));
            //sbvalue.append("," + getMapValue(map2));
            //sbvalue.append("," + getMapValue(map3));
            sbvalue.append("," + (s1 != null ? s1.trim() : ""));
            sbvalue.append("," + (s2 != null ? s2.trim() : ""));
            sbvalue.append("," + (s3 != null ? s3.trim() : ""));
            sbvalue.append(",");
        }
        else if("12".equals(chartCategory)) {   // 电流曲线
            Map<String, String> map = maps.get(keyPrefix);
            // B621 A相电流
            //Map<String, String> map1 = maps.get(keyPrefix + "#B621");
            String s1 = (map != null ? map.get("B621") : null);
            // B622 B相电流
            //Map<String, String> map2 = maps.get(keyPrefix + "#B622");
            String s2 = (map != null ? map.get("B622") : null);
            // B623 C相电流
            //Map<String, String> map3 = maps.get(keyPrefix + "#B623");
            String s3 = (map != null ? map.get("B623") : null);

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            //sbvalue.append("," + getMapValue(map1));
            //sbvalue.append("," + getMapValue(map2));
            //sbvalue.append("," + getMapValue(map3));
            sbvalue.append("," + (s1 != null ? s1.trim() : ""));
            sbvalue.append("," + (s2 != null ? s2.trim() : ""));
            sbvalue.append("," + (s3 != null ? s3.trim() : ""));
            sbvalue.append(",");
        }
        else if("13".equals(chartCategory)) {   // 剩余电流曲线
            Map<String, String> map = maps.get(keyPrefix);
            // B660 剩余电流
            //Map<String, String> map1 = maps.get(keyPrefix + "#B660");
            String s1 = (map != null ? map.get("B660") : null);

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            //sbvalue.append("," + getMapValue(map1));
            sbvalue.append("," + (s1 != null ? s1.trim() : ""));
            sbvalue.append(",");
        }
        else if("21".equals(chartCategory)) {   // 温度曲线
            // BE16 温度
            Map<String, String> map1 = maps.get(keyPrefix + "#BE16");

            String categoryLabel = getTimelable(new Date(), "HH:mm:ss");
            
            sbvalue.append(categoryLabel);
            sbvalue.append("," + getMapValue(map1));
        }

        StringBuffer sb = new StringBuffer();
        if(values != null && values.trim().length() > 0) {
            sb.append(values).append("#").append(sbvalue);
        }
        else {
            sb.append(sbvalue);
        }

        return sb.toString();
    }

    private String getTimelable(Date d, String r) {
        SimpleDateFormat sdf = new SimpleDateFormat(r);
        return sdf.format(d);
    }

    /**
     * 
     * @param values
     * @return
     */
    private String getChartXML(String values, String chartCategory) {
        List<String[]> valueList = new ArrayList<String[]>();
        String[] sa1 = values.split("#");
        for(int i = 0; i < sa1.length; i++) {
            valueList.add(sa1[i].split(","));
        }

        String caption = null;
        int labelStep = 0;
        String[] seriesNames = null;
        String[] colors = null;
        if("01".equals(chartCategory)) {        // 电压曲线
            caption = "电压曲线";
            seriesNames = new String[3];
            seriesNames[0] = "A相电压";
            seriesNames[1] = "B相电压";
            seriesNames[2] = "C相电压";
            colors = new String[3];
            colors[0] = "FF0000";
            colors[1] = "FFFF00";
            colors[2] = "0000FF";
        }
        else if("02".equals(chartCategory)) {   // 电流曲线
            caption = "电流曲线";
            seriesNames = new String[3];
            seriesNames[0] = "A相电流";
            seriesNames[1] = "B相电流";
            seriesNames[2] = "C相电流";
            colors = new String[3];
            colors[0] = "FF0000";
            colors[1] = "FFFF00";
            colors[2] = "0000FF";
        }
        else if("03".equals(chartCategory)) {   // 有功功率曲线
            caption = "有功功率曲线";
            seriesNames = new String[4];
            seriesNames[0] = "总有功功率";
            seriesNames[1] = "A相有功功率";
            seriesNames[2] = "B相有功功率";
            seriesNames[3] = "C相有功功率";
            colors = new String[4];
            colors[0] = "00FF00";
            colors[1] = "FF0000";
            colors[2] = "FFFF00";
            colors[3] = "0000FF";
        }
        else if("04".equals(chartCategory)) {   // 无功功率曲线
            caption = "无功功率曲线";
            seriesNames = new String[4];
            seriesNames[0] = "总无功功率";
            seriesNames[1] = "A相无功功率";
            seriesNames[2] = "B相无功功率";
            seriesNames[3] = "C相无功功率";
            colors = new String[4];
            colors[0] = "00FF00";
            colors[1] = "FF0000";
            colors[2] = "FFFF00";
            colors[3] = "0000FF";
        }
        else if("05".equals(chartCategory)) {   // 功率因数曲线
            caption = "功率因数曲线";
            seriesNames = new String[4];
            seriesNames[0] = "总功率因数";
            seriesNames[1] = "A相功率因数";
            seriesNames[2] = "B相功率因数";
            seriesNames[3] = "C相功率因数";
            colors = new String[4];
            colors[0] = "00FF00";
            colors[1] = "FF0000";
            colors[2] = "FFFF00";
            colors[3] = "0000FF";
        }
        else if("11".equals(chartCategory)) {   // 电压曲线
            caption = "电压曲线";
            seriesNames = new String[3];
            seriesNames[0] = "A相电压";
            seriesNames[1] = "B相电压";
            seriesNames[2] = "C相电压";
            colors = new String[3];
            colors[0] = "FF0000";
            colors[1] = "FFFF00";
            colors[2] = "0000FF";
        }
        else if("12".equals(chartCategory)) {   // 电流曲线
            caption = "电流曲线";
            seriesNames = new String[3];
            seriesNames[0] = "A相电流";
            seriesNames[1] = "B相电流";
            seriesNames[2] = "C相电流";
            colors = new String[3];
            colors[0] = "FF0000";
            colors[1] = "FFFF00";
            colors[2] = "0000FF";
        }
        else if("13".equals(chartCategory)) {   // 剩余电流曲线
            caption = "剩余电流曲线";
            seriesNames = new String[1];
            seriesNames[0] = "剩余电流";
            colors = new String[1];
            colors[0] = "FF0000";
        }
        else if("21".equals(chartCategory)) {   // 温度曲线
            caption = "温度曲线";
            seriesNames = new String[1];
            seriesNames[0] = "温度";
            colors = new String[1];
            colors[0] = "FF0000";
        }
        return FusionChartsUtils.getChartData(caption, labelStep, seriesNames, colors, valueList, true, null, null);
    }

    /**
     * 
     * @param map
     * @return
     */
    private String getMapValue(Map<String, String> map) {
        if(map != null) {
            Iterator<String> iterator = map.keySet().iterator();
            String key = null;
            String value = null;
            if(iterator.hasNext()) {
                key = iterator.next();
                value = map.get(key);
                return value;
            }
        }
        return "";
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
        else if("21".equals(chartCategory)) {   // 温度曲线
            mapSeriesNames.put("BE16", "温度#FF0000");
        }
    }
}
