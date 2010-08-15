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

	private Long tgId;

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
