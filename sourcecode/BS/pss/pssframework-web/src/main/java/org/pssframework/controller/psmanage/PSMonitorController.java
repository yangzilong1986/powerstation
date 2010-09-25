package org.pssframework.controller.psmanage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.query.psmanage.PSMonitorQuery;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.psmanage.PSTreeManager;
import org.pssframework.service.statistics.StatisticsManager;
import org.pssframework.service.statistics.StatisticsType;
import org.pssframework.service.system.CodeInfoManager;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.ConverterUtils;
import org.pssframework.util.DateUtils;
import org.pssframework.util.FusionChartsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.MessageTranObject;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Zhangyu
 * 
 */
@Controller
@RequestMapping("/psmanage/psmon")
public class PSMonitorController extends BaseSpringController {
    private static final String VIEW_NAME_FRM = "/psmanage/showPSMonitor";
    private static final String VIEW_NAME_MON = "/psmanage/psMonitor";
    private static final String VIEW_NAME_TRE = "/psmanage/psTree";
    private static final String VIEW_EVENT_QUERY = "/psmanage/eventQuery";
    private static final String VIEW_ECCURV_QUERY = "/psmanage/ecCurvQuery";
    private static final String VIEW_ECCURV_QUERY_CHART = "/psmanage/ecCurvQuery_Chart";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private TgInfoManager tgInfoManager;

    @Autowired
    private PSTreeManager psTreeManager;

    @Autowired
    private PsInfoManger psInfoManger;

    @Autowired
    private CodeInfoManager codeInfoManager;

    @Autowired
    private ICollectInterface realTimeProxy376;

    @Autowired
    private StatisticsManager statisticsManager;

    // 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS_EVENT = "trigTime desc";
    protected static final String DEFAULT_SORT_COLUMNS_PSDATA = "dataTime asc";
    protected static final String DEFAULT_SORT_COLUMNS_PSDATA_CHART = "dataTime asc";

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
     * 漏保对象树
     * 
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstree/{id}")
    public ModelAndView _pstree(ModelAndView mav, @PathVariable Long id) throws Exception {
        // logger.info("tdId : " + id);
        mav.setViewName(VIEW_NAME_TRE);
        mav.addObject("tree", psTreeManager.findPSTreeByTgId(id));
        return mav;
    }

