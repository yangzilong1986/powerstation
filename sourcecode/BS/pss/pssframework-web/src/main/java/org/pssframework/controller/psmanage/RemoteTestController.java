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
@RequestMapping("/psmanage/rmttest")
public class RemoteTestController extends BaseRestSpringController<PsInfo, Long> {

    @Autowired
    private ICollectInterface realTimeProxy376;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, PsInfo model) {
        ModelAndView result = new ModelAndView();
        result.addObject("psInfo", model);
        result.setViewName("/psmanage/showRemoteTest");
        return result;
    }

    public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PsInfo psInfo = new PsInfo();
        ModelAndView result = new ModelAndView();
        result.addObject("psInfo", psInfo);
        result.setViewName("/psmanage/showRemoteTest");
        return result;
    }

    /**
     * 
     * @param request
     * @param response
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
        String strCollectId = request.getParameter("collectId");
        if(strCollectId != null) {
            long collectId = Integer.parseInt(strCollectId);
            Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
            result.addObject("resultMap", resultMap);
            logger.info("resultMap : " + resultMap.toString());
        }
        return result;
    }
}
