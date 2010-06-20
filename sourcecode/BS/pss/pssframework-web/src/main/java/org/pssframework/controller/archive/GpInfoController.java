/**
 *
 */
package org.pssframework.controller.archive;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.service.archive.GpInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *         变压器信息
 */
@Controller
@RequestMapping("/archive/gpinfo")
public class GpInfoController extends BaseRestSpringController<GpInfo, java.lang.Long> {

    @Autowired
    private GpInfoManger gpInfoManger;

    @Autowired
    private CodeInfoManager codeInfoManager;

    private static final String SUCC = "成功";

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, GpInfo model) {

        Map mapRequest = new HashMap();

        Long tgid = 0L;

        // mapRequest.put("orgid", orgid);

        mapRequest.put("codecate", "TG_STATUS");

        // GpInfo tginfo = this.tranInfoManager.getById(tgid) == null ? new
        // GpInfo() : this.tgInfoManager.getById(tgid);
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
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, GpInfo model) throws Exception {
        boolean isSucc = true;
        String msg = SUCC;
        Long gpId = 0L;
        try {
            gpInfoManger.saveOrUpdate(model);
            gpId = model.getGpId();
        } catch (Exception e) {
            isSucc = false;
            msg = e.getMessage();

        }

        return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("gpid", model.getGpId());
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, GpInfo model) throws Exception {
        ModelAndView result = new ModelAndView();

        Map mapRequest = new HashMap();

        mapRequest.put("codecate", "TRAN_CODE");

        result.addObject("typelist", codeInfoManager.findByPageRequest(mapRequest));

        mapRequest.put("codecate", "TRAN_STATUS");

        result.addObject("statuslist", codeInfoManager.findByPageRequest(mapRequest));

        mapRequest.put("codecate", "VOLT_GRADE");

        result.addObject("voltlist", codeInfoManager.findByPageRequest(mapRequest));

        mapRequest.put("codecate", "RATED_EC");

        result.addObject("ratedlist", codeInfoManager.findByPageRequest(mapRequest));

        result.addObject("traninfo", model);

        result.setViewName("/archive/addTransformer");

        return result;
    }
}
