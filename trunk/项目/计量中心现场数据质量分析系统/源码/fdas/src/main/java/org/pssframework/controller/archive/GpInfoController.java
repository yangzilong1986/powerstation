/**
 *
 */
package org.pssframework.controller.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;

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

	/** 列表 */
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "/${classNameLowerCase}/index";
	}

}
