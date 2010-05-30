package org.pssframework.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.model.UserInfo;
import org.pssframework.service.UserInfoManager;
import org.pssframework.util.PageRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author PSS email:PPT(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/userinfo1")
public class UserInfoController extends BaseRestSpringController<UserInfo, java.lang.Long> {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private UserInfoManager userInfoManager;

	private final String LIST_ACTION = "redirect:/userinfo1";

	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 **/
	public void setUserInfoManager(UserInfoManager manager) {
		this.userInfoManager = manager;
	}

	/** 列表 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo) {
		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		//pageRequest.getFilters(); //add custom filters

		Page page = this.userInfoManager.findByPageRequest(pageRequest);

		ModelAndView result = toModelAndView(page, pageRequest);
		result.addObject("userInfo", userInfo);
		result.setViewName("/userinfo/list");
		return result;
	}

	/** 进入新增 */
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo)
			throws Exception {
		return new ModelAndView("/userinfo/new", "userInfo", userInfo);
	}

	/** 显示 */
	@Override
	public ModelAndView show(@PathVariable java.lang.Long id) throws Exception {
		UserInfo userInfo = (UserInfo) userInfoManager.getById(id);
		return new ModelAndView("/userinfo/show", "userInfo", userInfo);
	}

	/** 编辑 */
	@Override
	public ModelAndView edit(@PathVariable java.lang.Long id) throws Exception {
		UserInfo userInfo = (UserInfo) userInfoManager.getById(id);
		return new ModelAndView("/userinfo/edit", "userInfo", userInfo);
	}

	/** 保存新增 */
	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo)
			throws Exception {
		userInfoManager.save(userInfo);
		return new ModelAndView(LIST_ACTION);
	}

	/** 保存更新 */
	@Override
	public ModelAndView update(@PathVariable java.lang.Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfo userInfo = (UserInfo) userInfoManager.getById(id);
		bind(request, userInfo);
		userInfoManager.update(userInfo);
		return new ModelAndView(LIST_ACTION);
	}

	/** 删除 */
	@Override
	public ModelAndView delete(@PathVariable java.lang.Long id) {
		userInfoManager.removeById(id);
		return new ModelAndView(LIST_ACTION);
	}

	/** 批量删除 */
	@Override
	public ModelAndView batchDelete(java.lang.Long[] items) {
		for (int i = 0; i < items.length; i++) {
			userInfoManager.removeById(items[i]);
		}
		return new ModelAndView(LIST_ACTION);
	}

}
