/**
 * 
 */
package org.pssframework.controller.archive;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.service.archive.TgInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TgInfo model) {

		TgInfo tginfo = this.tgInfoManager.getById(model.getTgId());

		ModelAndView result = new ModelAndView();

		result.addObject("tginfo", tginfo);

		result.setViewName("/archive/addTgRelevance");

		return result;
	}

	private List getOrgOptions() {
		return null;
	}

	private List getStatusOptions() {
		return null;
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
