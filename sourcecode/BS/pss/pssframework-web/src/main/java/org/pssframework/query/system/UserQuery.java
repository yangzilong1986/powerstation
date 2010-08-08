/**
 * 
 */
package org.pssframework.query.system;

import org.pssframework.base.BaseQuery;

/**
 * @author Administrator
 *
 */
public class UserQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3390826575220055683L;
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
