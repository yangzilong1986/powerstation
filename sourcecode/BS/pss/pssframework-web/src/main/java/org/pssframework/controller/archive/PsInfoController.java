/**
 * 
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_BTL;
import static org.pssframework.support.system.SystemConst.CODE_CLOCK_LIST;
import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE_GM;
import static org.pssframework.support.system.SystemConst.CODE_CT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_DAY_LIST;
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
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_SHOW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_FAIL;
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
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.archive.TgInfoManager;
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

/**
 * @author Administrator 漏保
 */
@Controller
@RequestMapping("/archive/psinfo")
public class PsInfoController extends BaseRestSpringController<PsInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addPsInfo";

	private static final String FORWARD = "/archive/psinfo";

	//该终端下测量点序号重复
	private static final String GP_IS_REPEAT = "该终端下测量点序号重复";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private PsInfoManger psInfoManager;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Autowired
	private TgInfoManager tgInfoManager;

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@Valid PsInfo model) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;
		Long psId = 0L;

		if (checkGpsn(model)) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE, GP_IS_REPEAT);
			return VIEW;
		}

		try {
			this.psInfoManager.saveOrUpdate(model);
			psId = model.getPsId();
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, MSG_CREATED_SUCCESS);
		} catch (DataAccessException e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, MSG_CREATED_FAIL);
		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = MSG_CREATED_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, MSG_CREATED_FAIL);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg)
				.addAttribute("psId", psId);
		return VIEW;
	}

	/** 编辑 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id) throws Exception {

		PsInfo psInfo = this.psInfoManager.getById(id);

		Map requestMap = new HashMap();

		requestMap.put("tgid", psInfo.getGpInfo().getObjectId());

		result.addAttribute("istAddr", getPsAddr(psInfo.getGpInfo().getObjectId()));

		result.addAttribute("psinfo", psInfo);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/** 查看 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}")
	public String show(ModelMap result, @PathVariable Long id) throws Exception {

		PsInfo psInfo = this.psInfoManager.getById(id);

		Map requestMap = new HashMap();

		requestMap.put("tgid", psInfo.getGpInfo().getObjectId());

		result.addAttribute("istAddr", getPsAddr(psInfo.getGpInfo().getObjectId()));

		result.addAttribute("psinfo", psInfo);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);

		return VIEW;
	}

	/** 进入新增 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/new")
	public String _new(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, PsInfo model)
			throws Exception {

		Long tgid = model.getGpInfo().getObjectId();

		Map<String, Comparable> requestMap = new HashMap<String, Comparable>();

		requestMap.put("tgid", tgid);

		modelMap.addAttribute("istAddr", getPsAddr(tgid));

		modelMap.addAttribute("psinfo", model);

		this.CommonPart(modelMap, requestMap);

		modelMap.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW;
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap modelMap, @Valid PsInfo psinfo, BindingResult errors, @PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false)
					.addAttribute(CONTROLLER_AJAX_MESSAGE, MSG_UPDATE_FAIL);
			return FORWARD + "/" + id + "/edit";
		}

		try {

			logger.debug("bind psinfo {} from request", psinfo);

			if (checkGpsn(psinfo)) {

				logger.info("The serial number terminal repeat measurement points");
				modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE, "");
				return VIEW;

			}
			PsInfo psInfoDb = this.psInfoManager.getById(id);
			bind(request, psInfoDb);
			this.psInfoManager.update(psInfoDb);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap modelMap, @PathVariable Long id) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.psInfoManager.removeById(id);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 
	 * @param result
	 * @param mapRequest
	 */
	@SuppressWarnings("rawtypes")
	private void CommonPart(ModelMap result, Map<String, Comparable> mapRequest) {

		List<TerminalInfo> termlist = this.terminalInfoManger.findByPageRequest(mapRequest);

		result.addAttribute("termList", termlist);

		// RATED_EC 额定电流
		mapRequest.put("codecate", CODE_RATED_EC);

		result.addAttribute("ratedEcList", this.codeInfoManager.findByPageRequest(mapRequest));

		// RATED_EC 剩余电流档位
		mapRequest.put("codecate", CODE_REMC_GEAR);

		result.addAttribute("remcGearList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 剩余电流档位当前值 REMC_GEAR_VALUE

		mapRequest.put("codecate", CODE_REMC_GEAR_VALUE);

		result.addAttribute("remcGearValueList", this.codeInfoManager.findByPageRequest(mapRequest));

		// -漏电分断延迟档位 OFF_DELAY_GEAR
		mapRequest.put("codecate", CODE_OFF_DELAY_GEAR);

		result.addAttribute("offDelayGearList", this.codeInfoManager.findByPageRequest(mapRequest));

		// -漏电分断延迟时间 OFF_DELAY_VALUE

		mapRequest.put("codecate", CODE_OFF_DELAY_VALUE);

		result.addAttribute("offDelayValueList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 漏保类型
		mapRequest.put("codecate", CODE_PS_TYPE);

		result.addAttribute("psTypeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", CODE_COMM_MODE_GM);

		result.addAttribute("commModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", CODE_BTL);

		result.addAttribute("btlList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 漏保型号
		mapRequest.put("codecate", CODE_PS_MODEL);

		result.addAttribute("psModelList", this.codeInfoManager.findByPageRequest(mapRequest));

		// CT
		mapRequest.put("codecate", CODE_CT_RATIO);

		result.addAttribute("ctList", this.codeInfoManager.findByPageRequest(mapRequest));

		// PT
		mapRequest.put("codecate", CODE_PT_RATIO);

		result.addAttribute("ptList", this.codeInfoManager.findByPageRequest(mapRequest));

		// TODO
		/*
		 * 这里先采用电表规约
		 */
		mapRequest.put("codecate", CODE_PROTOCOL_METER);

		result.addAttribute("protocolList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 日
		mapRequest.put("codecate", CODE_DAY_LIST);

		result.addAttribute("dayList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 时
		mapRequest.put("codecate", CODE_CLOCK_LIST);

		result.addAttribute("clockList", this.codeInfoManager.findByPageRequest(mapRequest));

	}

	/**
	 * check gpSn 唯一性
	 * @param psInfo
	 * @return
	 */
	private boolean checkGpsn(PsInfo psInfo) {

		return psInfoManager.checkGpsn(psInfo);

	}

	private String getPsAddr(Long tgId) {
		TgInfo tgInfo = this.tgInfoManager.getById(tgId);
		String istAddr = null;
		List<TranInfo> tranInfoList = tgInfo.getTranInfos();
		for (TranInfo tranInfo : tranInfoList) {
			istAddr = tranInfo.getInstAddr();
		}
		return istAddr;
	}
}
