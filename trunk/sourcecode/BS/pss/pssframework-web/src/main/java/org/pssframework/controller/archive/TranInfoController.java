/**
 * 
 */
package org.pssframework.controller.archive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.archive.TranInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
*变压器信息
 */
@Controller
@RequestMapping("/archive/traninfo")
public class TranInfoController extends BaseRestSpringController<TranInfo, java.lang.Long> {

	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		super.initBinder(request, binder);
	}

	@Autowired
	private TranInfoManger tranInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TgInfoManager tgInfoManager;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TranInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TranInfo model)
			throws Exception {
		boolean isSucc = true;
		String msg = "成功";
		Long tranid = 0L;
		try {
			model.setPubPrivFlag("0");
			tranInfoManager.saveOrUpdate(model);
			tranid = model.getEquipId();
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();

		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("tranid", tranid);
	}

	@Override
	public ModelAndView show(@PathVariable Long id) throws Exception {
		ModelAndView result = getCommonModelAndView();

		result.addObject("traninfo", tranInfoManager.getById(id));

		result.setViewName("/archive/addTransformer");

		TranInfo traninfo = this.tranInfoManager.getById(id) == null ? new TranInfo() : this.tranInfoManager
				.getById(id);
		result.addObject("traninfo", traninfo);

		return result;
	}

	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TranInfo model) throws Exception {
		ModelAndView result = getCommonModelAndView();

		TgInfo tgInfo = tgInfoManager.getById(Long.parseLong(request.getParameter("tgId")));

		result.addObject("tgInfo", tgInfo);
		result.addObject("traninfo", model);

		result.setViewName("/archive/addTransformer");
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getOptionList(Map mapRequest) {

		return codeInfoManager.findByPageRequest(mapRequest);

	}

	@SuppressWarnings("unchecked")
	private ModelAndView getCommonModelAndView() {
		ModelAndView result = new ModelAndView();

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TRAN_CODE");

		result.addObject("typelist", getOptionList(mapRequest));

		mapRequest.put("codecate", "TRAN_STATUS");

		result.addObject("statuslist", getOptionList(mapRequest));

		mapRequest.put("codecate", "VOLT_GRADE");

		result.addObject("voltlist", getOptionList(mapRequest));

		mapRequest.put("codecate", "RATED_EC");

		result.addObject("ratedlist", getOptionList(mapRequest));

		return result;
	}
}