    /**
     * 漏保监测
     * 
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psmonitor/{id}")
    public ModelAndView _psmonitor(ModelAndView mav, @PathVariable Long id) throws Exception {
        mav.setViewName(VIEW_NAME_MON);
        // logger.info("psId : " + id);

        PsInfo psInfo = psInfoManger.getById(id);
        mav.addObject("psInfo", psInfo);

        GpInfo gpInfo = psInfo.getGpInfo();
        TranInfo tranInfo = null;
        if(gpInfo != null) {
            Long tgId = gpInfo.getObjectId();
            if(tgId != null) {
                tranInfo = getTranInfo(tgId);
            }
        }
        if(tranInfo == null) {
            tranInfo = new TranInfo();
        }
        mav.addObject("tranInfo", tranInfo);

        mav.addObject("psModel", codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode()));
        mav.addObject("commModeGm", codeInfoManager.getCodeInfo("COMM_MODE_GM", psInfo.getCommModeGm()));
        mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));
        mav.addObject("sdate", DateUtils.getCurrentDate());
        mav.addObject("edate", DateUtils.getCurrentDate());

        return mav;
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

    private TranInfo getTranInfo(Long tgId) {
        TgInfo tgInfo = tgInfoManager.getById(tgId);
        List<TranInfo> tranInfoList = tgInfo.getTranInfos();
        if(tranInfoList.size() > 0)
            return tranInfoList.get(0);
        else
            return new TranInfo();
    }

    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/down")
    public ModelAndView _down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        String dtoJSONString = request.getParameter("dto");
        logger.info("dtoJSONString : " + dtoJSONString);
        String mtoType = request.getParameter("mtoType");
        MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
        long collectId = realTimeProxy376.transmitMsg(mto);
        logger.info("collectId : " + collectId);
        result.addObject("collectId", collectId);
        result.addObject("fetchCount", 10);
        return result;
    }

    /**
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/up")
    public ModelAndView _up(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        String type = request.getParameter("type");
        if("ReadB66F".equals(type)) { // ReadB66F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitPara(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("ReadC04F".equals(type)) { // ReadC04F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitPara(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("RemoteTriping".equals(type)) { // RemoteTriping C036 50
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitWriteBack(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("RemoteSwitching".equals(type)) { // RemoteSwitching C036 5F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitWriteBack(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("RemoteTest".equals(type)) { // RemoteTest C037
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("TimeRead".equals(type)) { // TimeRead C012
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitPara(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("TimeSetup".equals(type)) { // TimeSetup C012
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("FuncSetupByteRead".equals(type)) { // FuncSetupByteRead C04F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitPara(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("FuncSetupByteSetup".equals(type)) { // FuncSetupByteSetup C04F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("PSTotalParamsRead".equals(type)) { // PSTotalParamsRead C04F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitPara(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("PSTotalParamsSetup".equals(type)) { // PSTotalParamsSetup C04F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        return result;
    }

    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/time")
    public ModelAndView _time(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String computerTime = sdf.format(new Date());

        result.addObject("computerTime", computerTime);

        return result;
    }

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @param psMonitorQuery
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/eventQuery")
    public ModelAndView _eventQuery(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            PSMonitorQuery psMonitorQuery) throws Exception {
        PageRequest<Map> pageRequest = bindPageRequest(request, psMonitorQuery, DEFAULT_SORT_COLUMNS_EVENT);
        Page page = statisticsManager.findByPageRequest(pageRequest, StatisticsType.PsEvent);// 获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        mav.addObject("psMonitorQuery", psMonitorQuery);
        mav.setViewName(VIEW_EVENT_QUERY);
        return mav;
    }

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @param psMonitorQuery
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ecCurvQuery")
    public ModelAndView _ecCurvQuery(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            PSMonitorQuery psMonitorQuery) throws Exception {
        PageRequest<Map> pageRequest = bindPageRequest(request, psMonitorQuery, DEFAULT_SORT_COLUMNS_PSDATA);
        Page page = statisticsManager.findByPageRequest(pageRequest, StatisticsType.PsEcCurv);// 获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        mav.addObject("psMonitorQuery", psMonitorQuery);
        initActionType(mav);
        mav.setViewName(VIEW_ECCURV_QUERY);
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
     * 
     * @param mav
     * @param request
     * @param response
     * @param psMonitorQuery
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ecCurvQuery_Chart")
    public ModelAndView _ecCurvQuery_Chart(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
            PSMonitorQuery psMonitorQuery) throws Exception {
        PageRequest<Map> pageRequest = bindPageRequest(request, psMonitorQuery, DEFAULT_SORT_COLUMNS_PSDATA_CHART);
        Page page = statisticsManager.findChartByPageRequest(pageRequest, StatisticsType.PsEcCurv);// 获取数据模型
        mav.addObject("page", page);
        mav.addObject("pageRequest", pageRequest);
        mav.addObject("psMonitorQuery", psMonitorQuery);

        String chartCategory = request.getParameter("chartCategory");
        Map<String, String> mapSeriesNames = new HashMap<String, String>();
        initChartSeriesNames(mapSeriesNames, chartCategory);

        String chartType = request.getParameter("chartType");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("caption", "漏保数据曲线");
        mapParams.put("contextPath", request.getContextPath());
        mapParams.put("chartType", chartType);
        mapParams.put("width", width);
        mapParams.put("height", height);
        mav.addObject("chart", FusionChartsUtils.getChart(page.getResult(), mapSeriesNames, mapParams,
                                                          "org.pssframework.model.statistics.PsEcCurv"));
        mav.setViewName(VIEW_ECCURV_QUERY_CHART);
        return mav;
    }

    private void initChartSeriesNames(Map<String, String> mapSeriesNames, String chartCategory) {
        if("1".equals(chartCategory)) {      // 电压数据
            mapSeriesNames.put("voltA", "A相电压#FF0000");
            mapSeriesNames.put("voltB", "B相电压#FFFF00");
            mapSeriesNames.put("voltC", "C相电压#0000FF");
        }
        else if("2".equals(chartCategory)) { // 电流数据
            mapSeriesNames.put("ecurA", "A相电流#FF0000");
            mapSeriesNames.put("ecurB", "B相电流#FFFF00");
            mapSeriesNames.put("ecurC", "C相电流#0000FF");
        }
        else if("3".equals(chartCategory)) { // 剩余电流
            mapSeriesNames.put("ecurS", "剩余电流#FF0000");
        }
    }
}
