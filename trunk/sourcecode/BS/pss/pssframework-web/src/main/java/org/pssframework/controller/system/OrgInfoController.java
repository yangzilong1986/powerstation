package org.pssframework.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/system/org")
public class OrgInfoController extends BaseRestSpringController<OrgInfo, Long> {

    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;

    private OrgInfoManager orgInfoManager;

    /** 
     * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
     **/
    public void setOrgInfoManager(OrgInfoManager manager) {
        this.orgInfoManager = manager;
    }

    @SuppressWarnings("unchecked")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, OrgInfo model) {
        Map fiterMap = new HashMap();
        List orglist = this.orgInfoManager.findByPageRequest(fiterMap);
        ModelAndView result = new ModelAndView();
        result.addObject("orginfo", orglist);
        result.setViewName("/system/orgList");
        return result;
    }
}
