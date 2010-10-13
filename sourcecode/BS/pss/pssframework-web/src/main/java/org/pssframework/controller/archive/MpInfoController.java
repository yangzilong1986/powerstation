/**
 *
 */
package org.pssframework.controller.archive;

import static org.pssframework.support.system.SystemConst.CODE_BTL;
import static org.pssframework.support.system.SystemConst.CODE_COMM_MODE;
import static org.pssframework.support.system.SystemConst.CODE_CT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_MEAS_MODE;
import static org.pssframework.support.system.SystemConst.CODE_METER_STATUS;
import static org.pssframework.support.system.SystemConst.CODE_PROTOCOL_METER;
import static org.pssframework.support.system.SystemConst.CODE_PT_RATIO;
import static org.pssframework.support.system.SystemConst.CODE_WIRING_MODE;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.GpInfoManger;
import org.pssframework.service.archive.MpInfoManger;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.service.system.CodeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.MTO_376;
import pep.bp.realinterface.mto.MessageTranObject;

/**
 * @author Administrator 计量点
 */
@Controller
@RequestMapping("/archive/mpinfo")
public class MpInfoController extends BaseRestSpringController<MpInfo, java.lang.Long> {

	private static final String VIEW = "/archive/addMpInfo";
	private static final String VALIDATE = "验证出错";

