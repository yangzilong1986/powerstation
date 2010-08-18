package org.pssframework.controller.homepage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/homepage/homepage")
public class HomepageController extends BaseSpringController {
    private static final String VIEW_NAME = "/homepage/homepage";

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        return modelAndView;
    }
}
