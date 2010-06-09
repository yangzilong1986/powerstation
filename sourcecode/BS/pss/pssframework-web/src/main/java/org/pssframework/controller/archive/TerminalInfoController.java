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
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.system.CodeInfo;
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
@RequestMapping("/archive/terminalinfo")
public class TerminalInfoController extends BaseRestSpringController<TerminalInfo, java.lang.Long> {
    @Autowired
    private TerminalInfoManger terminalInfoManger;

    @Autowired
    private CodeInfoManager codeInfoManager;

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TerminalInfo model) {

        Map mapRequest = new HashMap();

        // mapRequest.put("orgid", orgid);

        mapRequest.put("codecate", "TG_STATUS");

        // TerminalInfo tginfo = this.terminalInfoManger.getById(tgid) == null ? new TerminalInfo() :
        // this.tgInfoManager.getById(tgid);
        //
        ModelAndView result = new ModelAndView();
        //
        // result.addObject("tginfo", tginfo);
        //
        // result.setViewName("/archive/addTgRelevance");
        //
        // result.addObject("orglist", getOrgOptions(mapRequest));
        //
        // result.addObject("statuslist", getStatusOptions(mapRequest));

        return result;
    }

    @Override
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TerminalInfo model)
            throws Exception {
        boolean isSucc = true;
        String msg = "成功";
        Long termId = 0L;
        try {
            terminalInfoManger.saveOrUpdate(model);
            termId = model.getTermId();
        }
        catch(Exception e) {
            isSucc = false;
            msg = e.getMessage();

        }

        return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("termId", termId);
    }

    @SuppressWarnings("unchecked")
    private List<CodeInfo> getOptionList(Map mapRequest) {
        return codeInfoManager.findByPageRequest(mapRequest);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TerminalInfo model)
            throws Exception {
        ModelAndView result = new ModelAndView();
        Map mapRequest = new HashMap();
        mapRequest.put("codecate", "PROTOCOL_TERM");
        result.addObject("protocollist", getOptionList(mapRequest));
        mapRequest.put("codecate", "COMM_MODE");
        result.addObject("commlist", getOptionList(mapRequest));
        mapRequest.put("codecate", "CUR_STATUS");
        result.addObject("statuslist", getOptionList(mapRequest));
        mapRequest.put("codecate", "TERM_TYPE");
        result.addObject("typelist", getOptionList(mapRequest));
        mapRequest.put("codecate", "MADE_FAC");
        result.addObject("faclist", getOptionList(mapRequest));
        mapRequest.put("codecate", "WIRING_MODE");
        result.addObject("wiringlist", getOptionList(mapRequest));
        mapRequest.put("codecate", "PR");
        result.addObject("prlist", getOptionList(mapRequest));
        result.addObject("terminalinfo", model);
        result.setViewName("/archive/addTerminal");
        return result;
    }
}
