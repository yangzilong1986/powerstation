/**
 * 
 */
package org.pssframework.controller.archive;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
*漏保
 */
@Controller
@RequestMapping("/archive/psinfo")
public class PsInfoController extends BaseRestSpringController<PsInfo, java.lang.Long> {

	private static final String SUCC = "成功";

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
		String msg = SUCC;
		Long psId = 0L;
		try {
			psInfoManager.saveOrUpdate(model);
			psId = model.getPsId();
		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();

		}
		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg).addObject("psId", psId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, PsInfo model) throws Exception {
		ModelAndView result = new ModelAndView();

		String tgid = request.getParameter("tgId");

		Map requestMap = new HashMap();
		requestMap.put("tgid", tgid);

		result.addObject("psinfo", model);

		List<TerminalInfo> termlist = terminalInfoManger.findByPageRequest(requestMap);

		result.addObject("termList", termlist);

		Map mapRequest = new HashMap();

		// RATED_EC 额定电流
		mapRequest.put("codecate", "RATED_EC");

		result.addObject("ratedEcList", codeInfoManager.findByPageRequest(mapRequest));

		// RATED_EC 剩余电流档位
		mapRequest.put("codecate", "REMC_GEAR");

		result.addObject("remcGearList", codeInfoManager.findByPageRequest(mapRequest));

		// 剩余电流档位当前值 REMC_GEAR_VALUE

		mapRequest.put("codecate", "REMC_GEAR_VALUE");

		result.addObject("remcGearValueList", codeInfoManager.findByPageRequest(mapRequest));

		// -漏电分断延迟档位 OFF_DELAY_GEAR
		mapRequest.put("codecate", "OFF_DELAY_GEAR");

		result.addObject("offDelayGearList", codeInfoManager.findByPageRequest(mapRequest));

		// -漏电分断延迟时间 OFF_DELAY_VALUE

		mapRequest.put("codecate", "OFF_DELAY_VALUE");

		result.addObject("offDelayValueList", codeInfoManager.findByPageRequest(mapRequest));

		// 漏保类型
		mapRequest.put("codecate", "PS_TYPE");

		result.addObject("psTypeList", codeInfoManager.findByPageRequest(mapRequest));

		// 通讯方式
		mapRequest.put("codecate", "COMM_MODE");

		result.addObject("commModeList", codeInfoManager.findByPageRequest(mapRequest));

		// 波特率
		mapRequest.put("codecate", "BTL");

		result.addObject("btlList", codeInfoManager.findByPageRequest(mapRequest));

		// 漏保型号
		mapRequest.put("codecate", "PS_MODEL");

		result.addObject("psModelList", codeInfoManager.findByPageRequest(mapRequest));

		result.setViewName("/archive/addPsInfo");

		result.addObject("_type", "new");

		return result;
	}

	@Override
	public ModelAndView update(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSucc = true;
		String msg = SUCC;
		try {
			PsInfo psinfo = this.psInfoManager.getById(id);
			bind(request, psinfo);
			psInfoManager.update(psinfo);
		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();
		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg);
	}

	@Override
	public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		boolean isSucc = true;
		String msg = SUCC;
		try {
			this.psInfoManager.removeById(id);

		} catch (Exception e) {
			this.logger.error(e.getLocalizedMessage());
			isSucc = false;
			msg = e.getMessage();
		}

		return new ModelAndView().addObject("isSucc", isSucc).addObject("msg", msg);
	}
}
