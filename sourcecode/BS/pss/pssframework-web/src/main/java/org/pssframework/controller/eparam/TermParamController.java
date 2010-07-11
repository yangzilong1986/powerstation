package org.pssframework.controller.eparam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.eparam.TermParamInfo;
import org.pssframework.service.eparam.TermParamManager;
import org.pssframework.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.RealTimeProxy376;
import pep.bp.realinterface.mto.MessageTranObject;

/**
 * @author Zhangyu
 * 
 */
@Controller
@RequestMapping("/eparam/termparam")
public class TermParamController extends BaseRestSpringController<TermParamInfo, Long> {
    @Autowired
    private TermParamManager termParamManager;

    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TermParamInfo model) {
        Long termId = null;
        if(model.getTermId() != null) {
            termId = model.getTermId();
        }
        
        TermParamInfo termParamInfo = termParamManager.getById(termId) == null ? new TermParamInfo() : termParamManager.getById(termId);

        ModelAndView result = new ModelAndView();
        result.addObject("termParamInfo", termParamInfo);
        result.setViewName("/eparam/showTermParam");
        return result;
    }

    @Override
    public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TermParamInfo termParamInfo = termParamManager.getById(id);
        ModelAndView result = new ModelAndView();
        result.addObject("termParamInfo", termParamInfo);
        result.setViewName("/eparam/showTermParam");
        return result;
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/down")
    public void _down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // String termIdString = request.getParameter("termId");
        // System.out.println(termIdString);
        String dtoJSONString = request.getParameter("dto");
        MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(dtoJSONString);
        ICollectInterface ci = new RealTimeProxy376();
        long collectId = ci.writeEquipmentParameters(mto);
        logger.debug("collectId : " + collectId);
    }

    /**
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/up")
    public void _up(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termIdString = request.getParameter("termId");
        System.out.println(termIdString);
    }
}
