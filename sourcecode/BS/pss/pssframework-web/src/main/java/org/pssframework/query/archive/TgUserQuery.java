/**
 * 
 */
package org.pssframework.query.archive;

import org.pssframework.base.BaseQuery;

/**
 * @author Administrator
 *
 */
public class TgUserQuery extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4007863679990551233L;

	private Long orgId;

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

}
