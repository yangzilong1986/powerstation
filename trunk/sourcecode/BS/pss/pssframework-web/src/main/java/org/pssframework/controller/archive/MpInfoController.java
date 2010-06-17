/**
 * 
 */
package org.pssframework.controller.archive;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.service.archive.MpInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
*变压器信息
 */
@Controller
@RequestMapping("/archive/mpinfo")
public class MpInfoController extends BaseRestSpringController<MpInfo, java.lang.Long> {

	@Autowired
	private MpInfoManger mpInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, MpInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, MpInfo model) throws Exception {
		boolean isSucc = true;
		String msg = "成功";
		Long mpId = 0L;
		try {
			mpInfoManger.saveOrUpdate(model);
			mpId = model.getMpId();
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();

		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("mpId", mpId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, MpInfo model) throws Exception {
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
