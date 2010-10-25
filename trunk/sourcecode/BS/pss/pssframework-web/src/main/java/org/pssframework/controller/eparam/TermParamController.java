package org.pssframework.controller.eparam;

import java.util.LinkedHashMap;
import java.util.List;
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

    @SuppressWarnings("unchecked")
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

        Map mapRequest = new LinkedHashMap();
        getInitOption(result, mapRequest);

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
        String type = request.getParameter("type");
        if("setup".equals(type)) { // 设置
            String dtoJSONString = request.getParameter("dto");
            logger.info("dtoJSONString : " + dtoJSONString);
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(dtoJSONString);
            //ObjectMapper objectMapper = new ObjectMapper();
            //logger.info("mto json : " + objectMapper.writeValueAsString(mto));
            long collectId = realTimeProxy376.writeParameters(mto);
            logger.info("collectId : " + collectId);
            result.addObject("collectId", collectId);
            result.addObject("fetchCount", 10);
        }
        else {                     // 读取
            String dtoJSONString = request.getParameter("dto");
            logger.info("dtoJSONString : " + dtoJSONString);
            String mtoType = request.getParameter("mtoType");
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.readParameters(mto);
            logger.info("collectId : " + collectId);
            result.addObject("collectId", collectId);
            result.addObject("fetchCount", 10);
        }
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
        if("setup".equals(type)) { // 设置
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter(collectId);
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        else {                     // 读取
            String strCollectId = request.getParameter("collectId");
            if(strCollectId != null) {
                long collectId = Integer.parseInt(strCollectId);
                Map<String, Map<String, String>> resultMap = realTimeProxy376.getReturnByReadParameter(collectId);
                /*Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("1004000101", "1");
                tempMap.put("1004000102", "2");
                tempMap.put("1004000103", "3");
                tempMap.put("1004000104", "4");
                tempMap.put("1004000106", "6");
                tempMap.put("1004000107", "7");
                resultMap.put("96123456#0#10040001", tempMap);*/
                result.addObject("resultMap", resultMap);
                logger.info("resultMap : " + resultMap.toString());
            }
        }
        return result;
    }

    /**
     * 
     * @param model
     * @param mapRequest
     */
    private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {
        model.addObject("gpList", this.getGpOptions(mapRequest));
        model.addObject("agList", this.getAgOptions(mapRequest));
    }
    
    /**
     * 
     * @param mapRequest
     * @return
     */
    private List<?> getGpOptions(Map<String, ?> mapRequest) {
        return null;
    }

    /**
     * 
     * @param mapRequest
     * @return
     */
    private List<?> getAgOptions(Map<String, ?> mapRequest) {
        return null;
    }
}
