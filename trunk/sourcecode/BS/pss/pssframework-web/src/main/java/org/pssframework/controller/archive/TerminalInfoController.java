/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE;
import static org.pssframework.support.system.SystemConst.CODE_CUR_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_MADE_FAC;
import static org.pssframework.support.system.SystemConst.CODE_PR;
import static org.pssframework.support.system.SystemConst.CODE_PROTOCOL_TERM;
import static org.pssframework.support.system.SystemConst.CODE_RUN_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_TERM_TYPE;
import static org.pssframework.support.system.SystemConst.CODE_WIRING_MODE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/terminalinfo")
public class TerminalInfoController extends BaseRestSpringController<TerminalInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addTerminal";


	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;


	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TerminalInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}


	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.terminalInfoManger.removeById(id);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg);
	}


	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		try {
			TerminalInfo terminalInfo = this.terminalInfoManger.getById(id);
			//this.bind(request, terminalInfo);
			this.terminalInfoManger.saveOrUpdate(terminalInfo);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
			this.logger.error(e.getMessage());

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg);
	}


	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TerminalInfo model)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		try {
			this.terminalInfoManger.saveOrUpdate(model);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			this.logger.error(e.getMessage());

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg);
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getOptionList(Map mapRequest) {
		return this.codeInfoManager.findByPageRequest(mapRequest);
	}

	@SuppressWarnings("unchecked")

	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = new ModelAndView();

		this.getCommPart(result, new HashMap());

		result.addObject("terminalinfo", this.terminalInfoManger.getById(id));

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		result.addObject("tgId", request.getParameter("tgId"));

		result.setViewName(VIEW);

		return result;
	}

	@SuppressWarnings("unchecked")

	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TerminalInfo model)
			throws Exception {

		ModelAndView result = new ModelAndView();

		Map mapRequest = new HashMap();

		this.getCommPart(result, mapRequest);

		result.addObject("terminalinfo", model);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		result.addObject("tgId", request.getParameter("tgId"));

		result.setViewName(VIEW);

		return result;
	}

	@SuppressWarnings("unchecked")
	private void getCommPart(ModelAndView result, Map mapRequest) {

		mapRequest.put("codecate", CODE_PROTOCOL_TERM);
		result.addObject("protocollist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_COMM_MODE);
		result.addObject("commlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_CUR_STATUS);
		result.addObject("statuslist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_TERM_TYPE);
		result.addObject("typelist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_MADE_FAC);
		result.addObject("faclist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_WIRING_MODE);
		result.addObject("wiringlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_PR);
		result.addObject("prlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_RUN_STATUS);
		result.addObject("runStatuslist", this.getOptionList(mapRequest));

	}
}
