/**
 * 
 */
package org.pssframework.controller.archive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TermObjRelaInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/terminalinfo")
public class TerminalInfoController extends BaseRestSpringController<TerminalInfo, java.lang.Long> {

	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		super.initBinder(request, binder);
	}

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TerminalInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TerminalInfo model)
			throws Exception {
		boolean isSucc = true;
		String msg = "成功";
		Long termId = 0L;
		try {
			// TermObjRelaInfo termObjRelaInfo = new TermObjRelaInfo();
			// termObjRelaInfo.setObjType("2");
			// termObjRelaInfo.setObjId(Long.parseLong(request.getParameter("tgId")));
			// List<TermObjRelaInfo> termObjRelaInfos = new
			// ArrayList<TermObjRelaInfo>();
			// termObjRelaInfo.setTerminalInfo(model);
			// termObjRelaInfos.add(termObjRelaInfo);
			// model.setTermObjRelas(termObjRelaInfos);

			for (TermObjRelaInfo termObjRelaInfo : model.getTermObjRelas()) {
				termObjRelaInfo.setTerminalInfo(model);
			}
			terminalInfoManger.saveOrUpdate(model);
			termId = model.getTermId();
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();
			logger.error(e.getMessage());

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
		TermObjRelaInfo termObjRelaInfo = new TermObjRelaInfo();

		termObjRelaInfo.setObjType("2");

		termObjRelaInfo.setObjId(Long.parseLong(request.getParameter("tgId")));

		List<TermObjRelaInfo> termObjRelas = new ArrayList<TermObjRelaInfo>();

		termObjRelas.add(termObjRelaInfo);

		model.setTermObjRelas(termObjRelas);

		ModelAndView result = new ModelAndView();

		Map mapRequest = new HashMap();

		getCommPart(result, mapRequest);

		result.addObject("terminalinfo", model);

		result.addObject("_type", "new");

		result.addObject("tgId", request.getParameter("tgId"));

		result.setViewName("/archive/addTerminal");

		return result;
	}

	@SuppressWarnings("unchecked")
	private void getCommPart(ModelAndView result, Map mapRequest) {
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

	}
}
