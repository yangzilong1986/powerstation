package org.pssframework.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

@Controller
@RequestMapping("/system/org")
public class OrgInfoController extends BaseRestSpringController<OrgInfo, Long> {

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private OrgInfoManager orgInfoManager;

	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 **/
	public void setOrgInfoManager(OrgInfoManager manager) {
		this.orgInfoManager = manager;
	}

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, OrgInfo model) {
		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		//pageRequest.getFilters(); //add custom filters

		Map fiterMap = pageRequest.getFilters();

		Page page = this.orgInfoManager.findByPageRequest(pageRequest);

		ModelAndView result = toModelAndView(page, pageRequest);

		result.addObject("orginfo", page.getResult());

		result.setViewName("/system/orgList");

		return result;
	}

}
