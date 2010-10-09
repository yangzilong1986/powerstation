package org.pssframework.controller.tgmanage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.service.tgmanage.TGMonitorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Zhangyu
 *
 */
@Controller
@RequestMapping("/tgmanage/tgmon")
public class TGMonitorController extends BaseSpringController {
    private static final String VIEW_NAME_FRM = "/tgmanage/showTGMonitor";
    private static final String VIEW_NAME_MON = "/tgmanage/tgMonitor";
    private static final String VIEW_NAME_MON_TM = "/tgmanage/mon_TotalMeter";
    private static final String VIEW_NAME_MON_PS = "/tgmanage/mon_PS";
    private static final String VIEW_NAME_MON_AQ = "/tgmanage/mon_Analog";
    private static final String VIEW_NAME_MON_SW = "/tgmanage/mon_Switch";
    //private static final String VIEW_NAME_TRE = "/tgmanage/tgTree";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private TGMonitorManager tgMonitorManager;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/frame")
    public ModelAndView _frame(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_FRM);
        Map mapRequest = new LinkedHashMap();
        getInitOption(mav, mapRequest);
        return mav;
    }

    /**
     * 
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/tgmonitor/{id}")
    public ModelAndView _tgmonitor(ModelAndView mav, @PathVariable Long id) throws Exception {
        mav.setViewName(VIEW_NAME_MON);
        logger.info("tgId : " + id);
        List tmlist = tgMonitorManager.findCombBoxByTg(id, "getTotalMeterCombBoxByTgId");
        List pslist = tgMonitorManager.findCombBoxByTg(id, "getPSCombBoxByTgId");
        List aqlist = tgMonitorManager.findCombBoxByTg(id, "getAnalogCombBoxByTgId");
        logger.info("tmlist : " + tmlist.toString());
        logger.info("pslist : " + pslist.toString());
        logger.info("aqlist : " + aqlist.toString());
        mav.addObject("tmlist", tmlist);
        mav.addObject("pslist", pslist);
        mav.addObject("aqlist", aqlist);
        return mav;
    }

    /**
     * 总表监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tm")
    public ModelAndView _tm(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_TM);
        return mav;
    }

    /**
     * 漏保开关监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ps")
    public ModelAndView _ps(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_PS);
        return mav;
    }

    /**
     * 油温监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/aq")
    public ModelAndView _aq(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_AQ);
        return mav;
    }

    /**
     * 油温监测
     * @param mav
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sw")
    public ModelAndView _sw(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME_MON_SW);
        return mav;
    }

    /**
     * 初始化
     * @param model
     * @param mapRequest
     */
    private void getInitOption(ModelAndView mav, Map<String, ?> mapRequest) {
        mav.addObject("orglist", getOrgOptions(mapRequest));
    }

    /**
     * 
     * @param mapRequest
     * @return
     */
    private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
        return orgInfoManager.findByPageRequest(mapRequest);
    }
}
