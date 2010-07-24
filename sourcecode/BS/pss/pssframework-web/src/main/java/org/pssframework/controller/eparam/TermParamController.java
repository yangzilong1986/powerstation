package org.pssframework.controller.eparam;

import java.util.Map;

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

	@Autowired
	private ICollectInterface realTimeProxy376;

	@RequestMapping
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
    public ModelAndView _down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        String dtoJSONString = request.getParameter("dto");
        MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(dtoJSONString);
        long collectId = realTimeProxy376.writeParameters(mto);
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
            Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter(collectId);
            /*
             * Map<String, String> resultMap = new HashMap<String, String>(); resultMap.put("96123456#0#10040001",
             * "000000"); resultMap.put("96123456#0#10040003", "000000"); resultMap.put("96123456#0#10040004",
             * "000000"); resultMap.put("96123456#0#10040008", "000000"); resultMap.put("96123456#0#10040009",
             * "000000");
             */
            result.addObject("resultMap", resultMap);
        }
        return result;
    }
}
