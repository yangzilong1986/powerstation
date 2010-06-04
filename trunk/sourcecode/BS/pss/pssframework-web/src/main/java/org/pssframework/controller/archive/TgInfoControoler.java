/**
 * 
 */
package org.pssframework.controller.archive;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.system.CodeInfoManager;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.PageRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Controller
@RequestMapping("/archive/tginfo")
public class TgInfoControoler extends BaseRestSpringController<TgInfo, java.lang.Long> {

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	@Autowired
	private TgInfoManager tgInfoManager;

	@Autowired
	private OrgInfoManager orgInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		//pageRequest.getFilters(); //add custom filters
		pageRequest.setPageSize(PageRequestFactory.ALL_PAGE_SIZE);

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

		pageRequest.getFilters().put("orgid", orgid);

		pageRequest.getFilters().put("runstatuscode", runstatcode);

		TgInfo tginfo = this.tgInfoManager.getById(tgid);

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", tginfo);

		result.setViewName("/archive/addTgRelevance");

		result.addObject("orglist", getOrgOptions(pageRequest).getResult());

		//result.addObject("statuslist", getStatusOptions(pageRequest).getResult());

		return result;
	}

	@SuppressWarnings("unchecked")
	private Page getOrgOptions(PageRequest<Map> pageRequest) {
		return orgInfoManager.findByPageRequest(pageRequest);
	}

	private Page getStatusOptions(PageRequest<Map> pageRequest) {
		return codeInfoManager.findByPageRequest(pageRequest);
	}

	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		// TODO Auto-generated method stub
		return super._new(request, response, model);
	}

	@Override
	public ModelAndView delete(Long id) {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		// TODO Auto-generated method stub
		return super.create(request, response, model);
	}

	@Override
	public ModelAndView edit(Long id) throws Exception {
		// TODO Auto-generated method stub
		return super.edit(id);
	}

	@Override
	public ModelAndView update(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.update(id, request, response);
	}

	@Override
	public ModelAndView show(Long id) throws Exception {
		// TODO Auto-generated method stub
		return super.show(id);
	}
}
