/**
 * 
 */
package org.pssframework.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.pssframework.controller.BaseSpringController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.jcaptcha.JCaptchaFilter;

import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/security/checkCaptcha")
public class CheckCaptcha extends BaseSpringController {
	@Autowired
	private CaptchaService captchaService;

	ImageCaptcha imageCaptcha = null;

	@ResponseBody
	@RequestMapping
	public String validateCaptchaChallenge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ret = "1";
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME);

			String challengeSession = (String) request.getSession().getAttribute(
					JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME);
			if ((StringUtils.isNotBlank(JCaptchaFilter.PARAM_AUTO_PASS_VALUE))
					&& (JCaptchaFilter.PARAM_AUTO_PASS_VALUE.equals(challengeResponse))) {

			} else if (challengeResponse.equals(challengeSession)) {
			} else {
				ret = this.captchaService.validateResponseForID(captchaID, challengeResponse).booleanValue() ? "1"
						: "0";
				if ("1".equals(ret)) {
					request.getSession().setAttribute(JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME, challengeResponse);
				}
			}

		} catch (CaptchaServiceException e) {
			ret = "0";
			logger.error("验证码出错，原因:{}", e.getMessage());
		}
		return ret;
	}

	public boolean validateCaptchaResponse(String validateCode, HttpSession session) {
		boolean flag = true;
		try {
			imageCaptcha = (ImageCaptcha) session.getAttribute("imageCaptcha");
			if (imageCaptcha == null) {
				// log.info("validateCaptchaResponse returned false due to  
				// imageCaptcha is null");  
				flag = false;
			}
			//validateCode = validateCode.toLowerCase();// use upper case for  
			validateCode = validateCode.toUpperCase();// use upper case for  
			// easier usage  
			flag = (imageCaptcha.validateResponse(validateCode)).booleanValue();
			session.removeAttribute("imageCaptcha");
			return flag;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("校验码校验异常 ＝＝ " + ex);
			return false;
		}
	}
}
