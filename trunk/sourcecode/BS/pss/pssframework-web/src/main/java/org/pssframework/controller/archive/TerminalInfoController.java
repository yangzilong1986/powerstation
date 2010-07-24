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
import javax.validation.Valid;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/terminalinfo")
public class TerminalInfoController extends BaseRestSpringController<TerminalInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addTerminal";

	/**
	 * binder用于bean属性的设置
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	/**
	 * 列表
	 */
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response,
	                    TerminalInfo terminalInfo) {

		return VIEW;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap model, @PathVariable Long id) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.terminalInfoManger.removeById(id);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE,msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE,msg);

		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap modelMap, @PathVariable Long id, HttpServletRequest request,
	                     HttpServletResponse response, @Valid TerminalInfo terminalInfo, BindingResult errors) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW;
		}
		try {
			TerminalInfo terminalInfoDb = this.terminalInfoManger.getById(id);
			bind(request, terminalInfoDb);
			this.terminalInfoManger.update(terminalInfoDb);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE,msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(msg);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
	                     @Valid TerminalInfo model, BindingResult errors) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW;
		}
		try {
			this.terminalInfoManger.saveOrUpdate(model);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE,msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(msg);

		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getOptionList(Map mapRequest) {
		return this.codeInfoManager.findByPageRequest(mapRequest);
	}

	/**
	 * 编辑
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id, HttpServletRequest request) throws Exception {
		this.getCommPart(result, new HashMap());

		result.addAttribute("terminalinfo", this.terminalInfoManger.getById(id));

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		result.addAttribute("tgId", request.getParameter("tgId"));

		return VIEW;
	}

	@SuppressWarnings("unchecked")
	/** 进入新增 */
	@RequestMapping(value = "/new")
	public String _new(ModelMap model, HttpServletRequest request, HttpServletResponse response,
	                   TerminalInfo terminalInfo) throws Exception {

		Map mapRequest = new HashMap();

		this.getCommPart(model, mapRequest);

		model.addAttribute("terminalinfo", terminalInfo);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		model.addAttribute("tgId", request.getParameter("tgId"));

		return VIEW;
	}

	@SuppressWarnings("unchecked")
	private void getCommPart(ModelMap result, Map mapRequest) {

		mapRequest.put("codecate", CODE_PROTOCOL_TERM);
		result.addAttribute("protocollist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_COMM_MODE);
		result.addAttribute("commlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_CUR_STATUS);
		result.addAttribute("statuslist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_TERM_TYPE);
		result.addAttribute("typelist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_MADE_FAC);
		result.addAttribute("faclist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_WIRING_MODE);
		result.addAttribute("wiringlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_PR);
		result.addAttribute("prlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_RUN_STATUS);
		result.addAttribute("runStatuslist", this.getOptionList(mapRequest));

	}
}
