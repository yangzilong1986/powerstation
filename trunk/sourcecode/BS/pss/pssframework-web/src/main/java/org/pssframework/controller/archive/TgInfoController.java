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
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		Long tgid = 0L;
		if (model.getTgId() != null) {
			tgid = model.getTgId();
		}

		TgInfo tginfo = this.tgInfoManager.getById(tgid) == null ? new TgInfo() : this.tgInfoManager.getById(tgid);

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", tginfo);

		result.setViewName(TgInfoController.VIEW);

		return result;
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

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		this.logger.debug("tg.{}", CONTROLLER_METHOD_TYPE_NEW);

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", new TgInfo());

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", CODE_TG_STATUS);

		this.CommonPart(result, mapRequest);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		result.setViewName(TgInfoController.VIEW);
		return result;
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		this.logger.debug("tg.{},{}", "delete", id);
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {

			this.tgInfoManager.removeById(id);

		} catch (Exception e) {

			isSucc = false;

			msg = e.getMessage();

		}
		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(
				CONTROLLER_AJAX_MESSAGE, msg);
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		this.logger.debug("tg.{}", "create");
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long tgId = 0L;
		try {
			model.setChaDate(new Date());

			model.setPubPrivFlag("0");

			this.tgInfoManager.saveOrUpdate(model);

			tgId = model.getTgId();

		} catch (Exception e) {

			isSucc = false;

			msg = e.getMessage();

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(
				CONTROLLER_AJAX_MESSAGE, msg).addObject("tgId", tgId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.logger.debug("tg.{},{}", "edit", id);

		ModelAndView result = new ModelAndView();

		TgInfo tginfo = this.tgInfoManager.getById(id) == null ? new TgInfo() : this.tgInfoManager.getById(id);

		result.addObject("tginfo", tginfo);

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", CODE_TG_STATUS);

		mapRequest.put("tgid", id);

		this.CommonPart(result, mapRequest);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		result.setViewName(TgInfoController.VIEW);
		return result;
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.logger.debug("tg.{},{}", "update", id);

		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		try {
			TgInfo tginfo = this.tgInfoManager.getById(id);
			tginfo.setChaDate(new Date());
			this.bind(request, tginfo);
			this.tgInfoManager.update(tginfo);
		} catch (Exception e) {
			this.logger.info(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();
		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(
				CONTROLLER_AJAX_MESSAGE, msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.logger.debug("tg.{},{}", "show", id);

		TgInfo tginfo = this.tgInfoManager.getById(id);

		// tginfo.getOrgInfo().getOrgId()

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("tginfo", tginfo);

		Map mapRequest = new HashMap();

		// 台区状态
		mapRequest.put("codecate", CODE_TG_STATUS);

		mapRequest.put("tgid", id);

		this.CommonPart(modelAndView, mapRequest);

		modelAndView.setViewName(TgInfoController.VIEW);

		modelAndView.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);;

		return modelAndView;

	}

	/**
	 * 下拉框
	 * 
	 * @param model
	 * @param mapRequest
	 */
	private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {
		model.addObject("orglist", this.getOrgOptions(mapRequest));
		model.addObject("statuslist", this.getStatusOptions(mapRequest));

	}

	/**
	 * 关联项目
	 * 
	 * @param modelAndView
	 * @param mapRequest
	 */
	private void getRelevance(ModelAndView modelAndView, Map<String, ?> mapRequest) {
		// modelAndView.addObject("tranlist", getTranList(mapRequest));
		modelAndView.addObject("pslist", this.getPsList(mapRequest));
		modelAndView.addObject("termlist", this.getTerminalList(mapRequest));
		modelAndView.addObject("mplist", this.getMpList(mapRequest));

	}

	/**
	 * 重叠部分
	 * 
	 * @param modelAndView
	 * @param mapRequest
	 */
	private void CommonPart(ModelAndView modelAndView, Map<String, ?> mapRequest) {
		this.getInitOption(modelAndView, mapRequest);
		this.getRelevance(modelAndView, mapRequest);
	}

}
