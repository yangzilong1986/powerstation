/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_SWITCH_VALUE_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_SHOW;
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
import org.pssframework.model.archive.SwitchValueInfo;
import org.pssframework.model.archive.SwitchValueInfoPK;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.service.archive.SwitchValueInfoManager;
import org.pssframework.service.archive.TermObjRelaInfoManager;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Administrator 漏保
 */
@Controller
@RequestMapping("/archive/switchvalueinfo")
public class SwitchValueInfoController extends BaseRestSpringController<SwitchValueInfo, SwitchValueInfoPK> {

	private static final String VIEW = "/archive/addSwitchValueInfo";

	private static final String FORWARD = "/archive/switchvalueinfo";

	//该终端下测量点序号重复
	private static final String GP_IS_REPEAT = "该终端下测量点序号重复";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Autowired
	private SwitchValueInfoManager switchValueInfoManager;

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@Valid SwitchValueInfo model) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long switchValueId = 0L;

		if (checkSwtichNo(model)) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE, GP_IS_REPEAT);
			return VIEW;
		}

		try {
			this.switchValueInfoManager.saveOrUpdate(model);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, MSG_CREATED_SUCCESS);
		} catch (DataAccessException e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, MSG_CREATED_FAIL);
		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, MSG_CREATED_FAIL);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg)
				.addAttribute("switchValueId", switchValueId);
		return VIEW;
	}

	/** 编辑 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/edit")
	public String edit(ModelMap result, SwitchValueInfoPK switchValueInfoPK) throws Exception {

		SwitchValueInfo switchValueInfo = this.switchValueInfoManager.getById(switchValueInfoPK);

		Map requestMap = Maps.newHashMap();

		result.addAttribute("switchvalueinfo", switchValueInfo);

		requestMap.put("termid", switchValueInfoPK.getTerminalInfo().getTermId());

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/** 查看 */
	@SuppressWarnings("unchecked")
	@RequestMapping
	public String show(ModelMap result, SwitchValueInfoPK switchValueInfoPK) throws Exception {

		SwitchValueInfo switchValueInfo = this.switchValueInfoManager.getById(switchValueInfoPK);

		Map requestMap = Maps.newHashMap();

		result.addAttribute("switchvalueinfo", switchValueInfo);

		requestMap.put("termid", switchValueInfoPK.getTerminalInfo().getTermId());

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);

		return VIEW;
	}

	/** 进入新增 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/new")
	public String _new(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			SwitchValueInfo model) throws Exception {

		Long tgid = Long.parseLong(request.getParameter("tgId"));

		Map<String, Comparable> requestMap = new HashMap<String, Comparable>();

		requestMap.put("tgid", tgid);

		modelMap.addAttribute("tgId", tgid);

		modelMap.addAttribute("switchvalueinfo", model);

		this.CommonPart(modelMap, requestMap);

		modelMap.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(ModelMap modelMap, @Valid SwitchValueInfo switchvalueinfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false)
					.addAttribute(CONTROLLER_AJAX_MESSAGE, MSG_UPDATE_FAIL);
			return FORWARD + "/edit";
		}

		try {

			logger.debug("bind switchvalueinfo {} from request", switchvalueinfo);

			if (checkSwtichNo(switchvalueinfo)) {

				logger.error("the switchNo repeat");
				modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
						"the switchNo repeat");
				return VIEW;

			}

			SwitchValueInfo switchValueInfoDb = this.switchValueInfoManager.getById(switchvalueinfo.getSwitchValueId());
			bind(request, switchValueInfoDb);
			this.switchValueInfoManager.update(switchValueInfoDb);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 删除 */
	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(ModelMap modelMap, SwitchValueInfoPK switchValueInfoPK) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;

		try {
			this.switchValueInfoManager.removeById(switchValueInfoPK);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 
	 * @param result
	 * @param mapRequest
	 */
	@SuppressWarnings("rawtypes")
	private void CommonPart(ModelMap result, Map<String, Comparable> mapRequest) {

		List<TerminalInfo> termlist = Lists.newArrayList();
		if (mapRequest.get("tgid") == null) {
			Long tgid = this.getTgInfo(mapRequest).getTgId();
			result.addAttribute("tgId", tgid);
			mapRequest.put("tgid", tgid);
		}

		termlist = this.terminalInfoManger.findByPageRequest(mapRequest);

		result.addAttribute("termList", termlist);

		//  CODE_SWITCH_VALUE_TYPE
		mapRequest.put("codecate", CODE_SWITCH_VALUE_TYPE);

		result.addAttribute("switchTypeList", this.codeInfoManager.findByPageRequest(mapRequest));

	}

	/**
	 * checkSwtichNo 唯一性
	 * @param switchValueInfo
	 * @return
	 */
	private boolean checkSwtichNo(SwitchValueInfo switchValueInfo) {
		return switchValueInfoManager.checkSwtichNo(switchValueInfo);
	}

	/**
	 *校验测量点序号
	 */
	@ResponseBody
	@RequestMapping(value = "/checkSwtichNo")
	public String checkSwtichNoByAjax(SwitchValueInfo switchValueInfo) throws Exception {
		return switchValueInfoManager.checkSwtichNoRePeat(switchValueInfo);
	}

	@Autowired
	private TermObjRelaInfoManager termObjRelaInfoManager;

	private TgInfo getTgInfo(Map con) {
		List<TgInfo> tginfos = Lists.newArrayList();
		tginfos = termObjRelaInfoManager.findTgInfo(con);
		for (TgInfo tginfo : tginfos)
			return tginfo;
		return tginfos.get(0);
	}

}
