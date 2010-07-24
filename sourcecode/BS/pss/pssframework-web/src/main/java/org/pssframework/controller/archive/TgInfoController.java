/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_TG_STATUS;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.archive.MpInfoManger;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.system.CodeInfoManager;
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

/**
 * @author Baocj
 * 
 */
@Controller
@RequestMapping("/archive/tginfo")
public class TgInfoController extends BaseRestSpringController<TgInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addTgRelevance";

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManager;

	@Autowired
	private PsInfoManger psInfoManager;

	@Autowired
	private MpInfoManger mpInfoManger;

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/** 列表 */
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response, TgInfo tgInfo) {

		Long tgid = 0L;
		if (tgInfo.getTgId() != null) {
			tgid = tgInfo.getTgId();
		}

		TgInfo tginfo = this.tgInfoManager.getById(tgid) == null ? new TgInfo() : this.tgInfoManager.getById(tgid);

		model.addAttribute("tginfo", tginfo);

		return VIEW;
	}

	@SuppressWarnings("unchecked")
	private List<OrgInfo> getOrgOptions(Map mapRequest) {
		return this.orgInfoManager.findByPageRequest(mapRequest);
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getStatusOptions(Map mapRequest) {
		return this.codeInfoManager.findByPageRequest(mapRequest);
	}

	@SuppressWarnings("unchecked")
	private List<MpInfo> getMpList(Map mapRequest) {
		List<MpInfo> meterlist = this.mpInfoManger.findByPageRequest(mapRequest);
		if (meterlist == null || meterlist.size() <= 0) {
			meterlist = new LinkedList<MpInfo>();
		}
		return meterlist;
	}

	@SuppressWarnings("unchecked")
	private List<PsInfo> getPsList(Map mapRequest) {
		List<PsInfo> pslist = this.psInfoManager.findByPageRequest(mapRequest);
		if (pslist == null || pslist.size() <= 0) {
			pslist = new LinkedList<PsInfo>();
		}
		return pslist;
	}

	@SuppressWarnings("unchecked")
	private List<TerminalInfo> getTerminalList(Map mapRequest) {
		List<TerminalInfo> terminallist = this.terminalInfoManager.findByPageRequest(mapRequest);
		if (terminallist == null || terminallist.size() <= 0) {
			terminallist = new LinkedList<TerminalInfo>();
		}
		return terminallist;
	}

	/** 进入新增 */
	@RequestMapping(value = "/new")
	public String _new(ModelMap model, TgInfo tgInfo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.logger.debug("tg.{}", CONTROLLER_METHOD_TYPE_NEW);

		model.addAttribute("tginfo", tgInfo);

		Map<String, Object> mapRequest = new HashMap<String, Object>();

		mapRequest.put("codecate", CODE_TG_STATUS);

		mapRequest.put("tgid", 0L);

		this.CommonPart(model, mapRequest);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap model, @PathVariable Long id) {
		this.logger.debug("tg.{},{}", "delete", id);
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.tgInfoManager.removeById(id);
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
	public String create(ModelMap model, @Valid TgInfo tgInfo, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.logger.debug("tg.{}", "create");
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long tgId = 0L;
		try {

			tgInfo.setChaDate(new Date());

			tgInfo.setPubPrivFlag("0");

			this.tgInfoManager.saveOrUpdate(tgInfo);

			tgId = tgInfo.getTgId();

			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {

			isSucc = false;

			msg = MSG_CREATED_FAIL;

			logger.debug(e.getMessage());
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);

		}

		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg).addAttribute(
				"tgId", tgId);

		return VIEW;
	}

	/** 编辑 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap model, @PathVariable Long id) throws Exception {
		this.logger.debug("tg.{},{}", "edit", id);

		TgInfo tginfo = this.tgInfoManager.getById(id) == null ? new TgInfo() : this.tgInfoManager.getById(id);

		model.addAttribute("tginfo", tginfo);

		Map<String, Object> mapRequest = new HashMap<String, Object>();

		mapRequest.put("codecate", CODE_TG_STATUS);

		mapRequest.put("tgid", id);

		this.CommonPart(model, mapRequest);

		model.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap model, @PathVariable Long id, @Valid TgInfo tginfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		if (errors.hasErrors()) {
			model.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return "/archive/tginfo/" + id + "/edit";
		}
		this.logger.debug("tg.{},{}", "update", id);

		try {
			tginfo = this.tgInfoManager.getById(id);
			tginfo.setChaDate(new Date());
			this.tgInfoManager.update(tginfo);
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

		this.logger.debug("tg.{},{}", "show", id);

		TgInfo tginfo = this.tgInfoManager.getById(id);

		model.addAttribute("tginfo", tginfo);

		Map<String, Object> mapRequest = new HashMap<String, Object>();

		// 台区状态
		mapRequest.put("codecate", CODE_TG_STATUS);

		mapRequest.put("tgid", id);

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
	private void getInitOption(ModelMap model, Map<String, ?> mapRequest) {
		model.addAttribute("orglist", this.getOrgOptions(mapRequest));
		model.addAttribute("statuslist", this.getStatusOptions(mapRequest));

	}

	/**
	 * 关联项目
	 * 
	 * @param modelMap
	 * @param mapRequest
	 */
	private void getRelevance(ModelMap modelMap, Map<String, ?> mapRequest) {
		// modelAndView.addObject("tranlist", getTranList(mapRequest));
		modelMap.addAttribute("pslist", this.getPsList(mapRequest));
		modelMap.addAttribute("termlist", this.getTerminalList(mapRequest));
		// modelMap.addObject("mplist", this.getMpList(mapRequest));

	}

	/**
	 * 重叠部分
	 * 
	 * @param modelMap
	 * @param mapRequest
	 */
	private void CommonPart(ModelMap modelMap, Map<String, ?> mapRequest) {
		this.getInitOption(modelMap, mapRequest);
		this.getRelevance(modelMap, mapRequest);
	}

}
