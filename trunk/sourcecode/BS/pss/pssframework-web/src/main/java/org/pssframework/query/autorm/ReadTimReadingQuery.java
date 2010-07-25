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
	private Long objId;
	private String logicalAddr;

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

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Long getObjId() {
		return objId;
	}

	public void setLogicalAddr(String logicalAddr) {
		this.logicalAddr = logicalAddr;
	}

	public String getLogicalAddr() {
		return logicalAddr;
	}

}
