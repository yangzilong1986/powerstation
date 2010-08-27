package org.pssframework.controller.psmanage;

import static org.pssframework.support.system.SystemConst.CODE_PS_MODEL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.psmanage.PSTreeManager;
import org.pssframework.service.system.CodeInfoManager;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.MessageTranObject;

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
        logger.info("tdId : " + id);
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
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/psmonitor/{id}")
    public ModelAndView _psmonitor(ModelAndView mav, @PathVariable Long id) throws Exception {
        mav.setViewName(VIEW_NAME_MON);
        logger.info("psId : " + id);
        PsInfo psInfo = psInfoManger.getById(id);
        mav.addObject("psInfo", psInfo);

        Map<String, Comparable> requestMap = new HashMap<String, Comparable>();

        // 漏保型号
        requestMap.put("codecate", CODE_PS_MODEL);
        mav.addObject("psModelList", this.codeInfoManager.findByPageRequest(requestMap));

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
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else if("RemoteSwitching".equals(type)) { // RemoteSwitching C036 5F
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
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
}
