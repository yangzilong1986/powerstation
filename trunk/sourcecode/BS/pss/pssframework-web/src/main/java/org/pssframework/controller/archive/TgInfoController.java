/**
 * 
 */
package org.pssframework.controller.archive;

import java.util.Date;
import java.util.HashMap;
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

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	private TranInfoManger tranInfoManager;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		Map mapRequest = new HashMap();

		Long tgid = 0L;
		if (model.getTgId() != null) {
			tgid = model.getTgId();
		}

		Long orgid = 0L;
		if (model.getOrgId() != null) {
			orgid = model.getOrgId();
		}

		String runstatcode = "1";
		if (model.getRunStatusCode() != null) {
			runstatcode = model.getRunStatusCode();
		}

		//mapRequest.put("orgid", orgid);

		mapRequest.put("codecate", "TG_STATUS");

		TgInfo tginfo = this.tgInfoManager.getById(tgid) == null ? new TgInfo() : this.tgInfoManager.getById(tgid);

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", tginfo);

		result.setViewName("/archive/addTgRelevance");

		result.addObject("orglist", getOrgOptions(mapRequest));

		result.addObject("statuslist", getStatusOptions(mapRequest));


		return result;
	}

	@SuppressWarnings("unchecked")
	private List<OrgInfo> getOrgOptions(Map mapRequest) {
		return orgInfoManager.findByPageRequest(mapRequest);
	}

	private List<CodeInfo> getStatusOptions(Map mapRequest) {
		return codeInfoManager.findByPageRequest(mapRequest);
	}

	private List<TranInfo> getTranList(Map mapRequest) {
		return tranInfoManager.findByPageRequest(mapRequest);
	}

	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		// TODO Auto-generated method stub
		return super._new(request, response, model);
	}

	@Override
	public ModelAndView delete(@PathVariable Long id) {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		boolean isSucc = true;
		String msg = "成功";
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
	public ModelAndView edit(@PathVariable Long id) throws Exception {
		// TODO Auto-generated method stub
		return super.edit(id);
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = "成功";
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

	@Override
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TgInfo tginfo = this.tgInfoManager.getById(id);
		return index(request, response, tginfo);

	}

}
