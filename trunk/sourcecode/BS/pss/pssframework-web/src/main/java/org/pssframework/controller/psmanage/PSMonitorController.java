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
@RequestMapping("/psmanage/psmon")
public class PSMonitorController extends BaseRestSpringController<PsInfo, Long> {
    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, PsInfo model) {
        ModelAndView result = new ModelAndView();
        result.addObject("psInfo", model);
        result.setViewName("/psmanage/showPSMonitor");
        return result;
    }

    @Override
    public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PsInfo psInfo = new PsInfo();
        ModelAndView result = new ModelAndView();
        result.addObject("psInfo", psInfo);
        result.setViewName("/psmanage/showPSMonitor");
        return result;
    }

    /**
     * 漏保对象树
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstree")
    public ModelAndView _pstree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();

        return result;
    }

    /**
     * 漏保基本信息
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psinfo")
    public ModelAndView _psinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保跳闸
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstrip")
    public ModelAndView _pstrip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保电压数据
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psvolt")
    public ModelAndView _psvolt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保电流数据
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psec")
    public ModelAndView _psec(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保参数信息
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psparam")
    public ModelAndView _psparam(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保时间读设
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstimeSetup")
    public ModelAndView _pstimeSetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保状态读设
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psstatusSetup")
    public ModelAndView _psstatusSetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保参数读设
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/psparamsSetup")
    public ModelAndView _psparamsSetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }

    /**
     * 漏保跳闸信息
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pstripInfo")
    public ModelAndView _pstripInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView result = new ModelAndView();
        return result;
    }
}
