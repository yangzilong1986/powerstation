/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_BTL;
import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE;
import static org.pssframework.support.system.SystemConst.CODE_CT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_MEAS_MODE;
import static org.pssframework.support.system.SystemConst.CODE_METER_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_PROTOCOL_METER;
import static org.pssframework.support.system.SystemConst.CODE_PT_RATIO;
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
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.MpInfoManger;
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

import cn.org.rapid_framework.web.scope.Flash;

/**
 * @author Administrator 计量点
 */
@Controller
@RequestMapping("/archive/mpinfo")
public class MpInfoController extends BaseRestSpringController<MpInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addMpInfo";
	private static final String VALIDATE = "验证出错";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private MpInfoManger mpInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	/** 列表 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			org.pssframework.query.archive.MpQuery mpQuery) {

		return VIEW;
	}

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap modelMap, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response, @Valid MpInfo model) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW;
		}

		try {
			for (GpInfo gpInfo : model.getGpInfos()) {
				gpInfo.setObjectId(model.getTgInfo().getTgId());
				gpInfo.setMpInfo(model);
			}
			this.mpInfoManger.saveOrUpdate(model);
			Flash.current().success(MSG_CREATED_SUCCESS);
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();
			this.logger.error(e.getMessage());
			Flash.current().success(MSG_CREATED_FAIL);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap modelMap, @PathVariable Long id) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.mpInfoManger.removeById(id);
			Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
			Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}

		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap modelMap, @PathVariable Long id, @Valid MpInfo mpInfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW;
		}
		try {
			//this.bind(request, mpInfo);
			this.mpInfoManger.saveOrUpdate(mpInfo);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
			this.logger.error(e.getMessage());

		}

		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 进入新增 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/new")
	public String _new(ModelMap result, HttpServletRequest request, HttpServletResponse response, MpInfo model)
			throws Exception {

		Map requestMap = new HashMap();

		requestMap.put("tgid", model.getTgInfo().getTgId());

		result.addAttribute("mpinfo", model);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW;
	}

	/** 编辑 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id) throws Exception {

		Map requestMap = new HashMap();

		MpInfo model = mpInfoManger.getById(id);

		requestMap.put("tgid", model.getTgInfo().getTgId());

		result.addAttribute("mpinfo", model);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/**
	 * 
	 * @param model
	 * @param mapRequest
	 */
	@SuppressWarnings("unchecked")
	private void CommonPart(ModelMap result, Map mapRequest) {
		List<TerminalInfo> termlist = this.terminalInfoManger.findByPageRequest(mapRequest);
		result.addAttribute("termList", termlist);

		// CT
		mapRequest.put("codecate", CODE_CT_RATIO);

		result.addAttribute("ctList", this.codeInfoManager.findByPageRequest(mapRequest));

		// PT
		mapRequest.put("codecate", CODE_PT_RATIO);

		result.addAttribute("ptList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", CODE_COMM_MODE);

		result.addAttribute("commModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", CODE_BTL);

		result.addAttribute("btlList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 接线方式
		mapRequest.put("codecate", CODE_WIRING_MODE);

		result.addAttribute("wiringModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 计量方式
		mapRequest.put("codecate", CODE_MEAS_MODE);

		result.addAttribute("measModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 表 规 约
		mapRequest.put("codecate", CODE_PROTOCOL_METER);

		result.addAttribute("protocolMeterList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 表 状态
		mapRequest.put("codecate", CODE_METER_STATUS);

		result.addAttribute("runStatusList", this.codeInfoManager.findByPageRequest(mapRequest));

	}

	/**
	 * check gpSn 唯一性
	 * @param psInfo
	 * @return
	 */
	private boolean checkGpsn(MpInfo info) {
		return mpInfoManger.checkGpSn(info);
	}
}
