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

	private String[] checkedUser;

	private String empNos;

	/**
	 * @return the empNos
	 */
	public String getEmpNos() {
		return empNos;
	}

	/**
	 * @param empNos the empNos to set
	 */
	public void setEmpNos(String empNos) {
		this.empNos = empNos;
	}

	/**
	 * @return the checkedUser
	 */
	public String[] getCheckedUser() {
		return checkedUser;
	}

	/**
	 * @param checkedUser the checkedUser to set
	 */
	public void setCheckedUser(String[] checkedUser) {
		this.checkedUser = checkedUser;
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
	 * @return the showAllAccount
	 */
	public boolean isShowAllAccount() {
		return showAllAccount;
	}

	/**
	 * @param showAllAccount the showAllAccount to set
	 */
	public void setShowAllAccount(boolean showAllAccount) {
		this.showAllAccount = showAllAccount;
	}

	private Long orgId;

	private Long tgId;

	private boolean showAllAccount;

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
