/**
 * 
 */
package org.pssframework.controller.archive;

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
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.archive.MpInfoManger;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.archive.TranInfoManger;
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

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private static final String globalFoward = "/archive/addTgRelevance";

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

	@Autowired
	private TranInfoManger tranInfoManger;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		Long tgid = 0L;
		if (model.getTgId() != null) {
			tgid = model.getTgId();
		}

		TgInfo tginfo = this.tgInfoManager.getById(tgid) == null ? new TgInfo() : this.tgInfoManager.getById(tgid);

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", tginfo);

		result.setViewName(globalFoward);

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<OrgInfo> getOrgOptions(Map mapRequest) {
		return orgInfoManager.findByPageRequest(mapRequest);
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getStatusOptions(Map mapRequest) {
		return codeInfoManager.findByPageRequest(mapRequest);
	}

	@SuppressWarnings("unchecked")
	private List<MpInfo> getMpList(Map mapRequest) {
		List<MpInfo> meterlist = mpInfoManger.findByPageRequest(mapRequest);
		if (meterlist == null || meterlist.size() <= 0) {
			meterlist = new LinkedList<MpInfo>();
		}
		return meterlist;
	}

	@SuppressWarnings("unchecked")
	private List<TranInfo> getTranList(Map mapRequest) {
		List<TranInfo> tranlist = tranInfoManger.findByPageRequest(mapRequest);
		if (tranlist == null || tranlist.size() <= 0) {
			tranlist = new LinkedList<TranInfo>();
		}
		return tranlist;
	}

	@SuppressWarnings("unchecked")
	private List<PsInfo> getPsList(Map mapRequest) {
		List<PsInfo> pslist = psInfoManager.findByPageRequest(mapRequest);
		if (pslist == null || pslist.size() <= 0) {
			pslist = new LinkedList<PsInfo>();
		}
		return pslist;
	}

	@SuppressWarnings("unchecked")
	private List<TerminalInfo> getTerminalList(Map mapRequest) {
		List<TerminalInfo> terminallist = terminalInfoManager.findByPageRequest(mapRequest);
		if (terminallist == null || terminallist.size() <= 0) {
			terminallist = new LinkedList<TerminalInfo>();
		}
		return terminallist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		logger.debug("tg.{}", "new");

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", new TgInfo());

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TG_STATUS");

		CommonPart(result, mapRequest);

		result.addObject("_type", "new");

		result.setViewName(globalFoward);
		return result;
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("tg.{},{}", "delete", id);
		boolean isSucc = true;
		String msg = DELETE_SUCCESS;
		try {

			tgInfoManager.removeById(id);

		} catch (Exception e) {

			isSucc = false;

			msg = e.getMessage();

		}
		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg);
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		logger.debug("tg.{}", "create");
		boolean isSucc = true;
		String msg = CREATED_SUCCESS;
		Long tgId = 0L;
		try {
			model.setChaDate(new Date());

			model.setPubPrivFlag("0");

			tgInfoManager.saveOrUpdate(model);

			tgId = model.getTgId();

		} catch (Exception e) {

			isSucc = false;

			msg = e.getMessage();

		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("tgId", tgId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("tg.{},{}", "edit", id);

		ModelAndView result = new ModelAndView();

		TgInfo tginfo = this.tgInfoManager.getById(id) == null ? new TgInfo() : this.tgInfoManager.getById(id);

		result.addObject("tginfo", tginfo);

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TG_STATUS");

		mapRequest.put("tgid", id);

		CommonPart(result, mapRequest);

		result.addObject("_type", "edit");

		result.setViewName(globalFoward);
		return result;
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.debug("tg.{},{}", "update", id);

		boolean isSucc = true;
		String msg = UPDATE_SUCCESS;

		try {
			TgInfo tginfo = this.tgInfoManager.getById(id);
			tginfo.setChaDate(new Date());
			bind(request, tginfo);
			tgInfoManager.update(tginfo);
		} catch (Exception e) {
			this.logger.info(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();
		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.debug("tg.{},{}", "show", id);

		TgInfo tginfo = this.tgInfoManager.getById(id);

		// tginfo.getOrgInfo().getOrgId()

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("tginfo", tginfo);

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TG_STATUS");

		mapRequest.put("tgid", id);

		CommonPart(modelAndView, mapRequest);

		modelAndView.setViewName(globalFoward);

		modelAndView.addObject("_type", "show");

		return modelAndView;

	}

	/**
	 * 下拉框
	 * 
	 * @param model
	 * @param mapRequest
	 */
	private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {
		model.addObject("orglist", getOrgOptions(mapRequest));
		model.addObject("statuslist", getStatusOptions(mapRequest));

	}

	/**
	 * 关联项目
	 * 
	 * @param modelAndView
	 * @param mapRequest
	 */
	private void getRelevance(ModelAndView modelAndView, Map<String, ?> mapRequest) {
		modelAndView.addObject("tranlist", getTranList(mapRequest));
		modelAndView.addObject("pslist", getPsList(mapRequest));
		modelAndView.addObject("termlist", getTerminalList(mapRequest));
		modelAndView.addObject("mplist", getMpList(mapRequest));

	}

	/**
	 * 重叠部分
	 * 
	 * @param modelAndView
	 * @param mapRequest
	 */
	private void CommonPart(ModelAndView modelAndView, Map<String, ?> mapRequest) {
		getInitOption(modelAndView, mapRequest);
		getRelevance(modelAndView, mapRequest);
	}

}
