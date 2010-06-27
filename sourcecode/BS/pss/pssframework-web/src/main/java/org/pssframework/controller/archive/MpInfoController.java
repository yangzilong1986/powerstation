/**
 * 
 */
package org.pssframework.controller.archive;



import static org.pssframework.support.system.SystemConst.CODE_BTL;
import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE;
import static org.pssframework.support.system.SystemConst.CODE_CT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_MEAS_MODE;
import static org.pssframework.support.system.SystemConst.CODE_PROTOCOL_METER;
import static org.pssframework.support.system.SystemConst.CODE_PT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_WIRING_MODE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
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
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.MpInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator 计量点
 */
@Controller
@RequestMapping("/archive/mpinfo")
public class MpInfoController extends BaseRestSpringController<MpInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addMpInfo";

	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		super.initBinder(request, binder);
	}

	@Autowired
	private MpInfoManger mpInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, MpInfo model) {

		ModelAndView result = new ModelAndView();

		return result;
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, MpInfo model) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		try {
			for (GpInfo gpInfo : model.getGpInfos()) {
				gpInfo.setMpInfo(model);
			}
			this.mpInfoManger.saveOrUpdate(model);
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();
			this.logger.error(e.getMessage());

		}

		ModelAndView result = new ModelAndView();
		result.addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE,
				msg);
		return result;
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.mpInfoManger.removeById(id);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());

		}

		ModelAndView result = new ModelAndView();
		result.addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE,
				msg);
		return result;
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		try {
			MpInfo mpInfo = this.mpInfoManger.getById(id);
			this.bind(request, mpInfo);
			this.mpInfoManger.saveOrUpdate(mpInfo);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
			this.logger.error(e.getMessage());

		}

		ModelAndView result = new ModelAndView();
		result.addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE,
				msg);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, MpInfo model) throws Exception {
		ModelAndView result = new ModelAndView();

		Map requestMap = new HashMap();

		requestMap.put("tgid", model.getTgInfo().getTgId());

		result.addObject("mpinfo", model);

		this.CommonPart(result, requestMap);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return result;
	}

	/**
	 * 
	 * @param model
	 * @param mapRequest
	 */
	@SuppressWarnings("unchecked")
	private void CommonPart(ModelAndView result, Map mapRequest) {
		List<TerminalInfo> termlist = this.terminalInfoManger.findByPageRequest(mapRequest);
		result.addObject("termList", termlist);

		// CT
		mapRequest.put("codecate", CODE_CT_RATIO);

		result.addObject("ctList", this.codeInfoManager.findByPageRequest(mapRequest));

		// PT
		mapRequest.put("codecate", CODE_PT_RATIO);

		result.addObject("ptList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", CODE_COMM_MODE);

		result.addObject("commModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", CODE_BTL);

		result.addObject("btlList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 接线方式
		mapRequest.put("codecate", CODE_WIRING_MODE);

		result.addObject("wiringModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 计量方式
		mapRequest.put("codecate", CODE_MEAS_MODE);

		result.addObject("measModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 表 规 约
		mapRequest.put("codecate", CODE_PROTOCOL_METER);

		result.addObject("protocolMeterList", this.codeInfoManager.findByPageRequest(mapRequest));

		result.setViewName(MpInfoController.VIEW);

	}
}
