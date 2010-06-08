/**
 * 
 */
package org.pssframework.controller.archive;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.service.archive.TranInfoManger;
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
@RequestMapping("/archive/psinfo")
public class PsInfoController extends BaseRestSpringController<TranInfo, java.lang.Long> {
	@Autowired
	private TranInfoManger tranInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TranInfo model) {

		Map mapRequest = new HashMap();

		Long tgid = 0L;
		if (model.getTgId() != null) {
			tgid = model.getTgId();
		}

		String runstatcode = "1";
		if (model.getRunStatusCode() != null) {
			runstatcode = model.getRunStatusCode();
		}

		//mapRequest.put("orgid", orgid);

		mapRequest.put("codecate", "TG_STATUS");

		//		TranInfo tginfo = this.tranInfoManager.getById(tgid) == null ? new TranInfo() : this.tgInfoManager.getById(tgid);
		//
		ModelAndView result = new ModelAndView();
		//
		//		result.addObject("tginfo", tginfo);
		//
		//		result.setViewName("/archive/addTgRelevance");
		//
		//		result.addObject("orglist", getOrgOptions(mapRequest));
		//
		//		result.addObject("statuslist", getStatusOptions(mapRequest));

		return result;
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TranInfo model)
			throws Exception {
		boolean isSucc = true;
		String msg = "成功";
		Long tgId = 0L;
		try {
			model.setPubPrivFlag("0");
			tranInfoManager.saveOrUpdate(model);
			tgId = model.getTgId();
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();

		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("tgId", tgId);
	}


	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TranInfo model) throws Exception {
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
