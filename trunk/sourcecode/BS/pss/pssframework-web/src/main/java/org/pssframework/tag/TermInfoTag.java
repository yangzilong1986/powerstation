package org.pssframework.tag;

import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.service.archive.TerminalInfoManger;
import org.pssframework.util.TagUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * 
 * @author Administrator
 *
 */
public class TermInfoTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3757235845729811233L;

	private String termId = null;

	private TerminalInfoManger terminalInfoManger;

	/**
	 * 
	 */
	@Override
	public void release() {
		super.release();
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		logger.debug("开始获取terminalInfoManger");

		String termIdN = "-1";
		if (this.termId == null || "".endsWith(termId.trim())) {

		} else {
			termIdN = this.termId;
		}
		TerminalInfo terminalInfo = null;

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		try {
			terminalInfoManger = (TerminalInfoManger) ctx.getBean("terminalInfoManger");
			terminalInfo = terminalInfoManger.getById(Long.parseLong(termIdN));

			logger.debug("获取实体...");
			TagUtils.getInstance().write(pageContext, terminalInfo.getLogicalAddr());

		} catch (Exception _e) {
			logger.error("获取termId异常", _e);
		} finally {

		}
		return SKIP_BODY;
	}
}
