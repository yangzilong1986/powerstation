package org.pssframework.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/mobile/lpm")
public class LeakageProtectorManageController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/lpm";

    @Autowired
    private PsInfoManger psInfoManger;

    @Autowired
    private CodeInfoManager codeInfoManager;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping
    public ModelAndView index(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME);
        Map mapRequest = WebUtils.getParametersStartingWith(request, "");
        logger.info("mapRequest : " + mapRequest.toString());
        String sPsId = (String) mapRequest.get("psId");
        if(sPsId != null) {
            Long psId = Long.parseLong(sPsId);
            PsInfo psInfo = psInfoManger.getById(psId);
            mav.addObject("psInfo", psInfo);
            mav.addObject("psModel", codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode()));
            mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));
        }
        return mav;
    }
}