	/**
	 * binder用于bean属性的设置
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired
	private MpInfoManger mpInfoManger;

	@Autowired
	private CodeInfoManager codeInfoManager;

	@Autowired
	private TerminalInfoManger terminalInfoManger;

	@Autowired
	private GpInfoManger gpInfoManger;

	@Autowired
	private ICollectInterface realTimeProxy376;

	/**
	 * 列表
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			org.pssframework.query.archive.MpQuery mpQuery) {

		return VIEW;
	}

	/**
	 * 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap modelMap, @Valid MpInfo model, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_CREATED_SUCCESS;

		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW;
		}

		try {
			for (GpInfo gpInfo : model.getGpInfos()) {
				gpInfo.setObjectId(model.getTgId());
				gpInfo.setMpInfo(model);
			}
			this.mpInfoManger.saveOrUpdate(model);
			//Flash.current().success(MSG_CREATED_SUCCESS);
		} catch (Exception e) {
			isSucc = false;
			msg = e.getMessage();
			this.logger.error(e.getMessage());
			//Flash.current().success(MSG_CREATED_FAIL);
		}
		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap modelMap, @PathVariable Long id) {
		boolean isSucc = true;
		String msg = MSG_DELETE_SUCCESS;
		try {
			this.mpInfoManger.removeById(id);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_DELETE_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().error(CONTROLLER_AJAX_MESSAGE, msg);
		}

		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap modelMap, @PathVariable Long id, @Valid MpInfo mpInfo, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSucc = true;
		String msg = MSG_UPDATE_SUCCESS;
		if (errors.hasErrors()) {
			modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, false).addAttribute(CONTROLLER_AJAX_MESSAGE,
					errors.getObjectName());
			return VIEW;
		}
		try {
			MpInfo mpInfoDb = this.mpInfoManger.getById(id);
			this.bind(request, mpInfoDb);
			this.mpInfoManger.saveOrUpdate(mpInfoDb);
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		} catch (Exception e) {
			isSucc = false;
			msg = MSG_UPDATE_FAIL;
			this.logger.error(e.getMessage());
			//Flash.current().success(CONTROLLER_AJAX_MESSAGE, msg);
		}

		modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
		return VIEW;
	}

	/**
	 * 进入新增
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/new")
	public String _new(ModelMap result, HttpServletRequest request, HttpServletResponse response, MpInfo model)
			throws Exception {

		Map requestMap = new HashMap();

		requestMap.put("tgid", model.getTgId());

		result.addAttribute("mpinfo", model);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);

		return VIEW;
	}

	/**
	 * 编辑
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap result, @PathVariable Long id) throws Exception {

		Map requestMap = new HashMap();

		MpInfo model = mpInfoManger.getById(id);

		requestMap.put("tgid", model.getTgId());

		result.addAttribute("mpinfo", model);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);

		return VIEW;
	}

	/**
	 * 查看
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}")
	public String show(ModelMap result, @PathVariable Long id) throws Exception {

		Map requestMap = new HashMap();

		MpInfo model = mpInfoManger.getById(id);

		requestMap.put("tgid", model.getTgId());

		result.addAttribute("mpinfo", model);

		this.CommonPart(result, requestMap);

		result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_SHOW);

		return VIEW;
	}

	/**
	 * @param result     ModelMap
	 * @param mapRequest
	 */
	@SuppressWarnings("unchecked")
	private void CommonPart(ModelMap result, Map mapRequest) {
		List<TerminalInfo> termlist = this.terminalInfoManger.findByPageRequest(mapRequest);
		result.addAttribute("termList", termlist);

		// CT
		mapRequest.put("codecate", CODE_CT_RATIO);

		result.addAttribute("ctList", this.codeInfoManager.findByPageRequest(mapRequest));

		// PT
		mapRequest.put("codecate", CODE_PT_RATIO);

		result.addAttribute("ptList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", CODE_COMM_MODE);

		result.addAttribute("commModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", CODE_BTL);

		result.addAttribute("btlList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 接线方式
		mapRequest.put("codecate", CODE_WIRING_MODE);

		result.addAttribute("wiringModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 计量方式
		mapRequest.put("codecate", CODE_MEAS_MODE);

		result.addAttribute("measModeList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 表 规 约
		mapRequest.put("codecate", CODE_PROTOCOL_METER);

		result.addAttribute("protocolMeterList", this.codeInfoManager.findByPageRequest(mapRequest));

		// 表 状态
		mapRequest.put("codecate", CODE_METER_STATUS);

		result.addAttribute("runStatusList", this.codeInfoManager.findByPageRequest(mapRequest));

	}

	/**
	 * check gpSn 唯一性
	 *
	 * @param info
	 * @return
	 */
	private boolean checkGpsn(MpInfo info) {
		return mpInfoManger.checkGpSn(info);
	};

	/**
	 *校验测量点序号
	 */
	@ResponseBody
	@RequestMapping(value = "/checkGpSn")
	public String checkGpSn(MpInfo mpInfo) throws Exception {
		GpInfo model = mpInfo.getGpInfos().get(0);
		return this.gpInfoManger.checkGpSnRePeat(model);
	}

	/**
	 *校验测量地址序号
	 */
	@ResponseBody
	@RequestMapping(value = "/checkGpAddr")
	public String checkGpAddr(MpInfo mpInfo) throws Exception {
		GpInfo model = mpInfo.getGpInfos().get(0);
		return this.gpInfoManger.checkGpAddrRePeat(model);
	}

	/**
	 * 一键设置f10
	 * @param psInfo
	 * @return
	 * @throws Exception
	 * 
	 * @param mapCO
	 *     *<"logicalAddr", String>     : 逻辑地址 C_TERMINAL.LOGICAL_ADDR
	 *     *<"equipProtocol", String>   : 设备规约号 C_TERMINAL.PROTOCOL_NO
	 *     ---------------------------------------------------------------------
	 *      <"channelType", String>     : 通道类型 【默认为1(TCP)】
	 *      <"pwAlgorith", String>      : 密码算法 【默认为0】
	 *      <"pwContent", String>       : 密码内容 【默认为8888】
	 *      <"mpExpressMode", String>   : 测量点表示方式 【默认为3(列表方式)】
	 *      <"mpSn", String>            : 测量点序号组 【默认为[0]】
	 *     ---------------------------------------------------------------------
	 * @param mapParams
	 *     *<"1004001001", String>      : 本次电能表/交流采样装置配置数量
	 *     *<"10040010020001", String>  : 本次配置第0001块电能表/交流采样装置序号 【默认与所属测量点号相同】
	 *     *<"10040010030001", String>  : 本次配置第0001块电能表/交流采样装置所属测量点号
	 *     *<"10040010040001", String>  : 本次配置第0001块电能表/交流采样装置通信波特率 C_METER.BAUDRATE
	 *     *<"10040010050001", String>  : 本次配置第0001块电能表/交流采样装置通信端口号 C_GP.PORT
	 *     *<"10040010060001", String>  : 本次配置第0001块电能表/交流采样装置通信协议类型 C_GP.PROTOCOL_NO
	 *     *<"10040010070001", String>  : 本次配置第0001块电能表/交流采样装置通信地址  C_GP.GP_ADDR
	 *     ---------------------------------------------------------------------
	 *      <"10040010080001", String>  : 本次配置第0001块电能表/交流采样装置通信密码 【默认为000000000000】
	 *      <"10040010100001", String>  : 本次配置第0001块电能表/交流采样装置电能费率个数 【默认为000100(4个费率)】
	 *      <"10040010120001", String>  : 本次配置第0001块电能表/交流采样装置有功电能示值的整数位个数 【默认为10(6位)】
	 *      <"10040010130001", String>  : 本次配置第0001块电能表/交流采样装置有功电能示值的小数位个数 【默认为11(4位)】
	 *      <"10040010140001", String>  : 本次配置第0001块电能表/交流采样装置所属采集器通信地址 【默认为000000000000】
	 *      <"10040010150001", String>  : 本次配置第0001块电能表/交流采样装置所属的用户大类号 【默认为0000】
	 *      <"10040010160001", String>  : 本次配置第0001块电能表/交流采样装置所属的用户小类号 【默认为0000】
	 *     ---------------------------------------------------------------------
	 */
	@ResponseBody
	@RequestMapping(value = "/akeySetupTermParamF10")
	public String akeySetupTermParamF10(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String jsonString = request.getParameter("dto");

		ObjectMapper objectMapper = new ObjectMapper();

		MessageTranObject mto_376 = null;
		try {
			mto_376 = objectMapper.readValue(jsonString, MTO_376.class);
		} catch (JsonParseException e) {
			logger.debug(e.getMessage());
		} catch (JsonMappingException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}

		long collectId = this.realTimeProxy376.writeParameters(mto_376);

		logger.info("collectId : " + collectId);

		return String.valueOf(collectId);
	}

	@ResponseBody
	@RequestMapping(value = "/backAkeySetupTermParamF10")
	public String backAkeySetupTermParamF10(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String strCollectId = request.getParameter("collectId");
		String ret = "";
		if (strCollectId != null) {
			long collectId = Integer.parseInt(strCollectId);
			Map<String, String> resultMap = realTimeProxy376.getReturnByWriteParameter(collectId);
			if (resultMap.containsValue("1")) {
				ret = "1";
			} else if (resultMap.containsValue("2")) {
				ret = "2";
			} else {
				ret = "";
			}
			logger.info("resultMap : " + resultMap.toString());
		}
		return ret;
	}
}
