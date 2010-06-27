/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.MeterInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.MeterInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/meterinfo")
public class MeterInfoController extends BaseRestSpringController<MeterInfo, java.lang.Long> {

    @Autowired
    private MeterInfoManger meterInfoManager;

    @Autowired
    private CodeInfoManager codeInfoManager;

    @Autowired
    private TerminalInfoManger terminalInfoManger;

    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, MeterInfo model) {

        return new ModelAndView();
    }

    @Override
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, MeterInfo model)
            throws Exception {
        boolean isSucc = true;
        String msg = MSG_CREATED_SUCCESS;
        Long meterId = 0L;
        try {
            meterInfoManager.saveOrUpdate(model);
            meterId = model.getMeterId();
        }
        catch(Exception e) {
            isSucc = false;
            msg = e.getMessage();

        }

        return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("meterId", meterId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, MeterInfo model)
            throws Exception {
        ModelAndView result = new ModelAndView();

        String tgid = request.getParameter("tgId");

        Map requestMap = new HashMap();

        requestMap.put("tgid", tgid);

        result.addObject("psinfo", model);

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

    }
}
