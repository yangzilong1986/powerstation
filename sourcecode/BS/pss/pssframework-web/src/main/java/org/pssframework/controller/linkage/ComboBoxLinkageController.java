package org.pssframework.controller.linkage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.service.archive.TgInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * 下拉框联动
 * 
 * @author Nick
 *
 */
@Controller
@RequestMapping("/linkage/cblinkage")
public class ComboBoxLinkageController extends BaseSpringController {
    private static final String VIEW_NAME_TG_BY_ORG = "/cblinkage/tgLinkedByOrg";

    @Autowired
    private TgInfoManager tgInfoManager;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tgLinkedByOrg")
    public ModelAndView tgLinkedByOrg(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> mapRequest = new HashMap<String, Object>();
        mapRequest = WebUtils.getParametersStartingWith(request, "");
        Long orgId = (mapRequest.get("orgId") != null && !mapRequest.get("orgId").toString().trim().equals("") ? Long
                .valueOf(mapRequest.get("orgId").toString()) : null);
        Long tgId = (mapRequest.get("tgId") != null && !mapRequest.get("tgId").toString().trim().equals("") ? Long
                .valueOf(mapRequest.get("tgId").toString()) : null);
        if(orgId == null || new Long(0).equals(orgId)) {
            mapRequest.remove("orgId");
        }
        mapRequest.remove("tgId");
        List<TgInfo> tglist = tgInfoManager.findByPageRequest(mapRequest);
        mav.addObject("defType", mapRequest.get("defType"));
        mav.addObject("formId", mapRequest.get("formId"));
        mav.addObject("formName", mapRequest.get("formName"));
        mav.addObject("actionChange", mapRequest.get("actionChange"));
        mav.addObject("orgId", orgId);
        mav.addObject("tgId", tgId);
        mav.addObject("tglist", tglist);
        mav.setViewName(VIEW_NAME_TG_BY_ORG);
        return mav;
    }
}
