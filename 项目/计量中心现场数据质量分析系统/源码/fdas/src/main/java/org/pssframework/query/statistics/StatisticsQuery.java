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
	
	private String sdate;
	
	private String edate;
	
	private String logicalAddr;

    private String dbjh;

	/**
	 * @return the sdate
	 */
	public String getSdate() {
		return sdate;
	}

	/**
	 * @param sdate the sdate to set
	 */
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	/**
	 * @return the edate
	 */
	public String getEdate() {
		return edate;
	}

	/**
	 * @param edate the edate to set
	 */
	public void setEdate(String edate) {
		this.edate = edate;
	}

	/**
	 * @return the logicalAddr
	 */
	public String getLogicalAddr() {
		return logicalAddr;
	}

	/**
	 * @param logicalAddr the logicalAddr to set
	 */
	public void setLogicalAddr(String logicalAddr) {
		this.logicalAddr = logicalAddr;
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

    public String getDbjh() {
        return dbjh;
    }

    public void setDbjh(String dbjh) {
        this.dbjh = dbjh;
    }
}
