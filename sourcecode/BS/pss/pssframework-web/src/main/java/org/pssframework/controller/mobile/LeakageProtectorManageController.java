package org.pssframework.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
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
        return mav;
    }
}
