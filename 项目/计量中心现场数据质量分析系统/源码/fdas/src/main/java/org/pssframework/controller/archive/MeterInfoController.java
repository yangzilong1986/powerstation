/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_BTL;
import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE;
import static org.pssframework.support.system.SystemConst.CODE_CT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_PT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_WIRING_MODE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/meterinfo")
public class MeterInfoController extends BaseRestSpringController<MeterInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addTransformer";

	@Autowired
	private MeterInfoManger meterInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, MeterInfo model) {

		return new ModelAndView();
	}

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, MeterInfo model)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long meterId = 0L;
		try {
			this.meterInfoManager.saveOrUpdate(model);
			meterId = model.getMeterId();
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg)
				.addObject("meterId", meterId);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, MeterInfo model)
			throws Exception {
		ModelAndView result = new ModelAndView();

		String tgid = request.getParameter("tgId");

		Map requestMap = new HashMap();

		requestMap.put("tgid", tgid);

		result.addObject("psinfo", model);

		result.addObject("tgId", tgid);

		this.CommonPart(result, requestMap);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return result;
	}

	/**
	 * 
	 * @param model
	 * @param mapRequest
	 */
	@SuppressWarnings("unchecked")
	private void CommonPart(ModelAndView result, Map mapRequest) {
		List<TerminalInfo> termlist = this.terminalInfoManger.findByPageRequest(mapRequest);
		result.setViewName(VIEW);
		result.addObject("termList", termlist);

		// CT
		mapRequest.put("codecate", CODE_CT_RATIO);

		result.addObject("ctList", this.codeInfoManager.findByPageRequest(mapRequest));

		// PT
		mapRequest.put("codecate", CODE_PT_RATIO);

		result.addObject("ptList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", CODE_COMM_MODE);

		result.addObject("commModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", CODE_BTL);

		result.addObject("btlList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 接线方式
		mapRequest.put("codecate", CODE_WIRING_MODE);

		result.addObject("wiringModeList", this.codeInfoManager.findByPageRequest(mapRequest));

	}
}
