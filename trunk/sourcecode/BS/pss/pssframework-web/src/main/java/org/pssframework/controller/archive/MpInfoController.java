/**
 * 
 */
package org.pssframework.controller.archive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.MpInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 计量点
 */
@Controller
@RequestMapping("/archive/mpinfo")
public class MpInfoController extends BaseRestSpringController<MpInfo, java.lang.Long> {

    @Autowired
    private MpInfoManger mpInfoManger;

    @Autowired
    private CodeInfoManager codeInfoManager;

    @Autowired
    private TerminalInfoManger terminalInfoManger;

    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, MpInfo model) {

        ModelAndView result = new ModelAndView();

        return result;
    }

    @Override
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, MpInfo model) throws Exception {
        boolean isSucc = true;
        String msg = CREATED_SUCCESS;
        Long mpId = 0L;
        try {
            mpInfoManger.saveOrUpdate(model);
            mpId = model.getMpId();
        }
        catch(Exception e) {
            isSucc = false;
            msg = e.getMessage();

        }

        return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("mpId", mpId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, MpInfo model) throws Exception {
        ModelAndView result = new ModelAndView();

        String tgid = request.getParameter("tgId");

        Map requestMap = new HashMap();

        requestMap.put("tgid", tgid);

        result.addObject("mpinfo", model);

        result.addObject("tgId", tgid);

        CommonPart(result, requestMap);

        result.addObject("_type", "new");

        return result;
    }

    /**
     * 
     * @param model
     * @param mapRequest
     */
    @SuppressWarnings("unchecked")
    private void CommonPart(ModelAndView result, Map mapRequest) {
        List<TerminalInfo> termlist = terminalInfoManger.findByPageRequest(mapRequest);
        result.setViewName("/archive/addTransformer");
        result.addObject("termList", termlist);

        // CT
        mapRequest.put("codecate", "CT_RATIO");

        result.addObject("ctList", codeInfoManager.findByPageRequest(mapRequest));

        // PT
        mapRequest.put("codecate", "PT_RATIO");

        result.addObject("ptList", codeInfoManager.findByPageRequest(mapRequest));

        // 通讯方式
        mapRequest.put("codecate", "COMM_MODE");

        result.addObject("commModeList", codeInfoManager.findByPageRequest(mapRequest));

        // 波特率
        mapRequest.put("codecate", "BTL");

        result.addObject("btlList", codeInfoManager.findByPageRequest(mapRequest));

        // 接线方式
        mapRequest.put("codecate", "WIRING_MODE");

        result.addObject("wiringModeList", codeInfoManager.findByPageRequest(mapRequest));

        result.setViewName("/archive/addMpInfo");

    }
}
