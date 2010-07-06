/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_BTL;
import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE;
import static org.pssframework.support.system.SystemConst.CODE_CT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_OFF_DELAY_GEAR;
import static org.pssframework.support.system.SystemConst.CODE_OFF_DELAY_VALUE;
import static org.pssframework.support.system.SystemConst.CODE_PROTOCOL_METER;
import static org.pssframework.support.system.SystemConst.CODE_PS_MODEL;
import static org.pssframework.support.system.SystemConst.CODE_PS_TYPE;
import static org.pssframework.support.system.SystemConst.CODE_PT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_RATED_EC;
import static org.pssframework.support.system.SystemConst.CODE_REMC_GEAR;
import static org.pssframework.support.system.SystemConst.CODE_REMC_GEAR_VALUE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
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
 * @author Administrator 漏保
 */
@Controller
@RequestMapping("/archive/psinfo")
public class PsInfoController extends BaseRestSpringController<PsInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addPsInfo";

	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		super.initBinder(request, binder);
	}

	@Autowired
	private PsInfoManger psInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, PsInfo model) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long psId = 0L;

		if (checkGpsn(model))
			return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, false).addObject(CONTROLLER_AJAX_MESSAGE,
					"该终端下测量点序号重复");

		try {
			this.psInfoManager.saveOrUpdate(model);
			psId = model.getPsId();
		} catch (DataAccessException e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();

		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();
		}
		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg)
				.addObject("psId", psId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = new ModelAndView();

		PsInfo psInfo = this.psInfoManager.getById(id);

		Map requestMap = new HashMap();

		requestMap.put("tgid", psInfo.getGpInfo().getObjectId());

		result.addObject("psinfo", psInfo);

		this.CommonPart(result, requestMap);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, PsInfo model) throws Exception {
		ModelAndView result = new ModelAndView();

		Long tgid = model.getGpInfo().getObjectId();

		Map requestMap = new HashMap();

		requestMap.put("tgid", tgid);

		result.addObject("psinfo", model);

		this.CommonPart(result, requestMap);

		result.addObject(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return result;
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		try {
			
			PsInfo psinfo = this.psInfoManager.getById(id);
			logger.debug("get psinfo {} from db", psinfo);

			this.bind(request, psinfo);
			logger.debug("bind psinfo {} from request", psinfo);
			
			if (checkGpsn(psinfo)) {

				logger.info("The serial number terminal repeat measurement points");

				return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, false).addObject(CONTROLLER_AJAX_MESSAGE,
						"该终端下测量点序号重复");

			}

			this.psInfoManager.update(psinfo);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			isSucc = false;
			msg = e.getMessage();
		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg);
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.psInfoManager.removeById(id);

		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();
		}

		return new ModelAndView().addObject(CONTROLLER_AJAX_IS_SUCC, isSucc).addObject(CONTROLLER_AJAX_MESSAGE, msg);
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

		// RATED_EC 额定电流
		mapRequest.put("codecate", CODE_RATED_EC);

		result.addObject("ratedEcList", this.codeInfoManager.findByPageRequest(mapRequest));

		// RATED_EC 剩余电流档位
		mapRequest.put("codecate", CODE_REMC_GEAR);

		result.addObject("remcGearList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 剩余电流档位当前值 REMC_GEAR_VALUE

		mapRequest.put("codecate", CODE_REMC_GEAR_VALUE);

		result.addObject("remcGearValueList", this.codeInfoManager.findByPageRequest(mapRequest));

		// -漏电分断延迟档位 OFF_DELAY_GEAR
		mapRequest.put("codecate", CODE_OFF_DELAY_GEAR);

		result.addObject("offDelayGearList", this.codeInfoManager.findByPageRequest(mapRequest));

		// -漏电分断延迟时间 OFF_DELAY_VALUE

		mapRequest.put("codecate", CODE_OFF_DELAY_VALUE);

		result.addObject("offDelayValueList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 漏保类型
		mapRequest.put("codecate", CODE_PS_TYPE);

		result.addObject("psTypeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", CODE_COMM_MODE);

		result.addObject("commModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", CODE_BTL);

		result.addObject("btlList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 漏保型号
		mapRequest.put("codecate", CODE_PS_MODEL);

		result.addObject("psModelList", this.codeInfoManager.findByPageRequest(mapRequest));

		// CT
		mapRequest.put("codecate", CODE_CT_RATIO);

		result.addObject("ctList", this.codeInfoManager.findByPageRequest(mapRequest));

		// PT
		mapRequest.put("codecate", CODE_PT_RATIO);

		result.addObject("ptList", this.codeInfoManager.findByPageRequest(mapRequest));

		// TODO
		/*
		 * 这里先采用电表规约
		 */
		mapRequest.put("codecate", CODE_PROTOCOL_METER);

		result.addObject("protocolList", this.codeInfoManager.findByPageRequest(mapRequest));

		result.setViewName(VIEW);

	}

	/**
	 * check gpSn 唯一性
	 * @param psInfo
	 * @return
	 */
	private boolean checkGpsn(PsInfo psInfo) {

		return psInfoManager.checkGpsn(psInfo);

	}
}
