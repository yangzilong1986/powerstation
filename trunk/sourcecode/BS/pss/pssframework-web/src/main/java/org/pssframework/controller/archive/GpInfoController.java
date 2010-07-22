/**
 *
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_RATED_EC;
import static org.pssframework.support.system.SystemConst.CODE_TG_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_TRAN_CODE;
import static org.pssframework.support.system.SystemConst.CODE_TRAN_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_VOLT_GRADE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.base.BaseQuery;
import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.service.archive.GpInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;

/**
 * @author Administrator
 *         变压器信息
 */
@Controller
@RequestMapping("/archive/gpinfo")
public class GpInfoController extends BaseRestSpringController<GpInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addTransformer";

	@Autowired
	private GpInfoManger gpInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, GpInfo model) {

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", CODE_TG_STATUS);

		ModelAndView result = new ModelAndView();

		return result;
	}

	/** 列表 */
	@RequestMapping
	public String index(ModelMap model, GpInfoQuery query, HttpServletRequest request, HttpServletResponse response) {
		Page page = this.gpInfoManger.findPage(query);

		model.addAllAttributes(toModelMap(page, query));
		return "/${classNameLowerCase}/index";
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}

	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, GpInfo model) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		try {
			this.gpInfoManger.saveOrUpdate(model);
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg)
				.addObject("gpid", model.getGpId());
	}

	@SuppressWarnings("unchecked")
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, GpInfo model) throws Exception {
		ModelAndView result = new ModelAndView();

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", CODE_TRAN_CODE);

		result.addObject("typelist", this.codeInfoManager.findByPageRequest(mapRequest));

		mapRequest.put("codecate", CODE_TRAN_STATUS);

		result.addObject("statuslist", this.codeInfoManager.findByPageRequest(mapRequest));

		mapRequest.put("codecate", CODE_VOLT_GRADE);

		result.addObject("voltlist", this.codeInfoManager.findByPageRequest(mapRequest));

		mapRequest.put("codecate", CODE_RATED_EC);

		result.addObject("ratedlist", this.codeInfoManager.findByPageRequest(mapRequest));

		result.addObject("traninfo", model);

		result.setViewName(VIEW);

		return result;
	}

	class GpInfoQuery extends BaseQuery {

	}
}
