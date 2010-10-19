package org.pssframework.controller.system;

import static org.pssframework.support.system.SystemConst.CODE_ORG_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_SHOW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
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
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/orginfo")
public class OrgInfoController extends BaseRestSpringController<OrgInfo, Long> {

	private static final String VIEW = "/system/deptEdit";

	@Autowired
	private OrgInfoManager orgInfoManager;

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/** 列表 */
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response, OrgInfo orgInfo) {

		Long orgid = 0L;
		if (orgInfo.getOrgId() != null) {
			orgid = orgInfo.getOrgId();
		}

		OrgInfo orginfo = this.orgInfoManager.getById(orgid) == null ? new OrgInfo() : this.orgInfoManager
				.getById(orgid);

		model.addAttribute("orginfo", orginfo);

		return VIEW;
	}

	private List<CodeInfo> getOrgTypes(Map mapRequest) {
		return this.orgInfoManager.findOrgTypes(mapRequest);
	}

	/** 进入新增 */
	@RequestMapping(value = "/new")
	public String _new(ModelMap model, OrgInfo orgInfo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.logger.debug("org.{}", CONTROLLER_METHOD_TYPE_NEW);

		OrgInfo pOrgInfo = orgInfo.getParentOrgInfo();

		orgInfo.setParentOrgInfo(this.orgInfoManager.getById(pOrgInfo.getOrgId()));

		orgInfo.setOrgNo(lookingForPurposeOrgNo(pOrgInfo));

		model.addAttribute("orginfo", orgInfo);

		Map<String, Object> mapRequest = new HashMap<String, Object>();

		mapRequest.put("code", orgInfo.getParentOrgInfo().getOrgType());

		this.CommonPart(model, mapRequest);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap model, @PathVariable Long id) {
		this.logger.debug("org.{},{}", "delete", id);
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.orgInfoManager.removeById(id);
			//Flash.current().success(msg);
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();
			//Flash.current().error(msg);

		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap model, @Valid OrgInfo orgInfo, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.logger.debug("org.{}", "create");
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long orgId = 0L;
		try {
			orgInfo.setLasttimeStamp(new Date());
			this.orgInfoManager.saveOrUpdate(orgInfo);
			orgId = orgInfo.getOrgId();

			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {

			isSucc = false;

			msg = MSG_CREATED_FAIL;

			logger.debug(e.getMessage());
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);

		}

		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg)
				.addAttribute("orgId", orgId);

		return VIEW;
	}

	/** 编辑 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap model, @PathVariable Long id) throws Exception {
		this.logger.debug("org.{},{}", "edit", id);

		OrgInfo orginfo = this.orgInfoManager.getById(id) == null ? new OrgInfo() : this.orgInfoManager.getById(id);

		model.addAttribute("orginfo", orginfo);

		Map<String, Object> mapRequest = new HashMap<String, Object>();

		if (orginfo.getParentOrgInfo() == null) {
			mapRequest.put("code", "00");
		} else {
			mapRequest.put("code", orginfo.getParentOrgInfo().getOrgType());
		}

		
		this.CommonPart(model, mapRequest);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap model, @PathVariable Long id, @Valid OrgInfo orginfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		if (errors.hasErrors()) {
			model.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return "/archive/orginfo/" + id + "/edit";
		}
		this.logger.debug("org.{},{}", "update", id);

		try {
			orginfo = this.orgInfoManager.getById(id);

			bind(request, orginfo);

			orginfo.setLasttimeStamp(new Date());

			this.orgInfoManager.update(orginfo);

			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			this.logger.info(e.getMessage());

			isSucc = false;

			msg = MSG_UPDATE_FAIL;

			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);

		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);

		return VIEW;
	}

	/** 显示 */
	@RequestMapping(value = "/{id}")
	public String show(ModelMap model, @PathVariable Long id) throws Exception {

		this.logger.debug("org.{},{}", "show", id);

		OrgInfo orginfo = this.orgInfoManager.getById(id);

		model.addAttribute("orginfo", orginfo);

		Map<String, Object> mapRequest = new HashMap<String, Object>();

		if (orginfo.getParentOrgInfo() == null) {
			mapRequest.put("code", "00");
		} else {
			mapRequest.put("code", orginfo.getParentOrgInfo().getOrgType());
		}

		this.CommonPart(model, mapRequest);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);

		return VIEW;

	}

	/**
	 * 下拉框
	 * 
	 * @param model
	 * @param mapRequest
	 */
	private void getInitOption(ModelMap model, Map<String, Object> mapRequest) {

		mapRequest.put("codecate", CODE_ORG_TYPE);

		model.addAttribute("orgtype", this.getOrgTypes(mapRequest));

	}

	/**
	 * 重叠部分
	 * 
	 * @param modelMap
	 * @param mapRequest
	 */
	private void CommonPart(ModelMap modelMap, Map<String, Object> mapRequest) {
		this.getInitOption(modelMap, mapRequest);
	}

	/**
	 * 查询该部门下可用的orgNo
	 * @param pOrgInfo
	 * @return
	 */
	private String lookingForPurposeOrgNo(OrgInfo pOrgInfo) {
		return this.orgInfoManager.findPurposeOrgNo(pOrgInfo);
	}

	/**
	 *校验测量地址序号
	 */
	@ResponseBody
	@RequestMapping(value = "/checkSortNo")
	public String checkSortNo(OrgInfo orgInfo) throws Exception {

		return this.orgInfoManager.checkSortNoRePeat(orgInfo);
	}

	/**
	 *校验测量地址序号
	 */
	@ResponseBody
	@RequestMapping(value = "/checkOrgNo")
	public String checkOrgNo(OrgInfo orgInfo) throws Exception {

		return this.orgInfoManager.checkOrgNoRePeat(orgInfo);
	}
}
