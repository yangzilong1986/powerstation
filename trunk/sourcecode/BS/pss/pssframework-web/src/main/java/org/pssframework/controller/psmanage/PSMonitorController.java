package org.pssframework.controller.psmanage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.PsInfo;
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
public class PSMonitorController extends BaseRestSpringController<PsInfo, Long> {
    @Autowired
    private ICollectInterface realTimeProxy376;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, PsInfo model) {
        ModelAndView result = new ModelAndView();
        result.addObject("psInfo", model);
        result.setViewName("/psmanage/showPSMonitor");
        return result;
    }

    public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PsInfo psInfo = new PsInfo();
        ModelAndView result = new ModelAndView();
        result.addObject("psInfo", psInfo);
        result.setViewName("/psmanage/showPSMonitor");
        return result;
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
        result.addObject("fetchCount", 5);
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
                Map<String, Map<String, String>> resultMap = realTimeProxy376.readTransmitData(collectId);
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
        return result;
    }

    /**
     * 漏保对象树
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstree")
    public ModelAndView _pstree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();

        return result;
    }

    /**
     * 漏保数据（电压、电流、剩余电流、参数值）
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psdatainfo")
    public ModelAndView _psdatainfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保跳合闸
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstrip")
    public ModelAndView _pstrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保试验跳
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstest")
    public ModelAndView _pstest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保时间设置
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstimeSetup")
    public ModelAndView _pstimeSetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        // String dtoJSONString = request.getParameter("dto");
        String dtoJSONString = "{\"collectObjects\":[{\"logicalAddr\":\"96123456\",\"equipProtocol\":\"100\",\"channelType\":\"1\",\"pwAlgorith\":\"0\",\"pwContent\":\"8888\",\"mpExpressMode\":\"3\",\"mpSn\":[\"0\"],\"commandItems\":[{\"identifier\":\"10040001\"}]}]}";
        logger.info("dtoJSONString : " + dtoJSONString);

        return result;
    }

    /**
     * 漏保时间读取
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstimeRead")
    public ModelAndView _pstimeRead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保状态读设
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psstatusSetup")
    public ModelAndView _psstatusSetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保参数读设
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psparamsSetup")
    public ModelAndView _psparamsSetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保跳闸信息
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstripInfo")
    public ModelAndView _pstripInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }
}
