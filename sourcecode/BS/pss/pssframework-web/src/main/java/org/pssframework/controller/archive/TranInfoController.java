/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_RATED_EC;
import static org.pssframework.support.system.SystemConst.CODE_TRAN_CODE;
import static org.pssframework.support.system.SystemConst.CODE_TRAN_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_VOLT_GRADE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_SHOW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.archive.TranInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/tranInfo")
public class TranInfoController extends BaseRestSpringController<TranInfo, java.lang.Long> {

	private static final String METHOD_TYPE = CONTROLLER_METHOD_TYPE;

	private static final String VIEW = "/archive/addTransformer";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private TranInfoManger tranInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	/** 列表 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TranInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap model, @Valid TranInfo tranInfo, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		try {
			tranInfo.setPubPrivFlag("0");
			this.tranInfoManager.saveOrUpdate(tranInfo);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			isSucc = false;
			this.logger.error(e.getMessage());
			msg = e.getMessage();
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}
		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);

		return null;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap model, @PathVariable Long id, @Valid TranInfo tranInfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		try {
			tranInfo = this.tranInfoManager.getById(id);
			this.bind(request, tranInfo);
			this.tranInfoManager.saveOrUpdate(tranInfo);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			isSucc = false;
			this.logger.error(e.getMessage());
			msg = MSG_UPDATE_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}


		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc);

		model.addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 显示 */
	@RequestMapping(value = "/{id}")
	public String show(ModelMap model, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		model = this.getCommonModelMap();

		model.addAttribute("tranInfo", this.tranInfoManager.getById(id));

		model.addAttribute(TranInfoController.METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);

		return VIEW;
	}

	/** 编辑 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		result = this.getCommonModelMap();

		result.addAttribute("tranInfo", this.tranInfoManager.getById(id));

		result.addAttribute(TranInfoController.METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/** 进入新增 */
	@RequestMapping(value = "/new")
	public String _new(ModelMap result, HttpServletRequest request, HttpServletResponse response, TranInfo model) {
		result = this.getCommonModelMap();
		result.addAttribute("tranInfo", model);
		result.addAttribute(TranInfoController.METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);
		return VIEW;
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getOptionList(Map mapRequest) {

		return this.codeInfoManager.findByPageRequest(mapRequest);

	}

	@SuppressWarnings("unchecked")
	private ModelMap getCommonModelMap() {
		ModelMap result = new ModelMap();

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", CODE_TRAN_CODE);

		result.addAttribute("typelist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_TRAN_STATUS);

		result.addAttribute("statuslist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_VOLT_GRADE);

		result.addAttribute("voltlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_RATED_EC);

		result.addAttribute("ratedlist", this.getOptionList(mapRequest));

		return result;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap model, @PathVariable Long id) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.tranInfoManager.removeById(id);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (DataAccessException e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}

		model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}
}
