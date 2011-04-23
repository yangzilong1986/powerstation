package org.pssframework.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.system.OrgInfoManager;
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
@RequestMapping("/mobile/clp")
public class ChooseLeakageProtectorController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/clp";
    private static final String ORGLIST = "orglist";
    private static final String TGLIST = "tglist";
    private static final String PSLIST = "pslist";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private TgInfoManager tgInfoManager;

    @Autowired
    private PsInfoManger psInfoManger;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping
    public ModelAndView index(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME);
        Map mapRequest = WebUtils.getParametersStartingWith(request, "");
        String orgId = (String) mapRequest.get("orgId");
        String tgId = (String) mapRequest.get("tgId");
        if(orgId != null && ("".equals(orgId.trim()) || "null".equals(orgId.trim()))) {
            mapRequest.remove("orgId");
        }
        if(tgId != null) {
            mapRequest.put("tgid", tgId);
        }
        logger.info("mapRequest : " + mapRequest.toString());
        mav.addObject(ORGLIST, orgInfoManager.findByPageRequest(mapRequest));
        mav.addObject(TGLIST, tgInfoManager.findByPageRequest(mapRequest));
        mav.addObject(PSLIST, psInfoManger.findByPageRequest(mapRequest));
        return mav;
    }
}
