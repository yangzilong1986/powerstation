/**
 * 
 */
package org.pssframework.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.pssframework.controller.BaseSpringController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.jcaptcha.JCaptchaFilter;

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

	@ResponseBody
	@RequestMapping
	public String validateCaptchaChallenge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ret = "1";
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME);

			if ((StringUtils.isNotBlank(JCaptchaFilter.PARAM_AUTO_PASS_VALUE))
					&& (JCaptchaFilter.PARAM_AUTO_PASS_VALUE.equals(challengeResponse))) {

			} else {
				ret = this.captchaService.validateResponseForID(captchaID, challengeResponse).booleanValue() ? "1"
						: "0";
			}

		} catch (CaptchaServiceException e) {
			logger.error("验证码出错，原因:{}", e.getMessage());
		}
		return ret;
	}
}
