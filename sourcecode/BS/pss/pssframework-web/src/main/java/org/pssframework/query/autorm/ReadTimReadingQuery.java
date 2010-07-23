/**
 * 
 */
package org.pssframework.query.autorm;

import org.pssframework.base.BaseQuery;

/**
 * @author Administrator
 *
 */
public class ReadTimReadingQuery extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3218026520608204003L;
	private Long orgId;
	private Long tgId;
	private String loggicAddr;

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the tgId
	 */
	public Long getTgId() {
		return tgId;
	}

	/**
	 * @param tgId the tgId to set
	 */
	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	/**
	 * @return the loggicAddr
	 */
	public String getLoggicAddr() {
		return loggicAddr;
	}

	/**
	 * @param loggicAddr the loggicAddr to set
	 */
	public void setLoggicAddr(String loggicAddr) {
		this.loggicAddr = loggicAddr;
	}

}
