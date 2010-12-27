package org.pssframework.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TgInfoManager;
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
@RequestMapping("/mobile/lpt")
public class LeakageProtectorTrippingController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/lpt";

    @Autowired
    private TgInfoManager tgInfoManager;

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

        try {
            Long psId = Long.valueOf((String) mapRequest.get("psId"));

            PsInfo psInfo = psInfoManger.getById(psId);
            mav.addObject("psInfo", psInfo);

            GpInfo gpInfo = psInfo.getGpInfo();
            TranInfo tranInfo = null;
            if(gpInfo != null) {
                Long tgId = gpInfo.getObjectId();
                if(tgId != null) {
                    tranInfo = getTranInfo(tgId);
                }
            }
            if(tranInfo == null) {
                tranInfo = new TranInfo();
            }
            mav.addObject("tranInfo", tranInfo);

            mav.addObject("psModel", codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode()));
            mav.addObject("commModeGm", codeInfoManager.getCodeInfo("COMM_MODE_GM", psInfo.getCommModeGm()));
            mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));
            logger.info("mapRequest : " + mapRequest.toString());
        }
        catch(Exception _e) {
            logger.error("", _e.fillInStackTrace());
        }
        return mav;
    }

    private TranInfo getTranInfo(Long tgId) {
        TgInfo tgInfo = tgInfoManager.getById(tgId);
        List<TranInfo> tranInfoList = tgInfo.getTranInfos();
        if(tranInfoList.size() > 0)
            return tranInfoList.get(0);
        else
            return new TranInfo();
    }
}
