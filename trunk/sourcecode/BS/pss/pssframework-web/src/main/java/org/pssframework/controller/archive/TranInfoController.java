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

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.archive.TranInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 变压器信息
 */
@Controller
@RequestMapping("/archive/tranInfo")
public class TranInfoController extends BaseRestSpringController<TranInfo, java.lang.Long> {

	private static final String METHOD_TYPE = CONTROLLER_METHOD_TYPE;

	private static final String VIEW = "/archive/addTransformer";

	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		super.initBinder(request, binder);
	}

	@Autowired
	private TranInfoManger tranInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TranInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TranInfo model)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		try {
			model.setPubPrivFlag("0");
			this.tranInfoManager.saveOrUpdate(model);
		} catch (Exception e) {
			isSucc = false;
			this.logger.error(e.getMessage());
			msg = e.getMessage();

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(
				CONTROLLER_AJAX_MESSAGE, msg);
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		try {
			TranInfo tranInfo = this.tranInfoManager.getById(id);
			this.bind(request, tranInfo);
			this.tranInfoManager.saveOrUpdate(tranInfo);
		} catch (Exception e) {
			isSucc = false;
			this.logger.error(e.getMessage());
			msg = MSG_UPDATE_FAIL;

		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(
				CONTROLLER_AJAX_MESSAGE, msg);
	}

	@Override
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = this.getCommonModelAndView();

		result.addObject("tranInfo", this.tranInfoManager.getById(id));

		result.setViewName(VIEW);

		result.addObject(TranInfoController.METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);;

		return result;
	}

	@Override
	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = this.getCommonModelAndView();

		result.addObject("tranInfo", this.tranInfoManager.getById(id));

		result.setViewName(VIEW);

		result.addObject(TranInfoController.METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return result;
	}

	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TranInfo model) {
		ModelAndView result = this.getCommonModelAndView();
		result.addObject("tranInfo", model);
		result.setViewName(VIEW);
		result.addObject(TranInfoController.METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getOptionList(Map mapRequest) {

		return this.codeInfoManager.findByPageRequest(mapRequest);

	}

	@SuppressWarnings("unchecked")
	private ModelAndView getCommonModelAndView() {
		ModelAndView result = new ModelAndView();

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", CODE_TRAN_CODE);

		result.addObject("typelist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_TRAN_STATUS);

		result.addObject("statuslist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_VOLT_GRADE);

		result.addObject("voltlist", this.getOptionList(mapRequest));

		mapRequest.put("codecate", CODE_RATED_EC);

		result.addObject("ratedlist", this.getOptionList(mapRequest));

		return result;
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		ModelAndView modelAndView = new ModelAndView();
		try {
			this.tranInfoManager.removeById(id);
		} catch (DataAccessException e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
		}
		modelAndView.addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(
				CONTROLLER_AJAX_MESSAGE, msg);
		return modelAndView;
	}
}
