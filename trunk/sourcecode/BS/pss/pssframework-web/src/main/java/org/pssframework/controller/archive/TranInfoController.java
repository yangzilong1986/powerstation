/**
 * 
 */
package org.pssframework.controller.archive;

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

	private static final String METHOD_TYPE = "_type";

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
			tranInfoManager.saveOrUpdate(model);
		} catch (Exception e) {
			isSucc = false;
			logger.error(e.getMessage());
			msg = e.getMessage();

		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg);
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		try {
			TranInfo tranInfo = tranInfoManager.getById(id);
			bind(request, tranInfo);
			tranInfoManager.saveOrUpdate(tranInfo);
		} catch (Exception e) {
			isSucc = false;
			logger.error(e.getMessage());
			msg = MSG_UPDATE_FAIL;

		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg);
	}

	@Override
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = getCommonModelAndView();

		result.addObject("tranInfo", tranInfoManager.getById(id));

		result.setViewName("/archive/addTransformer");

		result.addObject(METHOD_TYPE, "show");

		return result;
	}

	@Override
	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = getCommonModelAndView();

		result.addObject("tranInfo", tranInfoManager.getById(id));

		result.setViewName("/archive/addTransformer");

		result.addObject(METHOD_TYPE, "edit");

		return result;
	}

	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TranInfo model) {
		ModelAndView result = getCommonModelAndView();
		result.addObject("tranInfo", model);
		result.setViewName("/archive/addTransformer");
		result.addObject(METHOD_TYPE, "new");
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<CodeInfo> getOptionList(Map mapRequest) {

		return codeInfoManager.findByPageRequest(mapRequest);

	}

	@SuppressWarnings("unchecked")
	private ModelAndView getCommonModelAndView() {
		ModelAndView result = new ModelAndView();

		Map mapRequest = new HashMap();

		mapRequest.put("codecate", "TRAN_CODE");

		result.addObject("typelist", getOptionList(mapRequest));

		mapRequest.put("codecate", "TRAN_STATUS");

		result.addObject("statuslist", getOptionList(mapRequest));

		mapRequest.put("codecate", "VOLT_GRADE");

		result.addObject("voltlist", getOptionList(mapRequest));

		mapRequest.put("codecate", "RATED_EC");

		result.addObject("ratedlist", getOptionList(mapRequest));

		return result;
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		ModelAndView modelAndView = new ModelAndView();
		try {
			tranInfoManager.removeById(id);
		} catch (DataAccessException e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			logger.error(e.getMessage());
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			logger.error(e.getMessage());
		}
		modelAndView.addObject("isSucc", isSucc).addObject("msg", msg);
		return modelAndView;
	}
}
