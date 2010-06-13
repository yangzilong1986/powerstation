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
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
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

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private static final String SUCC = "成功";

	private static final String globalFoward = "/archive/addTgRelevance";

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TranInfoManger tranInfoManager;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		Long tgid = 0L;
		if (model.getTgId() != null) {
			tgid = model.getTgId();
		}

		//mapRequest.put("orgid", orgid);

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
	private List<TranInfo> getTranList(Map mapRequest) {
		List<TranInfo> tranlist = tranInfoManager.findByPageRequest(mapRequest);
		if (tranlist == null || tranlist.size() <= 0) {
			tranlist = new LinkedList<TranInfo>();
		}
		return tranlist;
	}

	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		return index(request, response, model);
	}

	@Override
	public ModelAndView delete(@PathVariable Long id) {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		boolean isSucc = true;
		String msg = SUCC;
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

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView edit(@PathVariable Long id) throws Exception {
		ModelAndView result = new ModelAndView();

		TgInfo tginfo = this.tgInfoManager.getById(id) == null ? new TgInfo() : this.tgInfoManager.getById(id);

		result.addObject("tginfo", tginfo);

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TG_STATUS");

		getInitOption(result, mapRequest);

		getRelevance(result, mapRequest);

		result.setViewName(globalFoward);
		return result;
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = SUCC;
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

		TgInfo tginfo = this.tgInfoManager.getById(id);

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("tginfo", tginfo);

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TG_STATUS");

		getInitOption(modelAndView, mapRequest);

		getRelevance(modelAndView, mapRequest);

		modelAndView.setViewName(globalFoward);

		return modelAndView;

	}

	private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {
		model.addObject("orglist", getOrgOptions(mapRequest));
		model.addObject("statuslist", getStatusOptions(mapRequest));

	}

	private void getRelevance(ModelAndView modelAndView, Map<String, ?> mapRequest) {
		modelAndView.addObject("tranlist", getTranList(mapRequest));

	}

}
