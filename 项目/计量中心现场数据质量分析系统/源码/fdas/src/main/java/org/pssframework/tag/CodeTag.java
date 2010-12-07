package org.pssframework.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.system.CodeInfoManager;
import org.pssframework.util.TagUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * 
 * @author Administrator
 *
 */
public class CodeTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4974275120902366908L;

	// private final Logger logger = LoggerFactory.getLogger(getClass());

	private String codeCate = null;

	private String code = null;

	/**
	 * @return the codecate
	 */
	public String getCodeCate() {
		return codeCate;
	}

	/**
	 * @param codecate the codecate to set
	 */
	public void setCodeCate(String codeCate) {
		this.codeCate = codeCate;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	private CodeInfoManager codeInfoManager;

	public void setCodeInfoManager(CodeInfoManager codeInfoManager) {
		this.codeInfoManager = codeInfoManager;

	}

	/**
	 * 
	 */
	@Override
	public void release() {
		super.release();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected int doStartTagInternal() throws Exception {
		logger.debug("开始获取code");

		Map mapRequest = new HashMap();
		String codeN = "NULL";
		if (this.code == null || "".endsWith(code.trim())) {

		} else {
			codeN = this.code;
		}
		mapRequest.put("code", codeN);
		mapRequest.put("codecate", this.codeCate);
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		try {
			codeInfoManager = (CodeInfoManager) ctx.getBean("codeInfoManager");
			List<CodeInfo> code = codeInfoManager.findByPageRequest(mapRequest);
			for (CodeInfo codeInfo : code) {
				logger.debug("获取实体...");
				TagUtils.getInstance().write(pageContext, codeInfo.getName());
			}

		} catch (Exception _e) {
			logger.error("获取code异常", _e);
		} finally {

		}
		return SKIP_BODY;
	}
}
