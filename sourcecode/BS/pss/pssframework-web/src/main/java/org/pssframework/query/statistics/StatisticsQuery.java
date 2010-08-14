/**
 * 
 */
package org.pssframework.query.statistics;

import org.pssframework.base.BaseQuery;

/**
 * @author Administrator
 *
 */
public class StatisticsQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3390826575220055683L;
	private Long orgId;

	private Long tgId;

	private String ddate;

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
	 * @return the ddate
	 */
	public String getDdate() {
		return ddate;
	}

	/**
	 * @param ddate the ddate to set
	 */
	public void setDdate(String ddate) {
		this.ddate = ddate;
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
