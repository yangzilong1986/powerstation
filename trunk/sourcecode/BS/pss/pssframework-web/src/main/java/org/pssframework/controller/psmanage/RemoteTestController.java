package org.pssframework.controller.psmanage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.PsInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Zhangyu
 * 
 */
@Controller
@RequestMapping("/psmanage/rmttest")
public class RemoteTestController extends BaseRestSpringController<PsInfo, Long> {
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
}
