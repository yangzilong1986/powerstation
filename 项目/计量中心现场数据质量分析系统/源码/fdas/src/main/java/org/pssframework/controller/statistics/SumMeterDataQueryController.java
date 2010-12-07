package org.pssframework.controller.statistics;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/statistics/sumMeterDataQuery")
public class SumMeterDataQueryController extends BaseSpringController {
    private static final String VIEW_NAME = "/statistics/sumMeterDataQuery";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        modelAndView.setViewName(VIEW_NAME);

        Map mapRequest = new LinkedHashMap();
        getInitOption(modelAndView, mapRequest);
        modelAndView.addObject("qdate", DateUtils.getYestDate());

        return modelAndView;
    }

    /**
     * 下拉框
     *
     * @param model
     * @param mapRequest
     */
    private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {
        model.addObject("orglist", this.getOrgOptions(mapRequest));
    }

    private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
        return this.orgInfoManager.findByPageRequest(mapRequest);
    }
}
