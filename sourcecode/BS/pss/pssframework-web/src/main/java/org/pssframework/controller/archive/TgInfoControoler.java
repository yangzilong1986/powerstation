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

	private TgInfoManager tgInfoManager;

	private final String LIST_ACTION = "redirect:/archive/tginfo.do";

	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 **/
	public void setTgInfoManager(TgInfoManager manager) {
		this.tgInfoManager = manager;
	}

	private OrgInfoManager orgInfoManager;

	/**
	 * @param orgInfoManager the orgInfoManager to set
	 */
	public void setOrgInfoManager(OrgInfoManager orgInfoManager) {
		this.orgInfoManager = orgInfoManager;
	}

	private CodeInfoManager codeInfoManager;

	/**
	 * @param orgInfoManager the orgInfoManager to set
	 */
	public void setCodeInfoManager(CodeInfoManager codeInfoManager) {
		this.codeInfoManager = codeInfoManager;
	}

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		//pageRequest.getFilters(); //add custom filters

		Map fiterMap = pageRequest.getFilters();

		TgInfo tginfo = this.tgInfoManager.getById(model.getTgId());

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", tginfo);

		result.setViewName("/archive/addTgRelevance");

		return result;
	}

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
